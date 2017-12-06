package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.business.LoadPendingCodes;
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

public class MyTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyTasklet.class);

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext executionContext = chunkContext.getStepContext()
                .getStepExecution().getExecutionContext();

        String action = executionContext.getString(PersistentingStepPartitioner.ACTION);
        Service service = (Service) executionContext.get(PersistentingStepPartitioner.ITEM);

        LOGGER.info("Thread [{}] processing action [{}] having code [{}]", Thread.currentThread().getName(), action,
                service.getServiceCode());

        if (action == DiffCreator.CREATE_ACTION) {
            serviceDao.save(service);
        } else if (action == DiffCreator.APPEND_ACTION) {
            serviceDao.update(service);
        } else if (action == DiffCreator.TERMINATE_ACTION) {
            serviceDao.terminate(service);
        }

        // exit the step
        return RepeatStatus.FINISHED;

    }
}
