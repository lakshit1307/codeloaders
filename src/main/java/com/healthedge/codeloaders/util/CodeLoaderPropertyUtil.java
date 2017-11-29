package com.healthedge.codeloaders.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings({"PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal"})
@org.springframework.stereotype.Service
public final class CodeLoaderPropertyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeLoaderPropertyUtil.class);

    private final Properties properties = new Properties();

    public CodeLoaderPropertyUtil() {
        LOGGER.info("CodeLoaderPropertyUtil class loaded");
    }

    @PostConstruct
    public void loadProperties () {
        try {
            InputStream input = CodeLoaderPropertyUtil.class.getClassLoader().getResourceAsStream("codeType.properties");
            properties.load(input);
        } catch (Exception ex) { //NOPMD
            LOGGER.error("Error parsing properties file {}", ExceptionUtils.getStackTrace(ex));
        }
    }

    public Properties getProperties () {
        return properties;
    }


}
