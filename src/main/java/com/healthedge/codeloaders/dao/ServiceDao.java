package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceDao {

    @Autowired
    private ServiceRepository serviceRepository;

    public void save(Service service){

        serviceRepository.save(service);
    }
}
