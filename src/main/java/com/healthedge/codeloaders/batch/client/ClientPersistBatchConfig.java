package com.healthedge.codeloaders.batch.client;

import java.net.MalformedURLException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;

@Configuration
//@EnableBatchProcessing
public class ClientPersistBatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean(name = "partitionerJob")
	public Job partitionerJob() throws UnexpectedInputException, MalformedURLException, ParseException {
		return jobBuilderFactory.get("partitioningJob").start(partitionStep()).build();
	}

	@Bean
	public Step partitionStep() throws UnexpectedInputException, MalformedURLException, ParseException {
		return stepBuilderFactory.get("partitionStep").partitioner("slaveStep", partitioner()).step(slaveStep())
				.taskExecutor(taskExecutor()).build();
	}

	@Bean
	@JobScope
	public ClientPersistenceStepPartioner partitioner() {
		ClientPersistenceStepPartioner partitioner = new ClientPersistenceStepPartioner();
		return partitioner;
	}

	@Bean
	public Step slaveStep() throws UnexpectedInputException, MalformedURLException, ParseException {
		return stepBuilderFactory.get("slaveStep").listener(serviceCodeListener()).<BaseEntity, ClientBaseEntity>chunk(20).reader(serviceCodeReader())
				.processor(serviceCodeProcessor()).writer(serviceCodeWriter()).build();
	}
	
	@Bean
	@StepScope
	public ClientPersistenceListener serviceCodeListener() {
		return new ClientPersistenceListener();
	}

	@Bean
	@StepScope
	public ServiceCodeWriter serviceCodeWriter() {
		return new ServiceCodeWriter();
	}

	@Bean
	@StepScope
	public ServiceCodeProcessor serviceCodeProcessor() {
		return new ServiceCodeProcessor();
	}

	@Bean
	@StepScope
	public ServiceCodeReader serviceCodeReader() {
		ServiceCodeReader reader=new ServiceCodeReader();
		return reader;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(10);
		return taskExecutor;
	}

}
