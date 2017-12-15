package com.healthedge.codeloaders.service;

import com.healthedge.codeloaders.util.CodeLoaderProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class FileTypeOrdering {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTypeOrdering.class);

    private Properties properties;
    private List<String> fileTypes = new ArrayList<>();

    public FileTypeOrdering () {
        LOGGER.info("FileTypeOrdering class initialized");
        deriveFileTypes();
    }

    public List<String> getFileTypes ()  {
        return fileTypes;

    }

    private void deriveFileTypes () {
        String property = CodeLoaderProperty.getInstance().getProperty(CodeLoaderProperty.FILE_TYPE_ORDERING);
        String[] files = property.split(CodeLoaderProperty.PROP_VALUES_DELIMITER);

        for (String file : files) {
            fileTypes.add(file.trim());
        }
    }

}
