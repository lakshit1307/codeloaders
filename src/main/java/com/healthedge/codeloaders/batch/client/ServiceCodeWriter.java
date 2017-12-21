package com.healthedge.codeloaders.batch.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.healthedge.codeloaders.dao.ClientDao;
import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.service.ClientConnectionService;

public class ServiceCodeWriter implements ItemWriter<ClientService> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCodeWriter.class);

	@Value("#{stepExecutionContext[tenant_environment]}")
	TenantEnv tenantEnv;

	@Value("#{jobParameters['filePath']}")
	String codeType;

	private EntityManager entityManager;

	private Date currentCodeVersion;

	private List<String> clientCodes;

	@Autowired
	private ClientConnectionService clientConnectionService;

	@Autowired
	private ClientDao clientDao;

	@PostConstruct
	public void onInit() {
		this.entityManager = clientConnectionService
				.configureEntityManager(tenantEnv.getDbUrl(), tenantEnv.getDbUserName(), tenantEnv.getDbPassword())
				.createEntityManager();
		this.currentCodeVersion = clientDao.getPayorVersionPerCode(codeType, entityManager);
		this.clientCodes = clientDao.getPayorCodes(entityManager, codeType);
	}

	@Override
	public void write(List<? extends ClientService> items) throws Exception {
		List<ClientService> clientServicesInsert = new ArrayList<>();
		List<ClientService> clientServicesUpdate = new ArrayList<ClientService>();
		List<ClientService> clientServicesTerminate = new ArrayList<ClientService>();

		for (ClientService clientService : items) {
			LOGGER.debug("writing for id: " + clientService.getCode());
			if (clientCodes.contains(clientService.getCode())) {
				if(clientService.getEffectiveEndDate()!=null){
				    clientServicesTerminate.add(clientService);
                }
                else{
				    clientServicesUpdate.add(clientService);
                }
			} else {
				clientServicesInsert.add(clientService);
			}
		}

		clientDao.terminate(entityManager,clientServicesTerminate);
		clientDao.update(entityManager, clientServicesUpdate);
		clientDao.save(entityManager, clientServicesInsert);
		// clientConnectionService.saveToClient(items, emf);

	}
}
