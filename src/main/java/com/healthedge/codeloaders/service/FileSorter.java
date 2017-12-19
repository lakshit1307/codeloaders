package com.healthedge.codeloaders.service;

import com.healthedge.codeloaders.util.FileNameDateComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class FileSorter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSorter.class);

    public FileSorter () {
        LOGGER.info("FileSorter class initialized");
    }

    public List<String> sortFilesInDirectory (final String fileType, final String directoryPath) {
        List<String> fileNames = new ArrayList<String>();
        final File filePath = new File(directoryPath);
        if (filePath.exists() && filePath.isDirectory()) {
            fileNames = Arrays.asList(filePath.list());
            Collections.sort(fileNames, new FileNameDateComparator(fileType));
        }

        return fileNames;
    }
}
