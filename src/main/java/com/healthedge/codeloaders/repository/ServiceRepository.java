package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE T_SERVICE SET SERV_SHORT_DSC=?1,SERV_LONG_DSC=?2,ALT_DSC=?3,ACTION=?4,VERSION=?5 WHERE SERV_CD = ?6", nativeQuery = true)
	void update(String serviceShortDesciption, String serviceLongDesciption, String serviceAlternateDesciption,
			String action, Long version, String serviceCode);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE T_SERVICE SET EFF_END_DT=?1,ACTION =?2,VERSION=?3 WHERE SERV_CD=?4", nativeQuery = true)
	void terminate(Date effectiveEndDate, String action, Long version, String serviceCode);

	@Query(value = "SELECT VERSION FROM(SELECT * FROM T_SERVICE WHERE SERV_TYPE_CD=?1 ORDER BY VERSION DESC) WHERE ROWNUM=1", nativeQuery = true)
	Date getCodeTypeLatestVersion(String serviceTypeCode);

	@Query(value = "SELECT DISTINCT SERV_TYPE_CD  FROM T_SERVICE", nativeQuery = true)
	ArrayList<String> getDistinctCodeTypes();

	@Query(value = "SELECT * FROM T_SERVICE WHERE VERSION_START >?1 AND SERV_TYPE_CD=?2", nativeQuery = true)
	List<Service> getDeltaCloaderCodes(Date version, String codeType);

	@Query(value = "SELECT * FROM T_SERVICE WHERE SERV_TYPE_CD=?1", nativeQuery = true)
	List<Service> getServiceCodesByCodeType(String codeType);

	@Query(value = "SELECT * FROM T_SERVICE WHERE SERV_TYPE_CD=?1 AND VERSION_END=?2 AND ACTION!=?3", nativeQuery = true)
	List<Service> getServiceCodesByCodeTypeForVersionWithoutAction(String codeType, Long versionEnd, String action);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE T_SERVICE SET VERSION_END = ?1 WHERE VERSION_END = ?2 AND SERV_CD NOT IN (?3)", nativeQuery = true)
	void updateLatestVersionForProcessedFile(Long currentVersion, Long previousVersion, List<String> codes);

	//@Query(value = "SELECT * FROM T_SERVICE WHERE SERV_TYPE_CD=?1 AND VERSION_START>=?2 AND VERSION_END<=?2")
	//List<Service> getEntitiesPerFiletypeForVersion(String filetype, Long version);

	//@Query(value = "SELECT * FROM T_SERVICE WHERE SERV_TYPE_CD=?1 AND VERSION_START>?2 AND VERSION_END<=?3")
	//List<Service> getDeltaCodes(Long currPayorVersion, Long payorRequestedVersion, String codeType);

}
