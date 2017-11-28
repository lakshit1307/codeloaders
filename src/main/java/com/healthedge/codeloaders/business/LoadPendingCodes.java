package com.healthedge.codeloaders.business;

import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.ClientPersistenceService;
import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.FileParser;
import com.healthedge.codeloaders.service.FileSorter;
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
            final List<String> sortedFileNames = fileSorter.sortFilesInDirectory(directoryPath);
            for (final String file : sortedFileNames) {
                final String filePath = directoryPath + File.separator + file;

                try {
                    final Map<String, Service> record = fileParser.parse(filePath);
                    final Map<String, List<Service>> diffRecords = diffCreator.diff(filePath,record);
                    //Persist Data
                    persistData(diffRecords);


                } catch (Exception ex) { //NOPMD
                    System.out.println(ex.getMessage());
                    //TODO: Log the error with file name
                }

            }
        }
        clientService.persistToClients();
    }

    private void persistData(final Map<String, List<Service>> diffRecords) {
        //CREATE entities
        if(diffRecords.containsKey(DiffCreator.CREATE_ACTION)){
            final List<Service> createList = diffRecords.get(DiffCreator.CREATE_ACTION);
            for (final Service service : createList) {
                serviceDao.save(service);
            }
        }


        //UPDATE entities
        if(diffRecords.containsKey(DiffCreator.APPEND_ACTION)){
            final List<Service> updateList=diffRecords.get(DiffCreator.APPEND_ACTION);
            for (final Service service : updateList) {
                serviceDao.update(service);
            }
        }


        //TERMINATE entities
        if(diffRecords.containsKey(DiffCreator.TERMINATE_ACTION)){
            final List<Service> terminateList = diffRecords.get(DiffCreator.TERMINATE_ACTION);
            for (final Service service : terminateList) {
                serviceDao.terminate(service);
            }
        }
    }
}
