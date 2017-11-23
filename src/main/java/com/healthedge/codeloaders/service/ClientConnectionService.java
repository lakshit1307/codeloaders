package com.healthedge.codeloaders.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.entity.ClientService;

import oracle.jdbc.pool.OracleDataSource;

@Service
public class ClientConnectionService {

	EntityManagerFactory emf;
	EntityManager em;

	// public String connectToDbAndPersist() throws SQLException {
	// DataSourceAutoConfiguration ds = new DataSourceAutoConfiguration();
	// ds.setUsername("client");
	// ds.setPassword("root");
	// ds.setDriverClassName("oracle.jdbc.driver.OracleDriver"); // or another
	// driver
	// ds.setUrl("jdbc:oracle:thin:@//localhost:1521/xe");
	// ds.setTestWhileIdle(true);
	// ds.setTestOnBorrow(true);
	// ds.setTestOnReturn(false);
	// ds.setValidationQueryTimeout(1);
	// ds.setValidationInterval(30000);
	// ds.setTimeBetweenEvictionRunsMillis(30000);
	// ds.setMinIdle(1);
	// ds.setMaxWait(10000);
	// ds.setMaxIdle(10);
	// ds.setInitialSize(10);
	// ds.setMinEvictableIdleTimeMillis(30000);
	// Connection connection=ds.getConnection();
	// Statement statement=connection.prepareStatement("");
	// }

	// @Bean
	// DataSource dataSource() throws SQLException {
	//
	// OracleDataSource dataSource = new OracleDataSource();
	// dataSource.setUser("client");
	// dataSource.setPassword("root");
	// dataSource.setURL("jdbc:oracle:thin:@//localhost:1521/xe");
	// dataSource.setImplicitCachingEnabled(true);
	// dataSource.setFastConnectionFailoverEnabled(true);
	// return dataSource;
	// }

	public void configureEntityManager(String dbUrl, String userName, String password) {
		Map properties = new HashMap();
		properties.put("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
		properties.put("hibernate.connection.url", dbUrl);
		properties.put("hibernate.connection.username", userName);
		properties.put("hibernate.connection.password", password);
		properties.put("hibernate.show-sql", "true");
		// emf = Persistence.createEntityManagerFactory("jpablogPUnit");
		emf = Persistence.createEntityManagerFactory("jpablogPUnit", properties);
		em = emf.createEntityManager();
	}

	// public void saveCompany() {
	// EntityManager entityManager = (EntityManager) emf.createEntityManager();
	// entityManager.getTransaction().begin();
	// com.healthedge.codeloaders.entity.Service service = new
	// com.healthedge.codeloaders.entity.Service();
	//// service.setAction("action");
	//// service.setCodeProcessingHistoryId(12L);
	// service.setServiceTypeCode("sdd");
	// service.setWorkFlowCode(null);
	// service.setEffectiveEndDate(new Date());
	// service.setEffectiveStartDate(new Date());
	// service.setLastTransactionDate(new Date());
	// service.setLastTransactionUserText("user text");
	// service.setServiceAlternateDesciption("alternate desc");
	// service.setServiceCode("A1");
	// service.setServiceLongDesciption("long description");
	// service.setServiceShortDesciption("short description");
	// service.setStandardizedServiceCode("a");
	// service.setTransactionCount(23);
	// service.setworkFlowCode("code");
	// entityManager.persist(service);
	// entityManager.getTransaction().commit();
	// }

	public String saveToClient(ClientService clientService) {
		EntityManager entityManager = (EntityManager) emf.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(clientService);
		entityManager.getTransaction().commit();
		return "SUCCESS";
	}

	// public void fetchCompanyData() {
	// Query query = em.createQuery("SELECT c FROM Customers c");
	// List<Customer> list = (List<Customer>) query.getResultList();
	//
	// for (Company company : list) {
	// log.info("Company Name :: " + company.getName());
	// log.info("Company City :: " + company.getCity());
	// log.info("***************************");
	// }
	// }

}
