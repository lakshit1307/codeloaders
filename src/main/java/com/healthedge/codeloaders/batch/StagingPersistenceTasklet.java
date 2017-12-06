package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.DiffCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class StagingPersistenceTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(StagingPersistenceTasklet.class);

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext executionContext = chunkContext.getStepContext()
                .getStepExecution().getExecutionContext();

        String action = executionContext.getString(StagingPersistenceStepPartitioner.ACTION);
        Service service = (Service) executionContext.get(StagingPersistenceStepPartitioner.ITEM);

        LOGGER.info("Thread [{}] processing action [{}] having code [{}]", Thread.currentThread().getName(), action,
                service.getServiceCode());

        if (action == DiffCreator.CREATE_ACTION) {
            LOGGER.info("Persistence action Create action on code [{}] with action [{}]", service.getServiceCode(), service.getAction());
            serviceDao.save(service);
        } else if (action == DiffCreator.APPEND_ACTION) {
            LOGGER.info("Persistence action Append action on code [{}] with action [{}]", service.getServiceCode(), service.getAction());
            serviceDao.update(service);
        } else if (action == DiffCreator.TERMINATE_ACTION) {
            LOGGER.info("Persistence action Terminate action on code [{}] with action [{}]", service.getServiceCode(), service.getAction());
            serviceDao.terminate(service);
        }

        // exit the step
        return RepeatStatus.FINISHED;

    }
}
