package com.healthedge.codeloaders.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.healthedge.codeloaders.entity.ClientService;

@SuppressWarnings({ "PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal" })
@Service
public class ClientConnectionService {

	public EntityManagerFactory configureEntityManager(String dbUrl, String userName, String password) {
		Map properties = new HashMap();
		// EntityManagerFactory emf = new
		// HibernatePersistenceProvider().createContainerEntityManagerFactory(
		// archiverPersistenceUnitInfo(),
		// ImmutableMap.<String, String>builder()
		// .put("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver")
		// .put("hibernate.connection.url", dbUrl)
		// .put("hibernate.connection.username", userName)
		// .put("hibernate.connection.password", password)
		// .put("hibernate.show-sql", "true")
		// .build());
		EntityManagerFactory emf;
		properties.put("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
		properties.put("hibernate.connection.url", dbUrl);
		properties.put("hibernate.connection.username", userName);
		properties.put("hibernate.connection.password", password);
		properties.put("hibernate.show-sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		emf = Persistence.createEntityManagerFactory("jpablogPUnit", properties);
		// emf.createEntityManager();
		return emf;
	}

	public String saveToClient(ClientService clientService, EntityManagerFactory emf) {
		EntityManager entityManager = (EntityManager) emf.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(clientService);
		entityManager.getTransaction().commit();
		return "SUCCESS";
	}

	public String saveToClient(List<? extends ClientService> clientServices, EntityManagerFactory emf) {
		EntityManager entityManager = (EntityManager) emf.createEntityManager();
		entityManager.getTransaction().begin();
		for (ClientService clientService : clientServices) {
			entityManager.persist(clientService);
		}
		entityManager.getTransaction().commit();
		return "SUCCESS";
	}

	private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
		return new PersistenceUnitInfo() {
			@Override
			public String getPersistenceUnitName() {
				return "ApplicationPersistenceUnit";
			}

			@Override
			public String getPersistenceProviderClassName() {
				return "com.healthedge.codeloaders.entity.ClientService";
			}

			@Override
			public PersistenceUnitTransactionType getTransactionType() {
				return PersistenceUnitTransactionType.RESOURCE_LOCAL;
			}

			@Override
			public DataSource getJtaDataSource() {
				return null;
			}

			@Override
			public DataSource getNonJtaDataSource() {
				return null;
			}

			@Override
			public List<String> getMappingFileNames() {
				return Collections.emptyList();
			}

			@Override
			public List<URL> getJarFileUrls() {
				try {
					return Collections.list(this.getClass().getClassLoader().getResources(""));
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}

			@Override
			public URL getPersistenceUnitRootUrl() {
				return null;
			}

			@Override
			public List<String> getManagedClassNames() {
				return Collections.emptyList();
			}

			@Override
			public boolean excludeUnlistedClasses() {
				return false;
			}

			@Override
			public SharedCacheMode getSharedCacheMode() {
				return null;
			}

			@Override
			public ValidationMode getValidationMode() {
				return null;
			}

			@Override
			public Properties getProperties() {
				return new Properties();
			}

			@Override
			public String getPersistenceXMLSchemaVersion() {
				return null;
			}

			@Override
			public ClassLoader getClassLoader() {
				return null;
			}

			@Override
			public void addTransformer(ClassTransformer transformer) {

			}

			@Override
			public ClassLoader getNewTempClassLoader() {
				return null;
			}
		};
	}

}
