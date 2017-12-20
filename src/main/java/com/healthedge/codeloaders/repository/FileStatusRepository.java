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
    @Query(value = "UPDATE CODE_FILE_STATUS SET STATUS=?1 WHERE VERSION=?2 AND FILE_TYPE=?3 AND STATUS=?4",nativeQuery = true)
	void updateStatusByFileNameAndCodeType(String status, Long fileVersion, String fileType, String currStatus);

    @Query(value ="SELECT * FROM CODE_FILE_STATUS WHERE FILE_TYPE=?1 AND TX_DATE=(SELECT MAX(TX_DATE) FROM CODE_FILE_STATUS)" ,nativeQuery = true)
     FileStatus getFileTypeDetailsForLatestVersion(String fileType);
}
