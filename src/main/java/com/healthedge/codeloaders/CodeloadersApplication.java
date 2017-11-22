package com.healthedge.codeloaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.healthedge.codeloaders.business.LoadPendingCodes;


@ComponentScan(basePackages = "com.healthedge.*")
@EntityScan( basePackages = "com.healthedge.codeloaders.entity" )
@EnableJpaRepositories("com.healthedge.codeloaders.repository")
@SpringBootApplication
public class CodeloadersApplication {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeloadersApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting Code Loader Application");
		ConfigurableApplicationContext  context = SpringApplication.run(CodeloadersApplication.class, args);

        LOGGER.info("Preparing any pending code updates");
		context.getBean(LoadPendingCodes.class).startProcess();
        LOGGER.info("Persisted all pending code updates");
	}


}
