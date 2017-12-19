package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.StagingPersistenceService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class StagingPersistenceTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(StagingPersistenceTasklet.class);

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private StagingPersistenceService stagingPersistenceService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext executionContext = chunkContext.getStepContext()
                .getStepExecution().getExecutionContext();

        String action = executionContext.getString(StagingPersistenceStepPartitioner.ACTION);
        BaseEntity service = (BaseEntity) executionContext.get(StagingPersistenceStepPartitioner.ITEM);
        MyFileMetaData fileMetaData = (MyFileMetaData) executionContext.get(StagingPersistenceStepPartitioner.FILE_META_DATA);
        List<BaseEntity> entities = new ArrayList<>();
        entities.add(service);

        LOGGER.debug("Thread [{}] processing action [{}] having code [{}]", Thread.currentThread().getName(), action,
                service.getCode());

        try {
            LOGGER.debug("Persistence action Create action on code [{}] ", service.getCode());
            stagingPersistenceService.persistToCodeLoders(entities, fileMetaData);
        } catch (Exception ex) { //NOPMD
            LOGGER.error("Error occurred processing code [{}] with action [{}] with exception [{}]",
                    service.getCode(), service.getAction(), ExceptionUtils.getStackTrace(ex));
        }

        // exit the step
        return RepeatStatus.FINISHED;

    }
}
