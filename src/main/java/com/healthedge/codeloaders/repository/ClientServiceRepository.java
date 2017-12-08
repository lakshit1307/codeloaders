package com.healthedge.codeloaders.repository;


import com.healthedge.codeloaders.entity.ClientService;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


public interface ClientServiceRepository {

     Date getPayorCodeVersion(String codeType, EntityManager entityManager);


     List<String> getDistinctPayorCodes(EntityManager entityManager);

     void update(EntityManager entityManager, ClientService clientService);

     void terminate(EntityManager entityManager,ClientService clientService);

     void save(EntityManager entityManager,ClientService clientService);

     public List<String> getDistinctPayorCodes(EntityManager entityManager,String codeType);
}



