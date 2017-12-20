package com.healthedge.codeloaders.repository;


import com.healthedge.codeloaders.entity.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipCode,Long> {


    @Query(value = "SELECT * FROM T_ZIP WHERE  VERSION_END=?2 AND ACTION!=?3", nativeQuery = true)
    List<ZipCode> getZipCodesForVersionWithoutAction(String codeType, Long versionEnd, String action);

}
