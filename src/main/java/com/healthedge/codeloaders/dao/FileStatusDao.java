package com.healthedge.codeloaders.dao;


import com.healthedge.codeloaders.entity.FileStatus;
import com.healthedge.codeloaders.repository.ClientServiceRepository;
import com.healthedge.codeloaders.repository.FileStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class FileStatusDao {

    private static final Logger LOGGER=LoggerFactory.getLogger(FileStatusDao.class);

@Autowired
    private FileStatusRepository fileStatusRepository;

    public FileStatusDao(){
    LOGGER.info("FileStatusDao is initalized");
    }

    public void save(FileStatus fileStatus){
        fileStatusRepository.save(fileStatus);
    }

    @Transactional
    public void updateStatus(FileStatus fileStatus){
        fileStatusRepository.updateStatusByFileNameAndCodeType(fileStatus.getStatus(),fileStatus.getFileName(),fileStatus.getCodeType());
    }

    public FileStatus getFileTypeDetails(String fileType) {
        FileStatus fileStatus=fileStatusRepository.getFileTypeDetails(fileType);
        return fileStatus;
    }



   /* public void getFileVersionAndStatusByFileType() {
        fileStatusRepository.
    }*/

    /*public Date getFileDateByCodeType(){

    }

    @Transactional
    public void update(FileStatus fileStatus){
        fileStatusRepository.u(fileStatus.getFileVersion(),fileStatus.getStatus(),fileStatus.getCodeType());
    }
    */


}
