package com.healthedge.codeloaders.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Properties;

@Service
public class CodeLoaderProperty {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeLoaderProperty.class);

    public static final String PROP_NAME_DELIMITER = ".";
    public static final String DEPENDENCIES_DELIMITER = ",";
    public static final String DEPENDENCIES_PROPERTY_NAME = "dependencies";

    private final Properties properties = new Properties();

    private static CodeLoaderProperty ourInstance = new CodeLoaderProperty();

    public static CodeLoaderProperty getInstance() {
        return ourInstance;
    }

    private CodeLoaderProperty() {
        try {
            InputStream input = CodeLoaderProperty.class.getClassLoader().getResourceAsStream("codeType.properties");
            properties.load(input);
        } catch (Exception ex) { //NOPMD
            LOGGER.error("Error parsing properties file {}", ExceptionUtils.getStackTrace(ex));
        }
    }

    public Properties getProperties () {
        return properties;
    }
}
