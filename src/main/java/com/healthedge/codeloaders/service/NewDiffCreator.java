package com.healthedge.codeloaders.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.dao.BaseDao;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;

@Service
public class NewDiffCreator {

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private ImplementationFactory implementationFactory;

	@Autowired
	BaseDao baseDao;

	private Map previous = new ConcurrentHashMap<>();

//	public void initDiff(Map current, MyFileMetaData fileMetaData) throws SQLException, ClassNotFoundException {
//		Class entityClass = Class.forName("com.healthedge.codeloaders.entity." + fileMetaData.getTableName());
//		Class daoClass = Class.forName("com.healthedge.codeloaders.dao." + fileMetaData.getTableName() + "Dao");
//		createDiff(previous, current, new Date());
//	}

	public <T extends BaseEntity, D extends BaseDao> Map<String, List<T>> configureDiff(Map<String, T> currentFileCodes,
			MyFileMetaData fileMetaData) throws Exception {
		if (CollectionUtils.isEmpty(previous)) {
			previous = implementationFactory.getDao(fileMetaData.getFileType()).getLatestVersion(fileMetaData);
		}
		return createDiff(currentFileCodes, previous, fileMetaData.getFileDate().toDate());

	}

	public <T extends BaseEntity> Map<String, List<T>> createDiff(Map<String, T> previousFileCodes,
			Map<String, T> currentFileCodes, Date fileDate) {

		final List<T> create = new ArrayList<T>();
		final List<T> append = new ArrayList<T>();
		final List<T> terminate = new ArrayList<T>();
		final Map<String, List<T>> result = new ConcurrentHashMap<>();
		if (CollectionUtils.isEmpty(previousFileCodes)) {
			for (final String code : previousFileCodes.keySet()) {
				final T entity = currentFileCodes.get(code);
				entity.setAction(CodeLoaderConstants.CREATE_ACTION);
				entity.setEffectiveStartDate(fileDate);
				create.add(entity);
			}
			previousFileCodes.putAll(currentFileCodes);
			currentFileCodes.clear();

		} else {
			for (final String code : currentFileCodes.keySet()) {
				if (previousFileCodes.containsKey(code)) {
					final T pojo2 = currentFileCodes.get(code);
					final T pojo1 = previousFileCodes.get(code);
					if (!pojo1.equals(pojo2)) {
						pojo2.setAction(CodeLoaderConstants.APPEND_ACTION);
						pojo2.setVersionStart(fileDate);
						pojo2.setVersionEnd(fileDate);
						append.add(pojo2);
					}
					previousFileCodes.remove(code);
				} else {
					final T pojo = currentFileCodes.get(code);
					pojo.setAction(CodeLoaderConstants.CREATE_ACTION);
					pojo.setEffectiveStartDate(fileDate);
					pojo.setVersionStart(fileDate);
					pojo.setVersionEnd(fileDate);
					create.add(pojo);
				}
			}
			for (final String key : previousFileCodes.keySet()) {
				final T pojo = previousFileCodes.get(key);
				pojo.setAction(CodeLoaderConstants.TERMINATE_ACTION);
				pojo.setEffectiveEndDate(fileDate);
				pojo.setVersionEnd(fileDate);
				terminate.add(pojo);

			}
			previousFileCodes.clear();
			previousFileCodes.putAll(currentFileCodes);
			currentFileCodes.clear();
		}
		result.put(CodeLoaderConstants.CREATE_ACTION, create);
		result.put(CodeLoaderConstants.APPEND_ACTION, append);
		result.put(CodeLoaderConstants.TERMINATE_ACTION, terminate);
		return result;
	}

	public void flushPreviousData() {
		previous.clear();
	}

	public <T extends BaseEntity> void setPreviousData(Map<String, T> records, Class<T> entityClass) {

		records.clear();
		records.putAll(records);
	}
}
