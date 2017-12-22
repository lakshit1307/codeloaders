package com.healthedge.codeloaders.filelistener;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CopyFilesToBasedata {

    public static void copyFiles(String file) throws IOException {
        File source = new File(file.concat("\\current"));
        File destination = new File(file.concat("\\basedata"));
        List<String> folderList = findFoldersInDirectory(source,destination);
        deleteFoldersInSourceDirectory(folderList,source);
        System.out.println(folderList.toString());
    }

    private static void deleteFoldersInSourceDirectory(List<String> folderList,File sourcePath) throws IOException {

        for (String direcory:folderList){
            FileUtils.deleteDirectory(new File(direcory));
        }
        FileUtils.deleteQuietly(new File(sourcePath.toString().concat("\\done.txt")));
    }

    public static List<String> findFoldersInDirectory(File sourcePath, File destinationPath) throws IOException {

        FileFilter directoryFileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };

        File[] directoryListAsFile = sourcePath.listFiles(directoryFileFilter);
        List<String> foldersInDirectory = new ArrayList<String>(directoryListAsFile.length);
        for (File directoryAsFile : directoryListAsFile) {
            try {
                foldersInDirectory.add(directoryAsFile.toString());
                FileUtils.copyDirectoryToDirectory(
                        directoryAsFile,destinationPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return foldersInDirectory;

    }

}

