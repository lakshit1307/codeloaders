package com.healthedge.codeloaders.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.entity.ClientService;

@SuppressWarnings({ "PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal" })
@Service
public class ClientConnectionService {

	public EntityManagerFactory configureEntityManager(String dbUrl, String userName, String password) {
		Map properties = new HashMap();
		EntityManagerFactory emf;
		properties.put("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
		properties.put("hibernate.connection.url", dbUrl);
		properties.put("hibernate.connection.username", userName);
		properties.put("hibernate.connection.password", password);
		properties.put("hibernate.show-sql", "true");
		emf = Persistence.createEntityManagerFactory("jpablogPUnit", properties);
//		emf.createEntityManager();
		return emf;
	}

	public String saveToClient(ClientService clientService, EntityManagerFactory emf) {
		EntityManager entityManager = (EntityManager) emf.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(clientService);
		entityManager.getTransaction().commit();
		return "SUCCESS";
	}

	public String saveToClient(List<ClientService> clientServices, EntityManagerFactory emf) {
		EntityManager entityManager = (EntityManager) emf.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(clientServices);
		entityManager.getTransaction().commit();
		return "SUCCESS";
	}

}
