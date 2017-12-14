package com.healthedge.codeloaders.batch.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.ClientPersistenceService;

public class ServiceCodeProcessor implements ItemProcessor<Service, ClientService> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCodeProcessor.class);

	@Autowired
	private ClientPersistenceService clientPersistenceService;

	@Override
	public ClientService process(Service item) throws Exception {
		LOGGER.debug("Item service code: " + item.getServiceCode());
		return clientPersistenceService.mapServiceToClient(item);
	}

}
