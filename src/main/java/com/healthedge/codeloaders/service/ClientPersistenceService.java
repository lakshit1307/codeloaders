package com.healthedge.codeloaders.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.dao.TenantDao;
import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.entity.TenantEnv;

@SuppressWarnings({ "PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal",
		"PMD.AvoidInstantiatingObjectsInLoops" })
@Service
public class ClientPersistenceService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientPersistenceService.class);

	@Autowired
	private TenantDao tenantDao;

	@Autowired
	private ClientConnectionService clientConnectionService;

	@Autowired
	private ServiceDao serviceDao;

	public Map<Integer, Map<Integer, String>> persistToClients() {
		Map<Integer, Map<Integer, String>> response = new HashMap<Integer, Map<Integer, String>>();
		List<Tenant> tenants = tenantDao.getAllTenant();
		for (Tenant tenant : tenants) {
			LOGGER.info("Persisting to clients: " + tenant.getTenantId());
			if (validateTenant(tenant)) {
				response.put(tenant.getTenantId(), persistToEnvironments(tenant));
			}
		}
		return response;
	}

	public Map<Integer, String> persistToEnvironments(Tenant tenant) {
		Map<Integer, String> envStatusMap = new HashMap<Integer, String>();
		for (TenantEnv tenantEnv : tenant.getTenantEnv()) {
			LOGGER.info("Persisting to client-env: " + tenantEnv.getTenantEnvId());
			if (validateTenantEnvironment(tenantEnv)) {
				envStatusMap.put(tenantEnv.getTenantEnvId(), getandPersistRecords(tenantEnv));
			}
		}
		return envStatusMap;
	}

	public String getandPersistRecords(TenantEnv tenantEnv) {
		try {
			EntityManagerFactory entityManagerFactory = clientConnectionService
					.configureEntityManager(tenantEnv.getDbUrl(), tenantEnv.getDbUserName(), tenantEnv.getDbPassword());
			List<com.healthedge.codeloaders.entity.Service> services = serviceDao.getAll();
			for (com.healthedge.codeloaders.entity.Service service : services) {
				ClientService clientService = new ClientService();
				clientService.setEffectiveEndDate(service.getEffectiveEndDate());
				clientService.setEffectiveStartDate(service.getEffectiveStartDate());
				clientService.setLastTransactionDate(service.getLastTransactionDate());
				clientService.setLastTransactionUserText(service.getLastTransactionUserText());
				clientService.setServiceAlternateDesciption(service.getServiceAlternateDesciption());
				clientService.setServiceCode(service.getServiceCode());
				clientService.setServiceLongDesciption(service.getServiceLongDesciption());
				clientService.setServiceShortDesciption(service.getServiceShortDesciption());
				clientService.setServiceTypeCode(service.getServiceTypeCD());
				clientService.setStandardizedServiceCode(service.getStandardizedServiceCode());
				clientService.setTransactionCount(service.getTxCnt());
				clientService.setWorkFlowCode(service.getWorkFlowCode());
				clientConnectionService.saveToClient(clientService, entityManagerFactory);
			}
			LOGGER.info("Persisted to client-env:" + tenantEnv.getTenantEnvId());
			return "SUCCESS";
		} catch (Exception e) { // NOPMD
			LOGGER.error("Error in persisting records to client db for env: "+ tenantEnv.getTenantEnvId(), e);
			return "FAILURE";
		}
	}

	public boolean validateTenant(Tenant tenant) {
		return true;
	}

	public boolean validateTenantEnvironment(TenantEnv tenantEnv) {
		return true;
	}

}
