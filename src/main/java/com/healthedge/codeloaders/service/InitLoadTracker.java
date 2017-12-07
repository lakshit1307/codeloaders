package com.healthedge.codeloaders.service;

import com.healthedge.codeloaders.dao.FileStatusDao;
import com.healthedge.codeloaders.entity.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InitLoadTracker {


    @Autowired
    private FileStatusDao fileStatusDao;

    @Autowired
    private DiffCreator diffCreator;

    @Autowired
    private FileParser fileParser;

    @Autowired
    private FileSorter fileSorter;

    @Value("${basedata.path}")
    private String baseData;

    public static final String EOF="END OF DIRECTORY";


    public FileStatus dbFileStatus(String fileType) {

        FileStatus codeFileStatus;
        codeFileStatus = fileStatusDao.getFileTypeDetails(fileType);
        return codeFileStatus;

    }

    public String startFileProcessingOf(String fileType)  {

        String startFile="";
        final String directoryPath = baseData + File.separator + fileType;
        final List<String> sortedFileNames = fileSorter.sortFilesInDirectory(directoryPath);
        FileStatus codeFileStatus = dbFileStatus(fileType);
        if(codeFileStatus!=null){

            if (codeFileStatus.getStatus().equals(FileStatus.FAILURE)){

                String currentFile = codeFileStatus.getFileName();
                for (int i = 0; i < sortedFileNames.size(); ++i) {

                    if (sortedFileNames.get(i).equals(currentFile)) {

                        final String filePathToMap = directoryPath + File.separator + sortedFileNames.get(i - 1);
                        Map<String, com.healthedge.codeloaders.entity.Service> map = null;
                        try {
                            map = fileParser.parse(filePathToMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        diffCreator.setPreviousData(map);
                        startFile = sortedFileNames.get(i);
                        break;

                    }
                }

            }
            else if(codeFileStatus.getStatus().equals(FileStatus.PERSISTED)){

                String currentFile = codeFileStatus.getFileName();
                for (int i = 0; i < sortedFileNames.size(); ++i) {
                    if (sortedFileNames.get(i).equals(currentFile)) {

                        final String fileToMap = directoryPath + File.separator + sortedFileNames.get(i);
                        Map<String, com.healthedge.codeloaders.entity.Service> map = null;
                        try {
                            map = fileParser.parse(fileToMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        diffCreator.setPreviousData(map);

                        if((i+1)<sortedFileNames.size()){
                            startFile = sortedFileNames.get(i + 1); //need some modification here, if all files are already persisted,Array issue
                        }
                        else{
                            startFile=EOF;
                        }
                        break;

                    }

                }

            }

        }
        else{
            startFile=sortedFileNames.get(0);
        }
        return startFile;
    }
}
