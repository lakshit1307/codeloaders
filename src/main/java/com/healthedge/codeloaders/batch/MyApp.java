package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.CodeloadersApplication;
import com.healthedge.codeloaders.business.LoadPendingCodes;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.FileSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ComponentScan(basePackages = "com.healthedge.*")
@EnableAutoConfiguration
@SpringBootApplication
@EnableBatchProcessing
public class MyApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyApp.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Code Loader Application");
        LOGGER.info("Preparing any pending code updates");

        ConfigurableApplicationContext  context = SpringApplication.run(MyApp.class, args);
        context.getBean(CodeLoadProcess.class).startProcess();

        LOGGER.info("Persisted all pending code updates");
    }
}
