package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure,Long> {

    @Query(value = "SELECT * FROM T_PROCEDURE WHERE PROC_TYPE_CD=?2 AND VERSION_END=?2 AND ACTION!=?3", nativeQuery = true)
    List<Procedure> getServiceCodesByCodeTypeForVersionWithoutAction(String codeType, Long versionEnd, String action);

    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE T_PROCEDURE SET VERSION_END = ?1 WHERE VERSION_END = ?2 AND PROC_CD NOT IN (?3)", nativeQuery = true)
    void updateLatestVersionForProcessedFile (Long currentVersion, Long previousVersion, List<String> codes);

}

