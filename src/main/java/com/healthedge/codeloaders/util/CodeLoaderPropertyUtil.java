package com.healthedge.codeloaders.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

@SuppressWarnings({"PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal"})
public final class CodeLoaderPropertyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeLoaderPropertyUtil.class);

    private static final String CODE_LOADER_PROPERTY_FILE_PATH = "C:\\Users\\laksh\\Documents\\Code\\code_1.1\\codeloaders\\codeloaders\\src\\main\\resources\\codeType.properties";
    private final Properties properties = new Properties();
    private static CodeLoaderPropertyUtil ourInstance = new CodeLoaderPropertyUtil();

    public static CodeLoaderPropertyUtil getInstance() {
        return ourInstance;
    }

    private CodeLoaderPropertyUtil() {
        try {
            FileInputStream input = new FileInputStream(CODE_LOADER_PROPERTY_FILE_PATH);
            properties.load(input);
        } catch (Exception ex) { //NOPMD
            LOGGER.error("Error parsing properties file {}", ExceptionUtils.getStackTrace(ex));
        }
    }

    public Properties getProperties () {
        return properties;
    }

}
