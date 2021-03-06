package com.healthedge.codeloaders.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.dao.BaseDao;
import com.healthedge.codeloaders.dao.FileStatusDao;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.FileStatus;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;

@Service
public class NewDiffCreator {

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private ImplementationFactory implementationFactory;

	private Map previous = new ConcurrentHashMap<>();

	private Long currVersion = null;

	private Long prevVersion = null;

	@Autowired
	private FileStatusDao fileStatusDao;
	
	List<String> codes=new ArrayList<String>();

	// public void initDiff(Map current, MyFileMetaData fileMetaData) throws
	// SQLException, ClassNotFoundException {
	// Class entityClass = Class.forName("com.healthedge.codeloaders.entity." +
	// fileMetaData.getTableName());
	// Class daoClass = Class.forName("com.healthedge.codeloaders.dao." +
	// fileMetaData.getTableName() + "Dao");
	// createDiff(previous, current, new Date());
	// }


	public Long getCurrVersion() {
		return currVersion;
	}

	public Long getPrevVersion() {
		return prevVersion;
	}

	public List<String> getCodes() {
		return codes;
	}

	public <T extends BaseEntity, D extends BaseDao> Map<String, List<T>> configureDiff(Map<String, T> currentFileCodes,
																						MyFileMetaData fileMetaData, boolean isTerminateRequired) throws Exception {
		if (CollectionUtils.isEmpty(previous)) {
			FileStatus fileStatus = fileStatusDao.getFileTypeDetailsForLatestVersion(fileMetaData.getFileType());
			if (fileStatus != null) {
				previous = implementationFactory.getDao(fileMetaData.getFileType())
						.getLatestVersionWithoutTerminate(fileMetaData, fileStatus.getVersion());
				prevVersion = fileStatus.getVersion();
			}
		} else {
			prevVersion = currVersion;
		}

		currVersion = fileMetaData.getFileVersion();
		return createDiff(previous, currentFileCodes, fileMetaData, isTerminateRequired);

	}

	public <T extends BaseEntity> Map<String, List<T>> createDiff(Map<String, T> previousFileCodes,
			Map<String, T> currentFileCodes, MyFileMetaData fileMetaData, boolean isTerminateRequired) {
		final List<T> create = new ArrayList<T>();
		final List<T> append = new ArrayList<T>();
		final List<T> terminate = new ArrayList<T>();
		final Map<String, List<T>> result = new ConcurrentHashMap<>();
		if (CollectionUtils.isEmpty(previousFileCodes)) {
			for (final String code : currentFileCodes.keySet()) {
				final T entity = currentFileCodes.get(code);
				entity.setAction(CodeLoaderConstants.CREATE_ACTION);
				entity.setEffectiveStartDate(fileMetaData.getEffectiveStartDate());
				entity.setVersionStart(fileMetaData.getFileVersion());
				entity.setVersionEnd(fileMetaData.getFileVersion());
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
						pojo2.setEffectiveStartDate(pojo1.getEffectiveStartDate());
						pojo2.setVersionStart(fileMetaData.getFileVersion());
						pojo2.setVersionEnd(fileMetaData.getFileVersion());
						append.add(pojo2);
						codes.add(pojo1.getCode());
					} else {
						//This setting is done mainly to support effective start in memory date if no change in the code
						pojo2.setEffectiveStartDate(pojo1.getEffectiveStartDate());
					}
					previousFileCodes.remove(code);

 				} else {
					final T pojo = currentFileCodes.get(code);
					pojo.setAction(CodeLoaderConstants.CREATE_ACTION);
					pojo.setEffectiveStartDate(fileMetaData.getEffectiveStartDate());
					pojo.setVersionStart(fileMetaData.getFileVersion());
					pojo.setVersionEnd(fileMetaData.getFileVersion());
					create.add(pojo);
				}
			}
			if (isTerminateRequired) {
				for (final String key : previousFileCodes.keySet()) {
					final T pojo = previousFileCodes.get(key);
					pojo.setAction(CodeLoaderConstants.TERMINATE_ACTION);
					pojo.setEffectiveEndDate(fileMetaData.getEffectiveEndDate());
					pojo.setVersionEnd(fileMetaData.getFileVersion());
					terminate.add(pojo);
					codes.add(pojo.getCode());
				}
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
		currVersion = null;
		prevVersion = null;
		codes.clear();
	}

	public <T extends BaseEntity> void setPreviousData(Map<String, T> records, Class<T> entityClass) {

		records.clear();
		records.putAll(records);
	}

}
