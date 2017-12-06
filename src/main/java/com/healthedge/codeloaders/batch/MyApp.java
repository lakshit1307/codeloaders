package com.healthedge.codeloaders.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

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
        context.getBean(StagingLoadProcess.class).startProcess();

        LOGGER.info("Persisted all pending code updates");
    }
}
