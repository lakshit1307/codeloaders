package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.repository.TenantEnvironmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TenantEnvironmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantDao.class);

    @Autowired
    private TenantEnvironmentRepository tenantEnvironmentRepository;

    public TenantEnvironmentDao () {
        LOGGER.info("TenantDao class initialized");
    }

    public List<TenantEnv> getAllEnvironments(){
        return tenantEnvironmentRepository.findAll();
    }

    public void save(final List<TenantEnv> tenantEnvs) {
        tenantEnvironmentRepository.save(tenantEnvs);
    }

    public void save(final TenantEnv tenantEnv) {
        tenantEnvironmentRepository.save(tenantEnv);
    }


    public List<TenantEnv> findById(int id) {
        return tenantEnvironmentRepository.findByTenantId(id);
    }

    @Transactional
    public void update(TenantEnv tenantEnv) {
        tenantEnvironmentRepository.update(tenantEnv.getDescription(),tenantEnv.getDbUrl(),tenantEnv.getDbUserName(),
                tenantEnv.getDbPassword(),tenantEnv.getIsAutoLoad(),tenantEnv.getIsActive(),tenantEnv.getTenantEnvId());
    }

    public TenantEnv getByEnvId(int tenantEnvId) {

        TenantEnv tenantEnv=tenantEnvironmentRepository.findByTenantEnvId(tenantEnvId);
        return  tenantEnv;
    }
}
