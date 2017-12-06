package com.healthedge.codeloaders.business;

import com.healthedge.codeloaders.dao.TenantDao;
import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.service.FindDelta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LoadPayorDelta {

    @Autowired
    private FindDelta findDelta;

    @Autowired
    private TenantDao tenantDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadPendingCodes.class);

    public  LoadPayorDelta(){
        LOGGER.info("Loading Payor Delta initalized");
    }

    public void startLoadProcess(){

        List<Tenant> tenants = tenantDao.getAllTenant();
        for (Tenant tenant : tenants) {
            LOGGER.info("Processing delta of Tenant: " + tenant.getTenantId());

            for (TenantEnv tenantEnv : tenant.getTenantEnv()) {
                LOGGER.info("Delta of tenant environment " + tenantEnv.getTenantEnvId());
                findDelta.getPayorDelta(tenantEnv,"cp",null);
            }

        }


    }
}
