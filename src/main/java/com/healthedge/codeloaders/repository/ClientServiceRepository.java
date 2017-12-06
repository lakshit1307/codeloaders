package com.healthedge.codeloaders.repository;


import com.healthedge.codeloaders.entity.ClientService;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


public interface ClientServiceRepository {

    public Date getPayorCodeVersion(String codeType, EntityManager entityManager);


    public List<String> getDistinctPayorCodes(EntityManager entityManager);

    public void update(EntityManager entityManager, ClientService clientService);

    public void terminate(EntityManager entityManager,ClientService clientService);

    public void save(EntityManager entityManager,ClientService clientService);

}



