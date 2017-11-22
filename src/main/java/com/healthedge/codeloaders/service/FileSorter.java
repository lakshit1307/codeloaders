package com.healthedge.codeloaders.service;

import com.healthedge.codeloaders.util.FileNameDateComparator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class FileSorter {

    public List<String> sortFilesInDirectory (String directoryPath) {
        List<String> fileNames = new ArrayList<String>();
        File filePath = new File(directoryPath);
        if (filePath.exists() && filePath.isDirectory()) {
            fileNames = Arrays.asList(filePath.list());
            Collections.sort(fileNames, new FileNameDateComparator());
        }

        return fileNames;
    }
}
