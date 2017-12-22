package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.dao.BaseDao;
import com.healthedge.codeloaders.dao.ClientBaseDao;
import com.healthedge.codeloaders.service.Transformer.Transformer;
import com.healthedge.codeloaders.util.CodeLoaderProperty;
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

    public Transformer getTransformer (String fileType) throws Exception {
        String propertyName = fileType + CodeLoaderProperty.TRANSFORMER_CLASS_SUFFIX;
        Class clazz = Class.forName(getPropertyValue(propertyName));
        return (Transformer) applicationContext.getBean(clazz);
    }

    public BaseDao getDao (String fileType) throws Exception {
        String propertyName = fileType.toLowerCase() + CodeLoaderProperty.DAO_CLASS_SUFFIX;
        Class clazz = Class.forName(getPropertyValue(propertyName));
        return (BaseDao) applicationContext.getBean(clazz);
    }

    public ClientBaseDao getClientDao (String fileType) throws Exception {
        String propertyName = fileType.toLowerCase() + CodeLoaderProperty.CLIENT_DAO_CLASS_SUFFIX;
        Class clazz = Class.forName(getPropertyValue(propertyName));
        return (ClientBaseDao) applicationContext.getBean(clazz);
    }

    private String getPropertyValue(String propertyName) throws Exception {
        String implClassName = properties.getProperty(propertyName);
        if (StringUtils.isEmpty(implClassName) || StringUtils.isBlank(implClassName)) {
            throw new Exception("Property " + propertyName + " not set.");
        }
        return implClassName;
    }
}
