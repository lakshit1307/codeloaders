package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.repository.ServiceRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
public class ServiceDao {

    @Autowired
    private ServiceRepository serviceRepository;

    public Boolean save(Service service){

        System.out.println("\n Received Entity to Create: \n"+service.toString());
        serviceRepository.save(service);
        return true;
    }
    @Transactional
    public Boolean update(Service service){

        System.out.println("\n Received Entity to Update: \n"+service.toString());
        serviceRepository.update(service.getServiceShortDesciption(),
                service.getServiceLongDesciption(),service.getServiceAlternateDesciption(),
                service.getAction(),service.getVersion(),service.getServiceCode());
        return true;
    }
    @Transactional
    public Boolean terminate(Service service){

        System.out.println("\n Received Entity to Terminate: \n"+service.toString());
        serviceRepository.terminate(service.getEffectiveEndDate(),service.getAction(),service.getServiceCode());
        return true;
    }
    
    public List<Service> getAll(){
		return serviceRepository.getAll();
	}
}
