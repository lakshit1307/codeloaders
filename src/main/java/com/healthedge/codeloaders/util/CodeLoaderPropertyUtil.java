package com.healthedge.codeloaders.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.Properties;

@SuppressWarnings({"PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal"})
@org.springframework.stereotype.Service
public final class CodeLoaderPropertyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeLoaderPropertyUtil.class);

    @Value("${code.loader.property.path}")
    private String codeLoaderPropertyPath;

    private final Properties properties = new Properties();

    public CodeLoaderPropertyUtil() {
        LOGGER.info("CodeLoaderPropertyUtil class loaded");
    }

    @PostConstruct
    public void loadProperties () {
        try {
            FileInputStream input = new FileInputStream(codeLoaderPropertyPath);
            properties.load(input);
        } catch (Exception ex) { //NOPMD
            LOGGER.error("Error parsing properties file {}", ExceptionUtils.getStackTrace(ex));
        }
    }

    public Properties getProperties () {
        return properties;
    }


}
