package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.repository.ClientDiagnosisRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
public class ClientDiagnosisDao implements ClientBaseDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientBaseDao.class);

    @Autowired
    private ClientDiagnosisRepositoryImpl clientDiagnosisRepository;

    @Override
    public Date getPayorVersionPerCode(String codeType, EntityManager entityManager) {
        return clientDiagnosisRepository.getPayorCodeVersion(codeType, entityManager);
    }

    @Override
    public List<String> getCurrentPayorCodes(String codeType, EntityManager entityManager) {
        List<String> payorCode = clientDiagnosisRepository.getDistinctPayorCodes(entityManager, codeType);
        return payorCode;
    }

    @Override
    public void terminate(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        clientDiagnosisRepository.terminate(entityManager, clientEntity);
    }

    @Override
    public void save(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        clientDiagnosisRepository.save(entityManager, clientEntity);
    }

    @Override
    public void update(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        clientDiagnosisRepository.update(entityManager, clientEntity);
    }
}
