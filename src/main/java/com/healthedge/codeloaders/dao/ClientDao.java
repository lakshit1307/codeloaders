package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.repository.ClientServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class ClientDao {

	private static final Logger LOGGER = LoggerFactory.getLogger("Client Dao initialzied");

	@Autowired
	private ClientServiceRepository clientServiceRepository;

	public Map<String, Date> getPayorVersionAllCodes(EntityManager entityManager) {

		Map<String, Date> payorCodeTypeVersion = new HashMap<>();
		List<String> codeTypes;
		codeTypes = clientServiceRepository.getDistinctPayorCodes(entityManager);
		for (String codeType : codeTypes) {
			payorCodeTypeVersion.put(codeType, clientServiceRepository.getPayorCodeVersion(codeType, entityManager));
		}

		return payorCodeTypeVersion;

	}

	public Date getPayorVersionPerCode(String codeType, EntityManager entityManager) {

		return clientServiceRepository.getPayorCodeVersion(codeType, entityManager);

	}

	public void update(EntityManager entityManager, List<ClientService> clientServices) {

		clientServiceRepository.update(entityManager, clientServices);

	}

	public void terminate(EntityManager entityManager, List<ClientService> clientServices) {
		clientServiceRepository.terminate(entityManager, clientServices);
	}

	public void save(EntityManager entityManager, List<ClientService> clientServices) {
		clientServiceRepository.save(entityManager, clientServices);
	}

	public List<String> getPayorCodes(EntityManager entityManager, String codeType) {
		List<String> payorCode = clientServiceRepository.getDistinctPayorCodes(entityManager, codeType);
		return payorCode;
	}
}
