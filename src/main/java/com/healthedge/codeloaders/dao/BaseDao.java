package com.healthedge.codeloaders.dao;

import java.util.List;
import java.util.Map;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.myparser.MyFileMetaData;

public interface BaseDao {

	Map<String,? extends BaseEntity> getLatestVersion(MyFileMetaData fileMetaData);
	
	<T extends BaseEntity> boolean save(T entity);

	<T extends BaseEntity> boolean save(List<T> entity);
	}
