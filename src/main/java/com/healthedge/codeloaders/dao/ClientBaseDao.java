package com.healthedge.codeloaders.dao;

import java.util.List;

import javax.persistence.EntityManager;

public interface ClientBaseDao {
	
	Long getPayorVersionPerCode(String codeType, EntityManager entityManager);
	
	List<String> getCurrentPayorCodes(String codeType, EntityManager entityManager);
	
}
