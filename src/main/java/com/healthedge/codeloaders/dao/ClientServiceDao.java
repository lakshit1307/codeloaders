package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.repository.ClientServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import java.util.*;

@Service
public class ClientServiceDao implements ClientBaseDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientBaseDao.class);

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

	@Override
	public Long getPayorVersionPerCode(String codeType, EntityManager entityManager) {

		return clientServiceRepository.getPayorCodeVersion(codeType, entityManager).getTime();

	}

	@Override
	public List<String> getCurrentPayorCodes(String codeType, EntityManager entityManager) {
		List<String> payorCode = clientServiceRepository.getDistinctPayorCodes(entityManager, codeType);
		return payorCode;
	}

	@Override
	public void terminate(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
		clientServiceRepository.terminate(entityManager, clientEntity);
	}

	@Override
	public void save(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
		clientServiceRepository.save(entityManager, clientEntity);
	}

	@Override
	public void update(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
		clientServiceRepository.update(entityManager, clientEntity);

	}
}
