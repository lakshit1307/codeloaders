package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.FileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FileStatusRepository extends JpaRepository<FileStatus, String> {


    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE CODE_FILE_STATUS SET STATUS=?1 WHERE FILE_NAME=?2 AND SERV_TYPE_CD=?3",nativeQuery = true)
     void updateStatusByFileNameAndCodeType(String status, String fileName, String codeType);

    @Query(value ="SELECT * FROM CODE_FILE_STATUS WHERE SERV_TYPE_CD=?1" ,nativeQuery = true)
     FileStatus getFileTypeDetails(String fileType);
}
