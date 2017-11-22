package com.healthedge.codeloaders.business;

import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.FileParser;
import com.healthedge.codeloaders.service.FileSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LoadPendingCodes {

    //TODO: make it configurable
    private String basedata = "C:\\basedata";

    @Autowired
    private FileSorter fileSorter;

    @Autowired
    private DiffCreator diffCreator;

    @Autowired
    private FileParser fileParser;

    @Autowired
    private ServiceDao serviceDao;


    //TODO: get data from db or config
    private List<String> getFileTypes () {
        List<String> fileTypes = new ArrayList<String>();
        fileTypes.add("CPT");
        fileTypes.add("HCPCS");

        return fileTypes;
    }


    public void startProcess() {
        for (String fileType : getFileTypes()) {
            diffCreator.flushPreviousData();
            String directoryPath = basedata + File.separator + fileType;
            List<String> sortedFileNames = fileSorter.sortFilesInDirectory(directoryPath);
            for (String file : sortedFileNames) {
                String filePath = directoryPath + File.separator + file;
                try {
                    Map<String, Service> record = fileParser.parse(filePath);
                    Map<String, List<Service>> diffRecords = diffCreator.diff(record);
                    //Persist Data
                    List<Service> ls=new ArrayList<>();
                    if(diffRecords.containsKey(DiffCreator.CREATE_ACTION)){
                        ls=diffRecords.get(DiffCreator.CREATE_ACTION);
                    }

                    for (Service service : ls) {
                        System.out.println("\n PERSISTING Object :\n"+service.toString());
                        serviceDao.save(service);
                    }


                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    //TODO: Log the error with file name
                }

            }


        }
    }



}
