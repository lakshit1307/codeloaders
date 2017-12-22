package com.healthedge.codeloaders.batch.client;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import com.healthedge.codeloaders.myparser.MyFileMetaData;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.healthedge.codeloaders.dao.BaseDao;
import com.healthedge.codeloaders.dao.ClientBaseDao;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.service.ClientConnectionService;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;

@StepScope
public class ServiceCodeReader implements ItemReader<BaseEntity> {

	@Value("#{jobParameters['toVersion']}")
	Long toVersion;

	@Value("#{jobParameters['fileType']}")
	private String fileType;

	private EntityManager entityManager;

	private Long currentCodeVersion;

	private BaseEntity[] entitites;

	private int offset;

	@Autowired
	private BaseDao baseDao;

	@Autowired
	private ImplementationFactory implementationFactory;
	
	
	private ClientBaseDao clientBaseDao;

	@Autowired
	private ClientConnectionService clientConnectionService;

	@Value("#{stepExecutionContext[tenant_environment]}")
	TenantEnv tenantEnv;

	// public ServiceCodeReader() {
	// this.services = serviceDao.getServiceCodesByCodeType(codeType);
	// this.offset = 0;
	// }

	@PostConstruct
	public void onInit() throws Exception {
		MyFileMetaData fileMetaData = new MyFileMetaData(fileType);
		String fileTypeCd = fileMetaData.getFileTypeCd();
		this.offset = 0;
		this.entityManager = clientConnectionService
				.configureEntityManager(tenantEnv.getDbUrl(), tenantEnv.getDbUserName(), tenantEnv.getDbPassword())
				.createEntityManager();
		ClientBaseDao clientBaseDao = implementationFactory.getClientDao(fileType);
		String refFileTypeForStartVersion = fileMetaData.getRefFileTypeForStartVersion();
		if (refFileTypeForStartVersion != null) {
			MyFileMetaData fileMetaDataRef = new MyFileMetaData(refFileTypeForStartVersion);
			ClientBaseDao clientBaseDaoRef = implementationFactory.getClientDao(fileMetaDataRef.getFileType());
			Date lastUpdatedVersion = clientBaseDaoRef.getPayorVersionPerCode(fileMetaDataRef.getFileTypeCd(), entityManager);
			this.currentCodeVersion = lastUpdatedVersion.getTime();
		} else {
			Date lastUpdatedVersion = clientBaseDao.getPayorVersionPerCode(fileTypeCd, entityManager);
			this.currentCodeVersion = lastUpdatedVersion.getTime();
		}

		BaseDao baseDao = implementationFactory.getDao(fileType);
		List<? extends BaseEntity> baseEntities = baseDao.getDeltaCodes(currentCodeVersion, toVersion, fileTypeCd);
		this.entitites = baseEntities.toArray(new BaseEntity[baseEntities.size()]);

	}

	@Override
	public BaseEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		BaseEntity baseEntity = null;
		if (offset < entitites.length) {
			baseEntity = entitites[offset];
		}
		offset++;
		return baseEntity;
	}

}
