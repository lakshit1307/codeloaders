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
                    Map<String, List<Service>> diffRecords = diffCreator.diff(filePath,record);
                    //Persist Data

                    //CREATE entities
                    List<Service> createList=new ArrayList<>();
                    if(diffRecords.containsKey(DiffCreator.CREATE_ACTION)){
                        createList=diffRecords.get(DiffCreator.CREATE_ACTION);
                    }


                    for (Service service : createList) {
                        serviceDao.save(service);
                    }

                    //UPDATE entities
                    List<Service> updateList=new ArrayList<>();
                    if(diffRecords.containsKey(DiffCreator.APPEND_ACTION)){
                        updateList=diffRecords.get(DiffCreator.APPEND_ACTION);
                    }


                    for (Service service : updateList) {
                        serviceDao.update(service);
                    }

                    //TERMINATE entities
                    List<Service> terminateList=new ArrayList<>();
                    if(diffRecords.containsKey(DiffCreator.TERMINATE_ACTION)){
                        terminateList=diffRecords.get(DiffCreator.TERMINATE_ACTION);
                    }


                    for (Service service : terminateList) {
                        serviceDao.terminate(service);
                    }


                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    //TODO: Log the error with file name
                }

            }


        }
    }



}
