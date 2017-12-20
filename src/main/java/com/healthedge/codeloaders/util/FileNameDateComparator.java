package com.healthedge.codeloaders.util;


import com.healthedge.codeloaders.dto.FileMetadata;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

@SuppressWarnings({"PMD.LawOfDemeter","PMD.DataflowAnomalyAnalysis"})
public class FileNameDateComparator implements Comparator<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileNameDateComparator.class);

    private String fileType;
    public FileNameDateComparator (String fileType) {
        this.fileType = fileType;
        LOGGER.info("FileNameDateComparator class initialized");
    }

    public int compare(final String o1, final String o2) {
        int compare = 0;
        try {
            final MyFileMetaData file1 = new MyFileMetaData(fileType, o1);
            final MyFileMetaData file2 = new MyFileMetaData(fileType, o2);

            compare = file1.getFileDate().compareTo(file2.getFileDate());
        } catch (Exception ex) { //NOPMD
            LOGGER.error("Error parsing the filename {}", ExceptionUtils.getStackTrace(ex));
        }

        return compare;
    }

}
