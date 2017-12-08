package com.healthedge.codeloaders.repository;


import com.healthedge.codeloaders.entity.ClientService;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


public interface ClientServiceRepository {

     Date getPayorCodeVersion(String codeType, EntityManager entityManager);


     List<String> getDistinctPayorCodes(EntityManager entityManager);

     void update(EntityManager entityManager, List<ClientService> clientServices);

     void terminate(EntityManager entityManager,List<ClientService> clientServices);

     void save(EntityManager entityManager,List<ClientService> clientServices);

     public List<String> getDistinctPayorCodes(EntityManager entityManager,String codeType);
}



