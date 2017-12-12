package com.healthedge.codeloaders.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.repository.TenantEnvironmentRepository;

@Service
public class TenantEnvDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(TenantEnvDao.class);

	public TenantEnvDao() {
		LOGGER.info("TenantEnvDao class initialized");
	}
	
	@Autowired
	public TenantEnvironmentRepository tenantEnvRepository;
	
	public TenantEnv geTenantEnvById(String tenantEnvId) {
		Integer tenantEnvID=Integer.parseInt(tenantEnvId);
		return tenantEnvRepository.findByTenantId(tenantEnvID).get(0);
	}

}
