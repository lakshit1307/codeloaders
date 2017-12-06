package com.healthedge.codeloaders.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.net.MalformedURLException;

@Configuration
public class SpringBatchPartitionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBatchPartitionConfig.class);

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean(name = "stagingJob")
    public Job stagingJob() throws UnexpectedInputException, MalformedURLException, ParseException {
        return jobs.get("stagingJob")
                .start(stagingStep())
                .build();
    }

    @Bean
    public Step stagingStep() throws UnexpectedInputException, MalformedURLException, ParseException {
        return steps.get("stagingStep")
                .partitioner("stagingSlaveStep", stagingPartitioner())
                .step(stagingSlaveStep())
                .gridSize(20)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public StagingPersistenceStepPartitioner stagingPartitioner() {
        StagingPersistenceStepPartitioner persistentingStepPartitioner = new StagingPersistenceStepPartitioner();
        return persistentingStepPartitioner;
    }

    @Bean
    public Step stagingSlaveStep() throws UnexpectedInputException, MalformedURLException, ParseException {
        return steps.get("stagingSlaveStep").tasklet(stagingTasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet stagingTasklet() throws UnexpectedInputException, ParseException {
        Tasklet tasklet = new StagingPersistenceTasklet();
        return tasklet;
    }

    @Bean
    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setMaxPoolSize(5);
//        taskExecutor.setCorePoolSize(5);
//        taskExecutor.setQueueCapacity(5);
//        taskExecutor.afterPropertiesSet();
//        return taskExecutor;

        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }
}
