package com.healthedge.codeloaders.service.file.parser;


import com.healthedge.codeloaders.service.file.model.FileMetadata;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.file.util.CodeLoaderPropertyUtil;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileParser {

    private static final Properties properties = CodeLoaderPropertyUtil.getInstance().getProperties();
    private static final String CODE=".CODE";
    private static final String LONG_DESC=".LONG.DESC";
    private static final String SHORT_DESC=".SHORT.DESC";
    private static final String FULL_DESC=".FULL.DESC";
    private static final String DELEMITER=".DELIMITER";

    public Map<String, Service> parse(String filePath) throws IOException {
        Map<String, Service> result = new HashMap<String, Service>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";

        String fileName = FilenameUtils.getName(filePath);

        FileMetadata fileMetadata = new FileMetadata(fileName);
        String codeType = fileMetadata.getFileType();


        String delimiter = properties.getProperty(codeType + DELEMITER);

        String[] fileHeaders = br.readLine().split(String.valueOf(delimiter)); //to skip reading the first line(Header section) in the following steps


        String[] fields;
        while ((line = br.readLine()) != null) {
            if (line.length() > 0) {
                fields = line.split(String.valueOf(delimiter),-1);
                Service pojo = new Service();

                if (Integer.parseInt(properties.getProperty(codeType + CODE)) < fields.length)
                    pojo.setServiceCode(fields[Integer.parseInt(properties.getProperty(codeType + CODE))]);

                if (Integer.parseInt(properties.getProperty(codeType + FULL_DESC)) < fields.length)
                    pojo.setServiceAlternateDesciption(fields[Integer.parseInt(properties.getProperty(codeType + FULL_DESC))]);

                if (Integer.parseInt(properties.getProperty(codeType + LONG_DESC)) < fields.length)
                    pojo.setServiceLongDesciption(fields[Integer.parseInt(properties.getProperty(codeType + LONG_DESC))]);

                if (Integer.parseInt(properties.getProperty(codeType + SHORT_DESC)) < fields.length)
                    pojo.setServiceShortDesciption(fields[Integer.parseInt(properties.getProperty(codeType + SHORT_DESC))]);

                result.put(pojo.getServiceCode(), pojo);
            }

        }

        return result;

    }
}




