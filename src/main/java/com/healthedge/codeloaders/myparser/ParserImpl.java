package com.healthedge.codeloaders.myparser;

import com.healthedge.codeloaders.util.CodeLoaderProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParserImpl extends AbstractParser {

    private Properties properties = CodeLoaderProperty.getInstance().getProperties();

    private final String SKIP_FIRST_LINE_SUFFIX = ".mapper.skipfirstline";
    private final String MAPPER_TYPE_SUFFIX = ".mapper.type";
    private final String MAPPER_DELIMITER_SUFFIX = ".mapper.delimiter";

    @Override
    public List<Map<String, String>> parse(MyFileMetaData fileMetaData) throws Exception {
        String fileType = fileMetaData.getFileType();
        String filePath = fileMetaData.getFilePath();

        Properties properties = CodeLoaderProperty.getInstance().getPropertiesByFileType(fileType);

        String propertyKey = fileType + SKIP_FIRST_LINE_SUFFIX;
        boolean skipFirstLine = new Boolean(properties.getProperty(propertyKey,"true"));

        //Read if skip first line true
        List<String> lines = readLines(filePath, skipFirstLine);

        propertyKey = fileType + MAPPER_TYPE_SUFFIX;
        String mapperType = properties.getProperty(propertyKey);

        List<Map<String, String>> result = new ArrayList<>();
        lines.forEach(line -> {
            if (mapperType.equalsIgnoreCase("Column")) {
                String delimiterPropName = fileType + MAPPER_DELIMITER_SUFFIX;
                String delimiter = properties.getProperty(delimiterPropName);
                String[] fields = read(line, delimiter);
                result.add(createMapBasedOnColumns(fileType, properties, fields));
            } else if (mapperType.equalsIgnoreCase("line")) {
                result.add(createMapBasedOnLines(fileType, properties, line));
            }
        });

        return result;
    }


    private Map<String, String> createMapBasedOnColumns(String fileType, Properties properties, String[] fields) {
        Map<String, String> map = new HashMap<>();
        String propertyPrefix = fileType + ".mapperinfo.";
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(propertyPrefix)) {
                int index = Integer.parseInt(properties.getProperty(key));
                String value = fields[index];
                String mapKey = StringUtils.substring(key, key.lastIndexOf(".") + 1);
                map.put(mapKey, value);
            }
        }
        map.put("fileType", fileType);
        return map;
    }

    private Map<String, String> createMapBasedOnLines(String fileType, Properties properties, String line) {
        Map<String, String> map = new HashMap<>();
        String propertyPrefix = fileType + ".mapperinfo.";
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(propertyPrefix)) {
                String keyValue = properties.getProperty(key);
                String[] values = keyValue.split(",");
                int startIndex = Integer.parseInt(values[0]);
                int endIndex = Integer.parseInt(values[1]);
                String mapKey = StringUtils.substring(key, key.lastIndexOf(".") + 1);
                String value = line.substring(startIndex, endIndex);
                map.put(mapKey, value);
            }
        }
        map.put("fileType", fileType);
        return map;
    }

    private String[] read(String line, String delimiter) {
        return line.split(String.valueOf(delimiter),-1);
    }
}
