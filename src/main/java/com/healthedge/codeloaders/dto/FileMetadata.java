package com.healthedge.codeloaders.dto;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;

import java.util.*;

public class FileMetadata {

    private static final String FILE_DELIMITER = "_";
    private static final int NO_OF_TOKENS = 4;

    private String provider;
    private String fileType;
    private DateTime fileDate;
    private final String fileNameWithExt;
    private final String baseFileName;
    private String year;
    private String month;
    private List<String> dependencies = new ArrayList<>();

    public FileMetadata (final String fileName) {
        fileNameWithExt = fileName;

        baseFileName = FilenameUtils.getBaseName(fileName);

        final StringTokenizer st = new StringTokenizer(baseFileName, FILE_DELIMITER);

        if (st.countTokens() == NO_OF_TOKENS) {
            provider = st.nextToken();
            fileType = st.nextToken();

            year = st.nextToken();
            month = st.nextToken();

            fileDate = prepareDateStartingFromFirstOfMonth ();
            //identifyDependencies();
        }
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getFileNameWithExt() {
        return fileNameWithExt;
    }

    public String getBaseFileName() {
        return baseFileName;
    }

    public String getProvider() {
        return provider;
    }

    public String getFileType() {
        return fileType;
    }

    public DateTime getFileDate() {
        return fileDate;
    }

    private DateTime prepareDateStartingFromFirstOfMonth () {
        return new DateTime(Integer.parseInt(year), Integer.parseInt(month), 01,0,0);
    }

//    private void identifyDependencies () {
//        Properties properties = CodeLoaderProperty.getInstance().getProperties();
//        String dependencyPropName = this.fileType + CodeLoaderProperty.PROP_NAME_DELIMITER + CodeLoaderProperty.DEPENDENCIES_PROPERTY_NAME;
//        String dependency = properties.getProperty(dependencyPropName);
//        if (StringUtils.isNotBlank(dependency) && StringUtils.isNotBlank(dependency)) {
//            String[] split = StringUtils.split(dependency, CodeLoaderProperty.DEPENDENCIES_DELIMITER);
//            dependencies = new ArrayList<>(Arrays.asList(split));
//        }
//    }


}
