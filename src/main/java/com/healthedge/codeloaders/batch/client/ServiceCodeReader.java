package com.healthedge.codeloaders.batch.client;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.Service;

@StepScope
public class ServiceCodeReader implements ItemReader<Service> {

	@Value("#{jobParameters['filePath']}")
	String codeType;

	private Service[] services;

	private int offset;

	@Autowired
	private ServiceDao serviceDao;

	// public ServiceCodeReader() {
	// this.services = serviceDao.getServiceCodesByCodeType(codeType);
	// this.offset = 0;
	// }

	@PostConstruct
	public void onInit() {
		this.services = serviceDao.getServiceCodesByCodeType(codeType);
		this.offset = 0;
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
