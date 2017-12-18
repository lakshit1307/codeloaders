package com.healthedge.codeloaders.batch.client;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.healthedge.codeloaders.dao.ClientDao;
import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.service.ClientConnectionService;

@StepScope
public class ServiceCodeReader implements ItemReader<Service> {

	@Value("#{jobParameters['filePath']}")
	String codeType;

	private EntityManager entityManager;

	private Date currentCodeVersion;

	private Service[] services;

	private int offset;

	@Autowired
	private ServiceDao serviceDao;

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private ClientConnectionService clientConnectionService;

	@Value("#{stepExecutionContext[tenant_environment]}")
	TenantEnv tenantEnv;

	// public ServiceCodeReader() {
	// this.services = serviceDao.getServiceCodesByCodeType(codeType);
	// this.offset = 0;
	// }

	@PostConstruct
	public void onInit() {
		this.offset = 0;
		this.entityManager = clientConnectionService
				.configureEntityManager(tenantEnv.getDbUrl(), tenantEnv.getDbUserName(), tenantEnv.getDbPassword())
				.createEntityManager();
		this.currentCodeVersion = clientDao.getPayorVersionPerCode(codeType, entityManager);
		List<Service> services = serviceDao.getDeltaCodes(currentCodeVersion, codeType);
		this.services = services.toArray(new Service[services.size()]);

	}

	@Override
	public Service read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Service service = null;
		// this.services = serviceDao.getServiceCodesByCodeType(codeType);
		// this.offset = 0;
		if (offset < services.length) {
			service = services[offset];
		}
		offset++;
		return service;
	}
}
