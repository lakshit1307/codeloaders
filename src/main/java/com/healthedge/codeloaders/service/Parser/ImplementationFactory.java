package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.service.Transformer.Transformer;
import com.healthedge.codeloaders.util.CodeLoaderProperty;
import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.classfile.Code;
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
        Class clazz = Class.forName(getTransformerImplClassName(fileType));
        return (Transformer) applicationContext.getBean(clazz);
    }

    private String getTransformerImplClassName(String fileType) throws Exception {
        String propertyName = fileType + CodeLoaderProperty.TRANSFORMER_CLASS_SUFFIX;
        String transformerImplClassName = properties.getProperty(propertyName);
        if (StringUtils.isEmpty(transformerImplClassName) || StringUtils.isBlank(transformerImplClassName)) {
            throw new Exception("Transformer Class property not set for fileType " + fileType);
        }
        return transformerImplClassName;
    }
}
