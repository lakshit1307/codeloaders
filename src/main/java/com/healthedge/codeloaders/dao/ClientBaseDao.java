package com.healthedge.codeloaders.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.healthedge.codeloaders.entity.ClientBaseEntity;

public interface ClientBaseDao {
	
	Date getPayorVersionPerCode(String codeType, EntityManager entityManager);
	
	List<String> getCurrentPayorCodes(String codeType, EntityManager entityManager);
	
	void terminate(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity);
	
	void save(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity);
	
	void update(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity);

	
}
