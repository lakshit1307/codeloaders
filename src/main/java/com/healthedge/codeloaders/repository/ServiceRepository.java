package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ServiceRepository extends JpaRepository<Service,String> {

    @Query(value = "UPDATE T_SERVICE SET SERV_SHORT_DSC=?1,SERV_LONG_DSC=?2,ALT_DSC=?3,ACTION=?4,VERSION=?5 WHERE SERV_CD = ?6",
            nativeQuery = true)
    void update(String serviceShortDesciption,String serviceLongDesciption, String serviceAlternateDesciption,String action,String version,String serviceCode);

    @Query(value = "UPDATE T_SERVICE SET EFF_END_DT=?1,ACTION =?2 WHERE SERV_CD=?3", nativeQuery = true)
    void terminate(Date effectiveEndDate,String action, String serviceCode);
}
