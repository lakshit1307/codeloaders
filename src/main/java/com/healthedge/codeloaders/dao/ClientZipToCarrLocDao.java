package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.repository.ClientZipToCarrLocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
public class ClientZipToCarrLocDao implements ClientBaseDao {

    @Autowired
    private ClientZipToCarrLocRepository clientZipToCarrLocRepository;
    @Override
    public Date getPayorVersionPerCode(String codeType, EntityManager entityManager) {
        return clientZipToCarrLocRepository.getPayorCodeVersion(codeType, entityManager);
    }

    @Override
    public List<String> getCurrentPayorCodes(String codeType, EntityManager entityManager) {
        List<String> zipCodes=clientZipToCarrLocRepository.getDistinctPayorCodes(entityManager,codeType);
        return zipCodes;
    }

    @Override
    public void terminate(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {

    }

    @Override
    public void save(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        clientZipToCarrLocRepository.save(entityManager, clientEntity);
    }

    @Override
    public void update(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        clientZipToCarrLocRepository.update(entityManager, clientEntity);
    }
}
