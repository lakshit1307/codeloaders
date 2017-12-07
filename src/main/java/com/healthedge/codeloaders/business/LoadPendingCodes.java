package com.healthedge.codeloaders.business;

import com.healthedge.codeloaders.dao.FileStatusDao;
import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.FileStatus;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LoadPendingCodes {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadPendingCodes.class);

    @Value("${basedata.path}")
    private String baseData;

    @Autowired
    private FileSorter fileSorter;

    @Autowired
    private DiffCreator diffCreator;

    @Autowired
    private FileParser fileParser;

    @Autowired
    private ServiceDao serviceDao;
    
    @Autowired
    private ClientPersistenceService clientService;

    @Autowired
    private FileStatusDao fileStatusDao;

    private FileStatus fileStatus=new FileStatus();

    @Autowired
    private InitLoadTracker initLoadTracker;


    public LoadPendingCodes () {
        LOGGER.info("LoadPendingCodes class initialized");
    }


    //TODO: get data from db or config
    private List<String> getFileTypes () {
        final List<String> fileTypes = new ArrayList<String>();
        fileTypes.add("CPT");
        fileTypes.add("HCPCS");

        return fileTypes;
    }


    public void startProcess() {
        for (final String fileType : getFileTypes()) {

            diffCreator.flushPreviousData();
            final String directoryPath = baseData + File.separator + fileType;
            String startFile= initLoadTracker.startFileProcessingOf(fileType);
            if(!startFile.equals(initLoadTracker.EOF)){
                final List<String> sortedFileNames = fileSorter.sortFilesInDirectory(directoryPath);
                for (int i=sortedFileNames.indexOf(startFile);i<sortedFileNames.size();++i ) {
                    final String filePath = directoryPath + File.separator + sortedFileNames.get(i);
                    fileStatus.setCodeType(fileType);
                    fileStatus.setFileName(sortedFileNames.get(i));
                    processFile(filePath);
                }
            }

        }
        //clientService.persistToClients();

    }

    private Boolean persistData(final Map<String, List<Service>> diffRecords) {

        Boolean flag=false;
        //CREATE entities
        if(diffRecords.containsKey(DiffCreator.CREATE_ACTION)){
            final List<Service> createList = diffRecords.get(DiffCreator.CREATE_ACTION);
            for (final Service service : createList) {
                Boolean tempFlag=serviceDao.save(service);
                flag=flag||tempFlag;
            }
        }


        //UPDATE entities
        if(diffRecords.containsKey(DiffCreator.APPEND_ACTION)){
            final List<Service> updateList=diffRecords.get(DiffCreator.APPEND_ACTION);
            for (final Service service : updateList) {
                Boolean tempFlag=serviceDao.update(service);
                flag=flag||tempFlag;
            }
        }


        //TERMINATE entities
        if(diffRecords.containsKey(DiffCreator.TERMINATE_ACTION)){
            final List<Service> terminateList = diffRecords.get(DiffCreator.TERMINATE_ACTION);
            for (final Service service : terminateList) {
                Boolean tempFlag=serviceDao.terminate(service);
                flag=flag||tempFlag;
            }
        }
        return flag;
    }
    private void processFile(String filePath){

        try {
            fileStatus.setStatus(FileStatus.IN_PROGRESS);
            fileStatusDao.save(fileStatus);

            final Map<String, Service> record = fileParser.parse(filePath);
            final Map<String, List<Service>> diffRecords = diffCreator.diff(filePath,record);

            //Persisting data here

            Boolean flag=persistData(diffRecords);
            if(flag){
                fileStatus.setStatus(FileStatus.PERSISTED);
                fileStatusDao.updateStatus(fileStatus);

            }
            else {
                fileStatus.setStatus(FileStatus.FAILURE);
                fileStatusDao.updateStatus(fileStatus);
            }


        } catch (Exception ex) { //NOPMD
            System.out.println(ex.getMessage());
            //TODO: Log the error with file name
        }

    }
}
