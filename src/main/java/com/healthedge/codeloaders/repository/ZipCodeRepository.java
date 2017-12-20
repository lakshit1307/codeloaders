package com.healthedge.codeloaders.repository;


import com.healthedge.codeloaders.entity.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZipCodeRepository extends JpaRepository<ZipCode,Long> {

    @Query(value = "SELECT * FROM ZIP WHERE VERSION_END=?1", nativeQuery = true)
    List<ZipCode> getZipCodesForVersion(Long versionEnd);
}
