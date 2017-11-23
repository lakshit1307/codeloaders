package com.healthedge.codeloaders.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.dao.TenantDao;
import com.healthedge.codeloaders.dto.BaseResponse;
import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.entity.TenantEnv;

@Service
public class ClientService {

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
			if (validateTenant(tenant)) {
				response.put(tenant.getTenantId(), persistToEnvironments(tenant));
			}
		}
		return response;
	}

	public Map<Integer, String> persistToEnvironments(Tenant tenant) {
		Map<Integer, String> envStatusMap = new HashMap<Integer, String>();
		for (TenantEnv tenantEnv : tenant.getTenantEnv()) {
			if (validateTenantEnvironment(tenantEnv)) {
				envStatusMap.put(tenantEnv.getTenantEnvId(), getandPersistRecords(tenantEnv));
			}
		}
		return envStatusMap;
	}

	public String getandPersistRecords(TenantEnv tenantEnv) {
		try {
			clientConnectionService.configureEntityManager(tenantEnv.getDbUrl(), tenantEnv.getDbUserName(),
					tenantEnv.getDbPassword());
			for (com.healthedge.codeloaders.entity.Service service : serviceDao.getAll()) {
				com.healthedge.codeloaders.entity.ClientService clientService = new com.healthedge.codeloaders.entity.ClientService();
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
				clientConnectionService.saveToClient(clientService);
			}
			return "SUCCESS";
		} catch (Exception e) {
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
