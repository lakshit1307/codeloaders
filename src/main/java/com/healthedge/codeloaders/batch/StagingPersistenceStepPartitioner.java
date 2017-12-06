package com.healthedge.codeloaders.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.FileParser;
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

	@Autowired
	private FileParser fileParser;

	@Autowired
	private DiffCreator diffCreator;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> map = new HashMap<>();

		String filePath = CodeLoaderContext.getInstance().getCurrentFilePath();
		LOGGER.info("Processing filePath [{}]", filePath);

		try {
			final Map<String, Service> record = fileParser.parse(filePath);
			final Map<String, List<Service>> diffRecords = diffCreator.diff(filePath,record);
			createExecutionContext(map, diffRecords);
		} catch (Exception ex) { //NOPMD
			LOGGER.error("Error encountered during parsing or diff [{}]", ExceptionUtils.getStackTrace(ex));
		}

		return map;

	}

	private void createExecutionContext(Map<String, ExecutionContext> map, Map<String, List<Service>> diffRecords) {
		ExecutionContext executionContext;
		List<Service> createList = diffRecords.get(DiffCreator.CREATE_ACTION);
		if (CollectionUtils.isNotEmpty(createList)) {
            for (Service service : createList) {
                executionContext = new ExecutionContext();
                executionContext.put(ACTION, DiffCreator.CREATE_ACTION);
                executionContext.put(ITEM, service);
                map.put(service.getServiceCode(), executionContext);
            }
        }

		List<Service> appendList = diffRecords.get(DiffCreator.APPEND_ACTION);
		if (CollectionUtils.isNotEmpty(appendList)) {
            for (Service service : appendList) {
                executionContext = new ExecutionContext();
                executionContext.put(ACTION, DiffCreator.APPEND_ACTION);
                executionContext.put(ITEM, service);
                map.put(service.getServiceCode(), executionContext);
            }
        }

		List<Service> terminateList = diffRecords.get(DiffCreator.TERMINATE_ACTION);
		if (CollectionUtils.isNotEmpty(terminateList)) {
            for (Service service : terminateList) {
                executionContext = new ExecutionContext();
                executionContext.put(ACTION, DiffCreator.TERMINATE_ACTION);
                executionContext.put(ITEM, service);
                map.put(service.getServiceCode(), executionContext);
            }
        }
	}


}
