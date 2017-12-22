package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, String> {

    @Query(value = "SELECT * FROM T_DIAGNOSIS WHERE DIAG_TYPE_CD=?1 AND VERSION_END=?2 AND ACTION!=?3", nativeQuery = true)
    List<Diagnosis> getDiagnosisCodesByCodeTypeForVersionWithoutAction(String codeType, Long versionEnd, String action);

    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE T_DIAGNOSIS SET VERSION_END = ?1 WHERE VERSION_END = ?2 AND DIAG_CD NOT IN (?3)", nativeQuery = true)
    void updateLatestVersionForProcessedFile (Long currentVersion, Long previousVersion, List<String> codes);

    @Query(value = "SELECT * FROM T_DIAGNOSIS WHERE DIAG_TYPE_CD=?1 AND VERSION_START>?2 AND VERSION_END=?3", nativeQuery = true)
    List<Diagnosis> getDeltaCodes(String codeType, Long currPayorVersion, Long payorRequestedVersion);

}
