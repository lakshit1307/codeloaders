package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceDao {

    @Autowired
    private ServiceRepository serviceRepository;

    public void save(Service service){

        System.out.println("\n RECEIVED DAO: \n"+service.toString());
        serviceRepository.save(service);
    }
}
