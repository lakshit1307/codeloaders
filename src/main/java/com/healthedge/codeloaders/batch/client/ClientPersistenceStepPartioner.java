package com.healthedge.codeloaders.batch.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.healthedge.codeloaders.dao.TenantDao;
import com.healthedge.codeloaders.dao.TenantEnvDao;
import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.entity.TenantEnv;

public class ClientPersistenceStepPartioner implements Partitioner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientPersistenceStepPartioner.class);

	@Autowired
	private TenantDao tenantDao;

	@Autowired
	private TenantEnvDao tenantEnvDao;

	@Value("#{jobParameters['tenantId']}")
	String tenantId;

	@Value("#{jobParameters['tenantEnvId']}")
	String tenantEnvId;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitionMap = new HashMap<String, ExecutionContext>();
		if (tenantId.equals("all")) {
			for (Tenant tenant : tenantDao.getAllTenant()) {
				if (validateTenant(tenant)) {
					for (TenantEnv tenantEnv : tenant.getTenantEnv()) {
						if (validateTenantEnv(tenantEnv)) {
							ExecutionContext context = new ExecutionContext();
							context.put("tenant_environment", tenantEnv);
							partitionMap.put(tenant.getTenantId() + "_" + tenantEnv.getTenantEnvId(), context);
						}

					}
				}
			}
		} else {
			TenantEnv tenantEnv = tenantEnvDao.geTenantEnvById(tenantEnvId);
			if (tenantEnv.getTenant().getIsActive() == 1 && tenantEnv.getIsActive() == 1) {
				ExecutionContext context = new ExecutionContext();
				context.put("tenant_environment", tenantEnv);
				partitionMap.put(tenantEnv.getTenant().getTenantId() + "_" + tenantEnv.getTenantEnvId(), context);

			}
		}

		return partitionMap;
	}

	private boolean validateTenant(Tenant tenant) {
		if (tenant.getIsActive() == 1 && tenant.getIsAutoLoad() == 1) {
			return true;
		}
		LOGGER.info("Tenant with tenantId: " + tenant.getTenantId() + " and tenant name: " + tenant.getName()
				+ " is not configured for auto load");
		return false;
	}

	private boolean validateTenantEnv(TenantEnv tenantEnv) {
		if (tenantEnv.getIsActive() == 1 && tenantEnv.getIsAutoLoad() == 1) {
			return true;
		}
		LOGGER.info("Tenant environment with tenantEnvId: " + tenantEnv.getTenantEnvId() + " and tenant name: "
				+ tenantEnv.getName() + " is not configured for auto load");
		return false;
	}

}
