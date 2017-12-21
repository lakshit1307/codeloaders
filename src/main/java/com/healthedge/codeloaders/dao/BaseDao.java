package com.healthedge.codeloaders.dao;

import java.util.List;
import java.util.Map;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.myparser.MyFileMetaData;

public interface BaseDao {

	Map<String,? extends BaseEntity> getLatestVersionWithoutTerminate(MyFileMetaData fileMetaData, Long prevVersion);
	
	<T extends BaseEntity> boolean save(T entity);

	<T extends BaseEntity> boolean save(List<T> entity);

	void updateLatestVersionForProcessedFile (Long currentVersion, Long previousVersion, List<String> codes);
	
//	List<? extends BaseEntity> getEntitiesPerFiletypeForVersion(String filetype, Long Version);
}
