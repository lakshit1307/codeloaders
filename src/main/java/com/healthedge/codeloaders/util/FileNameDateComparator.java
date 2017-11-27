package com.healthedge.codeloaders.util;


import com.healthedge.codeloaders.dto.FileMetadata;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

@SuppressWarnings({"PMD.LawOfDemeter","PMD.DataflowAnomalyAnalysis"})
public class FileNameDateComparator implements Comparator<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileNameDateComparator.class);

    public FileNameDateComparator () {
        LOGGER.info("FileNameDateComparator class initialized");
    }

    public int compare(final String o1, final String o2) {
        int compare = 0;
        try {
            final FileMetadata file1 = new FileMetadata(o1);
            final FileMetadata file2 = new FileMetadata(o2);

            compare = file1.getFileDate().compareTo(file2.getFileDate());
        } catch (Exception ex) { //NOPMD
            LOGGER.error("Error parsing the filename {}", ExceptionUtils.getStackTrace(ex));
        }

        return compare;
    }

}
