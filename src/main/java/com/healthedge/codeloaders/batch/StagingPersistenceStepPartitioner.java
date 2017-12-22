package com.healthedge.codeloaders.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.myparser.AbstractParser;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.NewDiffCreator;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;
import com.healthedge.codeloaders.service.Transformer.Transformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class StagingPersistenceStepPartitioner implements Partitioner {
	private static final Logger LOGGER = LoggerFactory.getLogger(StagingPersistenceStepPartitioner.class);

	public static final String ACTION = "action";
	public static final String ITEM = "item";
	public static final String FILE_META_DATA = "filemetadata";

	@Autowired
	private AbstractParser abstractParser;

	@Autowired
	private ImplementationFactory implementationFactory;

	@Autowired
	private NewDiffCreator diffCreator;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		MyFileMetaData fileMetaData = CodeLoaderContext.getInstance().getFileMetaData();
		String filePath = fileMetaData.getFilePath();
		LOGGER.info("Processing filePath [{}]", filePath);

		Map<String, ExecutionContext> map = new HashMap<>();
		try {
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			List<Map<String, String>> parseResult = abstractParser.parse(fileMetaData);
			endTime = System.currentTimeMillis();
			LOGGER.info("Total time required to parse file [{}]: {} ms", filePath, endTime - startTime);

			startTime = System.currentTimeMillis();
			Transformer transformer = implementationFactory.getTransformer(fileMetaData.getFileType());
			Map<String, BaseEntity> record = transformer.transform(parseResult);
			endTime = System.currentTimeMillis();
			LOGGER.info("Total time required to transform file [{}]: {} ms", filePath, endTime - startTime);

			startTime = System.currentTimeMillis();
			final Map<String, List<BaseEntity>> diffRecords = diffCreator.configureDiff(record, fileMetaData, fileMetaData.isTerminateRequired());
			endTime = System.currentTimeMillis();
			LOGGER.info("Total time required to diff file [{}]: {} ms", filePath, endTime - startTime);

			startTime = System.currentTimeMillis();
			createExecutionContext(map, diffRecords, fileMetaData);
			endTime = System.currentTimeMillis();
			LOGGER.info("Total time required to create ExecutionContext [{}]: {} ms", filePath, endTime - startTime);
		} catch (Exception ex) { //NOPMD
			LOGGER.error("Error encountered during parsing or diff [{}]", ExceptionUtils.getStackTrace(ex));
		}

		return map;
	}

	private void createExecutionContext(Map<String, ExecutionContext> map, Map<String, List<BaseEntity>> diffRecords, MyFileMetaData fileMetaData) {
		ExecutionContext executionContext;
		List<BaseEntity> createList = diffRecords.get(DiffCreator.CREATE_ACTION);
		if (CollectionUtils.isNotEmpty(createList)) {
            for (BaseEntity service : createList) {
                executionContext = new ExecutionContext();
                executionContext.put(ACTION, DiffCreator.CREATE_ACTION);
                executionContext.put(ITEM, service);
                executionContext.put(FILE_META_DATA, fileMetaData);
                map.put(service.getCode(), executionContext);
            }
        }

		List<BaseEntity> appendList = diffRecords.get(DiffCreator.APPEND_ACTION);
		if (CollectionUtils.isNotEmpty(appendList)) {
            for (BaseEntity service : appendList) {
                executionContext = new ExecutionContext();
                executionContext.put(ACTION, DiffCreator.APPEND_ACTION);
                executionContext.put(ITEM, service);
				executionContext.put(FILE_META_DATA, fileMetaData);
                map.put(service.getCode(), executionContext);
            }
        }

		List<BaseEntity> terminateList = diffRecords.get(DiffCreator.TERMINATE_ACTION);
		if (CollectionUtils.isNotEmpty(terminateList)) {
            for (BaseEntity service : terminateList) {
                executionContext = new ExecutionContext();
                executionContext.put(ACTION, DiffCreator.TERMINATE_ACTION);
                executionContext.put(ITEM, service);
				executionContext.put(FILE_META_DATA, fileMetaData);
                map.put(service.getCode(), executionContext);
            }
        }
	}


}
