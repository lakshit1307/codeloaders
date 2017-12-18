package com.healthedge.codeloaders.myparser;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyFileMetaData {

    private static final String FILE_DELIMITER = "_";

    private String filePath;
    private String fileType;
    private DateTime fileDate;
    private String baseFileName;
    private long fileVersion;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public MyFileMetaData (final String fileType, final String filePath) throws Exception {
        this.fileType = fileType.toLowerCase(Locale.getDefault());
        this.filePath = filePath;
        this.baseFileName = FilenameUtils.getBaseName(filePath);
        identifyVersion();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public DateTime getFileDate() {
        return fileDate;
    }

    public String getBaseFileName() {
        return baseFileName;
    }

    public long getFileVersion() {
        return fileVersion;
    }

    private DateTime prepareDateStartingFromFirstOfMonth () {
        return this.fileDate;
    }

    private void identifyVersion () throws Exception {
        int index = this.baseFileName.lastIndexOf(FILE_DELIMITER);
        if (index != -1) {
            String date = this.baseFileName.substring(index);
            this.fileDate = new DateTime(sdf.parse(date))
                    .withHourOfDay(0)
                    .withMinuteOfHour(0)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0);
            this.fileVersion = this.fileDate.getMillis();
        }
    }
}
