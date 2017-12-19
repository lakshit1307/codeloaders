package com.healthedge.codeloaders.util;

import com.oracle.webservices.internal.api.message.PropertySet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

public class CodeLoaderProperty {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeLoaderProperty.class);

    public static final String PROP_NAME_DELIMITER = ".";
    public static final String PROP_VALUES_DELIMITER = ",";
    public static final String DEPENDENCIES_PROPERTY_NAME = "dependencies";
    public static final String PARSER_CLASS_SUFFIX = "parser.class";
    public static final String TRANSFORMER_CLASS_SUFFIX = ".transformer.class";
    public static final String DAO_CLASS_SUFFIX = ".dao.class";

    public static final String FILE_TYPE_ORDERING = "filetype.ordering";

    private final Properties properties = new Properties();

    private Map<String, Properties> propertyCacheByFileType = new HashMap<>();

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

    public String getProperty (String key) {
        return properties.getProperty(key, null);
    }

    public Properties getPropertiesByFileType (String fileType) {
        if (!propertyCacheByFileType.containsKey(fileType)) {
            loadPropertiesByFileType(fileType);
        }

        return propertyCacheByFileType.get(fileType);
    }

    private void loadPropertiesByFileType (String fileType) {
        Properties properties = new Properties();
        for (String key : this.properties.stringPropertyNames()) {
            if (StringUtils.startsWith(key,fileType)) {
                properties.setProperty(key,this.properties.getProperty(key));
            }
        }
        propertyCacheByFileType.put(fileType, properties);
    }
}
