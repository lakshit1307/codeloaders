package com.healthedge.codeloaders.batch.client;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Value;

import com.healthedge.codeloaders.entity.TenantEnv;

public class ClientPersistenceListener implements StepExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientPersistenceListener.class);

	@Value("#{stepExecutionContext[tenant_environment]}")
	TenantEnv tenantEnv;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOGGER.info("Persistence started at:" + new Date() + "for tenant env id: "
				+ tenantEnv.getTenantEnvId().toString() + " of tenant id: " + tenantEnv.getTenantEnvId().toString());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOGGER.info("Persistence ended at:" + new Date() + "for tenant env id: "
				+ tenantEnv.getTenantEnvId().toString() + " of tenant id: " + tenantEnv.getTenantEnvId().toString());
		return null;
	}

}
