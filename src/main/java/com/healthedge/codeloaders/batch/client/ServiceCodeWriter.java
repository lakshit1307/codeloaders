package com.healthedge.codeloaders.batch.client;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.service.ClientConnectionService;
import com.healthedge.codeloaders.service.ClientPersistenceService;

import oracle.jdbc.pool.OracleDataSource;

public class ServiceCodeWriter implements ItemWriter<ClientService> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCodeWriter.class);

	@Value("#{stepExecutionContext[tenant_environment]}")
	TenantEnv tenantEnv;

	private EntityManagerFactory emf;

	@Autowired
	private ClientConnectionService clientConnectionService;

	public ServiceCodeWriter() {
		this.emf = clientConnectionService.configureEntityManager(tenantEnv.getDbUrl(), tenantEnv.getDbUserName(),
				tenantEnv.getDbPassword());
	}

	@Override
	public void write(List<? extends ClientService> items) throws Exception {
//		LOGGER.info("writing for id: " + clientService.getServiceCode());
		clientConnectionService.saveToClient(items, emf);

	}
}
