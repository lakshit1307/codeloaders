package com.healthedge.codeloaders.batch.client;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.healthedge.codeloaders.dao.ClientBaseDao;
import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.service.ClientConnectionService;

public class ServiceCodeWriter implements ItemWriter<ClientBaseEntity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCodeWriter.class);

	@Value("#{stepExecutionContext[tenant_environment]}")
	TenantEnv tenantEnv;

	@Value("#{jobParameters['fileType']}")
	private String fileType;

	private EntityManager entityManager;

	private List<String> clientCodes;

	@Autowired
	private ClientConnectionService clientConnectionService;

	@Autowired
	private ImplementationFactory implementationFactory;

	private ClientBaseDao clientBaseDao;

	@PostConstruct
	public void onInit() throws Exception {
		MyFileMetaData fileMetaData = new MyFileMetaData(fileType);
		String fileTypeCd = fileMetaData.getFileTypeCd();
		this.entityManager = clientConnectionService
				.configureEntityManager(tenantEnv.getDbUrl(), tenantEnv.getDbUserName(), tenantEnv.getDbPassword())
				.createEntityManager();
		clientBaseDao = implementationFactory.getClientDao(fileType);
		this.clientCodes = clientBaseDao.getCurrentPayorCodes(fileTypeCd, entityManager);
	}

	@Override
	public void write(List<? extends ClientBaseEntity> items) throws Exception {
		List<ClientBaseEntity> clientBaseInsert = new ArrayList<>();
		List<ClientBaseEntity> clientBaseUpdate = new ArrayList<ClientBaseEntity>();
		List<ClientBaseEntity> clientBaseTerminate = new ArrayList<ClientBaseEntity>();

		for (ClientBaseEntity clientBaseEntity : items) {
			LOGGER.debug("writing for id: " + clientBaseEntity.getCode());
			if (clientCodes.contains(clientBaseEntity.getCode())) {
				if (clientBaseEntity.getEffectiveEndDate() != null) {
					clientBaseTerminate.add(clientBaseEntity);
				} else {
					clientBaseUpdate.add(clientBaseEntity);
				}
			} else {
				clientBaseInsert.add(clientBaseEntity);
			}
		}

		clientBaseDao.terminate(entityManager, clientBaseTerminate);
		clientBaseDao.update(entityManager, clientBaseUpdate);
		clientBaseDao.save(entityManager, clientBaseInsert);
	}
}
