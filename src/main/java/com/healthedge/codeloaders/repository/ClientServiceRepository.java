package com.healthedge.codeloaders.repository;


import com.healthedge.codeloaders.entity.ClientBaseEntity;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


public interface ClientServiceRepository {

     Date getPayorCodeVersion(String codeType, EntityManager entityManager);


     List<String> getDistinctPayorCodes(EntityManager entityManager);

     void update(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity);

     void terminate(EntityManager entityManager,List<? extends ClientBaseEntity> clientEntity);

     void save(EntityManager entityManager,List<? extends ClientBaseEntity> clientEntity);

     public List<String> getDistinctPayorCodes(EntityManager entityManager,String clientEntity);
}



