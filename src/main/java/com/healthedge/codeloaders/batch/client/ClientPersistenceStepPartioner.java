package com.healthedge.codeloaders.batch.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthedge.codeloaders.dao.TenantDao;
import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.entity.TenantEnv;

public class ClientPersistenceStepPartioner implements Partitioner {

	@Autowired
	private TenantDao tenantDao;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitionMap = new HashMap<String, ExecutionContext>();
		for (Tenant tenant : tenantDao.getAllTenant()) {
			for (TenantEnv tenantEnv : tenant.getTenantEnv()) {
				ExecutionContext context = new ExecutionContext();
				context.put("tenant_environment", tenantEnv);
				partitionMap.put(tenant.getTenantId() + "_" + tenantEnv.getTenantEnvId(), context);

			}
		}
		return partitionMap;
	}

}
