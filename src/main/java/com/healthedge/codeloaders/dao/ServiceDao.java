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

    public void save(Service service){

        System.out.println("\n Received Entity to Create: \n"+service.toString());
        serviceRepository.save(service);
    }
    @Transactional
    public void update(Service service){

        System.out.println("\n Received Entity to Update: \n"+service.toString());
        serviceRepository.update(service.getServiceShortDesciption(),
                service.getServiceLongDesciption(),service.getServiceAlternateDesciption(),
                service.getAction(),service.getVersion(),service.getServiceCode());
    }
    @Transactional
    public void terminate(Service service){

        System.out.println("\n Received Entity to Terminate: \n"+service.toString());
        serviceRepository.terminate(service.getEffectiveEndDate(),service.getAction(),service.getServiceCode());
    }
    
    public List<Service> getAll(){
		return serviceRepository.getAll();
	}
}
