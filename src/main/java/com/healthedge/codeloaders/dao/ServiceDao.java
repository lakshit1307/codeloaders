package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.repository.ServiceRepository;

import java.util.Date;
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

    public Boolean save(final Service service){
        serviceRepository.save(service);
        return true;
    }

    @Transactional
    public Boolean update(final Service service){
        serviceRepository.update(service.getServiceShortDesciption(),
                service.getServiceLongDesciption(),service.getServiceAlternateDesciption(),
                service.getAction(),service.getVersion(),service.getServiceCode());
        return true;
    }

    @Transactional
    public Boolean terminate(final Service service){
        serviceRepository.terminate(service.getEffectiveEndDate(),service.getAction(),service.getVersion(),service.getServiceCode());
        return true;
    }

    @Transactional
    public Date getCodeVersion(final String codeType){
        Date version=serviceRepository.getCodeTypeLatestVersion(codeType);
        return version;
    }

    @Transactional
    public List<String> getDistinctCodeTypes(){
        List<String> allCodeTypes=serviceRepository.getDistinctCodeTypes();
        return allCodeTypes;
    }

    @Transactional
    public List<Service> getDeltaCodes(Date version,String codeType){
        List<Service> ls=serviceRepository.getDeltaCloaderCodes(version,codeType);
        return ls;
    }
    public List<Service> getAll(){
		return serviceRepository.findAll();
	}
    
    public Service[] getServiceCodesByCodeType(String codeType){
    	List<Service> services=serviceRepository.getServiceCodesByCodeType(codeType);
    	return services.toArray(new Service[services.size()]);
    }
}
