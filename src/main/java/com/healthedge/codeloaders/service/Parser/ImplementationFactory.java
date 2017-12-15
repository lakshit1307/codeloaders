package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.service.FileParser;
import com.healthedge.codeloaders.util.CodeLoaderProperty;
import com.healthedge.codeloaders.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ImplementationFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImplementationFactory.class);

    @Autowired
    private ApplicationContext applicationContext;

    private Properties properties;

    public ImplementationFactory () {
        LOGGER.info("ImplementationFactory class initialized");
        properties = CodeLoaderProperty.getInstance().getProperties();
    }

    public Parser getParser (String fileType) throws Exception {
        Class clazz = Class.forName(getParserImplementationClassName(fileType));
        return (Parser) applicationContext.getBean(clazz);
    }

    private String getParserImplementationClassName (String fileType) throws Exception {
        String propertyName = new StringBuilder().append(fileType).append(CodeLoaderProperty.PROP_NAME_DELIMITER)
                .append(CodeLoaderProperty.PARSER_CLASS_SUFFIX).toString();
        String parserImplClass = properties.getProperty(propertyName);
        if (StringUtils.isEmpty(parserImplClass) || StringUtils.isBlank(parserImplClass)) {
            throw new Exception("Parser Class property not set for fileType " + fileType);
        }
        return parserImplClass;
    }
}
