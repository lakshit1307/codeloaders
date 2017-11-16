package com.healthedge.codeloaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class CodeloadersApplication {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeloadersApplication.class);

	public static void main(String[] args) {
		LOGGER.debug("sdsdsd");
		SpringApplication.run(CodeloadersApplication.class, args);
	}
}
