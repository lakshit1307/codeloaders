package com.healthedge.codeloaders.repository;


import com.healthedge.codeloaders.entity.ZipToCarrierLocality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ZipToCarrierLocalityRepository extends JpaRepository<ZipToCarrierLocality,Long>{

    @Query(value = "SELECT * FROM T_ZIP_TO_CARRIER_LOCALITY WHERE  VERSION_END=?2 AND ACTION!=?3", nativeQuery = true)
    List<ZipToCarrierLocality> getZipToCarrierLocalityCodesForVersionWithoutAction(String codeType, Long versionEnd, String action);

    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE T_ZIP_TO_CARRIER_LOCALITY SET VERSION_END = ?1 WHERE VERSION_END = ?2 AND ZIP_CD NOT IN (?3)",
            nativeQuery = true)
    void updateLatestVersionForProcessedFile (Long currentVersion, Long previousVersion, List<String> codes);

}
