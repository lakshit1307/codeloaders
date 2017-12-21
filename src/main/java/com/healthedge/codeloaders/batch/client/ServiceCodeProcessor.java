package com.healthedge.codeloaders.batch.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.service.ClientPersistenceService;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;
import com.healthedge.codeloaders.service.Transformer.Transformer;

public class ServiceCodeProcessor implements ItemProcessor< BaseEntity, ClientBaseEntity > {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCodeProcessor.class);

	@Value("#{jobParameters['fileType']}")
	String fileType;
	
	@Autowired
	private ClientPersistenceService clientPersistenceService;
	
	@Autowired
	private ImplementationFactory implementationFactory;

	@Override
	public ClientBaseEntity process(BaseEntity item) throws Exception {
//		LOGGER.debug("Item service code: " + item.getServiceCode());
		Transformer transformer=implementationFactory.getTransformer(fileType);
		return transformer.clientEntityTransform(item);
	}

}
