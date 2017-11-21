package com.healthedge.codeloaders.service.file.sort;


import com.healthedge.codeloaders.service.file.model.FileMetadata;

import java.util.Comparator;

public class FileNameDateComparator implements Comparator<String> {

    public int compare(String o1, String o2) {
        int compare = 0;
        try {
            FileMetadata file1 = new FileMetadata(o1);
            FileMetadata file2 = new FileMetadata(o2);

            compare = file1.getFileDate().compareTo(file2.getFileDate());
        } catch (Exception ex) {
            //TODO: Log the exception
        }

        return compare;
    }

}
