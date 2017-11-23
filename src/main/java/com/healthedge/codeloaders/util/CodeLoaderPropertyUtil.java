package com.healthedge.codeloaders.util;

import java.io.FileInputStream;
import java.util.Properties;

public class CodeLoaderPropertyUtil {

    private static final String CODE_LOADER_PROPERTY_FILE_PATH = "C:\\Users\\laksh\\Documents\\Code\\code_1.1\\codeloaders\\codeloaders\\src\\main\\resources\\codeType.properties";
    private Properties properties = new Properties();
    private static CodeLoaderPropertyUtil ourInstance = new CodeLoaderPropertyUtil();

    public static CodeLoaderPropertyUtil getInstance() {
        return ourInstance;
    }

    private CodeLoaderPropertyUtil() {
        try {
            FileInputStream input = new FileInputStream(CODE_LOADER_PROPERTY_FILE_PATH);
            properties.load(input);
        } catch (Exception ex) {
            //TODO: Log error
        }
    }

    public Properties getProperties () {
        return properties;
    }

}
