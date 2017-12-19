package com.healthedge.codeloaders.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.dao.BaseDao;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;

@Service
public class StagingPersistenceService {

	@Autowired
	private ImplementationFactory implementationFactory;

	public <T extends BaseEntity> void persistToCodeLoders(List<T> entities, MyFileMetaData fileMetaData)
			throws Exception {
		BaseDao baseDao = implementationFactory.getDao(fileMetaData.getFileType());
		baseDao.save(entities);
	}

}
