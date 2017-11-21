package com.healthedge.codeloaders.service.file.model;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;

import java.util.StringTokenizer;

public class FileMetadata {

    private static final String FILE_DELIMITER = "_";

    private String provider;
    private String fileType;
    private DateTime fileDate;
    private String fileNameWithExt;
    private String baseFileName;

    public FileMetadata (String fileName) {
        fileNameWithExt = fileName;

        baseFileName = FilenameUtils.getBaseName(fileName);

        StringTokenizer st = new StringTokenizer(baseFileName, FILE_DELIMITER);

        if (st.countTokens() == 4) {
            provider = st.nextToken();
            fileType = st.nextToken();

            String year = st.nextToken();
            String month = st.nextToken();

            fileDate = prepareDateStartingFromFirstOfMonth (year, month);
        }
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

    private DateTime prepareDateStartingFromFirstOfMonth (String year, String month) {
        DateTime retValue = new DateTime(Integer.parseInt(year), Integer.parseInt(month), 01,0,0);

        return retValue;
    }


}
