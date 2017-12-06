package com.healthedge.codeloaders.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.MalformedURLException;
import java.util.logging.Logger;

@Configuration
@EnableBatchProcessing
public class SpringBatchPartitionConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean(name = "partitionerJob")
    public Job partitionerJob() throws UnexpectedInputException, MalformedURLException, ParseException {
        return jobs.get("partitionerJob")
                .start(partitionStep())
                .build();
    }

    @Bean
    public Step partitionStep() throws UnexpectedInputException, MalformedURLException, ParseException {
        return steps.get("partitionStep")
                .partitioner("slaveStep", partitioner())
                .step(slaveStep())
                .gridSize(5)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public PersistentingStepPartitioner partitioner() {
        return new PersistentingStepPartitioner();
    }

    @Bean
    public Step slaveStep() throws UnexpectedInputException, MalformedURLException, ParseException {
        return steps.get("slaveStep").tasklet(tasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet tasklet () throws UnexpectedInputException, ParseException {
        Tasklet tasklet = new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String value = (String) chunkContext
                        .getStepContext()
                        .getStepExecution()
                        .getExecutionContext()
                        .get("action");

                System.out.println("********************* execution context: " + value);
                // exit the step
                return RepeatStatus.FINISHED;
            }
        };
        return tasklet;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    @StepScope
    public FlatFileItemReader itemReader (@Value("#{stepExecutionContext[action]}") String action) throws UnexpectedInputException, ParseException {
        FlatFileItemReader reader = new FlatFileItemReader<>();
        return reader;
    }

}
