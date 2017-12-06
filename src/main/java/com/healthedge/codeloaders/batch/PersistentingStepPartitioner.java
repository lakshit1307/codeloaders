package com.healthedge.codeloaders.batch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.healthedge.codeloaders.business.LoadPendingCodes;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.FileParser;
import com.healthedge.codeloaders.service.FileSorter;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PersistentingStepPartitioner implements Partitioner {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistentingStepPartitioner.class);

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> map = new HashMap<>(gridSize);

		String filePath = CodeLoaderContext.getInstance().getCurrentFilePath();
		LOGGER.info("********** Processing filePath [{}]", filePath);
		System.out.println("*************** Processing filePath " + filePath);

		for (int i = 0; i < gridSize; i++) {
			ExecutionContext context = new ExecutionContext();
			context.put("action", "Create " + i + " Processing filePath " + filePath);
			map.put("partitionkey " + i, context);
		}

		return map;

	}


}
