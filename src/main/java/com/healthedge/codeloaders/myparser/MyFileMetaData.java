package com.healthedge.codeloaders.myparser;

import com.healthedge.codeloaders.util.CodeLoaderProperty;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class MyFileMetaData {

    private static final String FILE_DELIMITER = "_";

    private String filePath;
    private String fileType;
    private DateTime fileDate;
    private String baseFileName;
    private long fileVersion;
    private String fileTypeCd;
    private Date effectiveStartDate;
    private Date effectiveEndDate;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public MyFileMetaData (final String fileType, final String filePath) throws Exception {
        this.fileType = fileType.toLowerCase(Locale.getDefault());
        this.filePath = filePath;
        this.baseFileName = FilenameUtils.getBaseName(filePath);
        identifyVersion();
        identifyFileTypeCd();
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

    public String getFileTypeCd() {
        return fileTypeCd;
    }

    public Date getEffectiveStartDate() {
        return this.effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    private void identifyVersion () throws Exception {
        int index = this.baseFileName.lastIndexOf(FILE_DELIMITER);
        if (index != -1) {
            String date = this.baseFileName.substring(index + 1);
            this.fileDate = new DateTime(sdf.parse(date))
                    .withHourOfDay(0)
                    .withMinuteOfHour(0)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0);
            this.fileVersion = this.fileDate.getMillis();
            this.effectiveStartDate = new DateTime(this.fileDate)
                    .withDayOfMonth(this.fileDate.dayOfMonth().withMinimumValue().getDayOfMonth()).toDate();
            this.effectiveEndDate = new DateTime(this.fileDate)
                    .withDayOfMonth(this.fileDate.dayOfMonth().withMaximumValue().getDayOfMonth()).toDate();
        }
    }

    private void identifyFileTypeCd () {
        Properties properties = CodeLoaderProperty.getInstance().getPropertiesByFileType(this.fileType);
        String propertyName = this.fileType + CodeLoaderProperty.FILE_TYPE_CD_SUFFIX;
        String property = properties.getProperty(propertyName);
        if (StringUtils.isNotEmpty(property) && StringUtils.isNotBlank(property)) {
            this.fileTypeCd = property.trim();
        }
    }
}
