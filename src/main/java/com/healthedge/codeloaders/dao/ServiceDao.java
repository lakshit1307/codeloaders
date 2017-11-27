package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.repository.ServiceRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
public class ServiceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDao.class);

    @Autowired
    private ServiceRepository serviceRepository;

    public ServiceDao () {
        LOGGER.info("ServieDao initialized");
    }

    public void save(final Service service){
        serviceRepository.save(service);
    }

    @Transactional
    public void update(final Service service){
        serviceRepository.update(service.getServiceShortDesciption(),
                service.getServiceLongDesciption(),service.getServiceAlternateDesciption(),
                service.getAction(),service.getVersion(),service.getServiceCode());
    }

    @Transactional
    public void terminate(final Service service){
        serviceRepository.terminate(service.getEffectiveEndDate(),service.getAction(),service.getServiceCode());
    }
    
    public List<Service> getAll(){
		return serviceRepository.findAll();
	}
}
