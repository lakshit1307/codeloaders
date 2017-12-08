package com.healthedge.codeloaders.business;

import com.healthedge.codeloaders.dao.ClientDao;
import com.healthedge.codeloaders.dao.TenantDao;
import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.service.EntityManagerService;
import com.healthedge.codeloaders.service.FindDelta;
import com.healthedge.codeloaders.service.ServiceEntityToClientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class LoadPayorDelta extends ServiceEntityToClientEntity{

    @Autowired
    private FindDelta findDelta;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private ClientDao clientDao;

    private static final String CREATE="CREATE";
    private static final String APPEND="APPEND";
    private static final String TERMINATE="TERMINATE";

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadPendingCodes.class);

    public  LoadPayorDelta(){
        LOGGER.info("Loading Payor Delta initalized");
    }

    public void startLoadProcess(){

        String codeType="cp";
        List<Tenant> tenants = tenantDao.getAllTenant();
        for (Tenant tenant : tenants) {
            LOGGER.info("Processing delta of Tenant: " + tenant.getTenantId());

            for (TenantEnv tenantEnv : tenant.getTenantEnv()) {
                LOGGER.info("Delta of tenant environment " + tenantEnv.getTenantEnvId());
                EntityManagerService entityManagerService=new EntityManagerService();
                EntityManager entityManager=entityManagerService.
                        createEntityManagerService(tenantEnv.getDbUrl(),tenantEnv.getDbUserName(),tenantEnv.getDbPassword());
                //payorCodeVersion can be null if the db is empty. Note to take care of that
                Date toCodeVersion=clientDao.getPayorVersionOneCode(codeType,entityManager);
                List<String> payorCodes=clientDao.getPayorCodes(entityManager,codeType);
                List<Service> cloaderDelta=findDelta.getDeltaFromCloader(codeType,toCodeVersion);
                Map<String,List<Service>> deltaRecords=findDelta.prepareDelta(cloaderDelta,payorCodes);
                persistDeltaData(deltaRecords,entityManager);
            }
        }
    }

    public void persistDeltaData(Map<String,List<Service>> deltaRecords,EntityManager entityManager){

        if(deltaRecords.containsKey(CREATE)){
            List<Service> createList=deltaRecords.get(CREATE);
            for(Service service:createList){
                ClientService clientService=serviceTransformer(service);
                clientDao.save(entityManager,clientService);
            }
        }
        if(deltaRecords.containsKey(APPEND)){
            List<Service> appendList=deltaRecords.get(APPEND);
            for(Service service:appendList){
                ClientService clientService=serviceTransformer(service);
                clientDao.update(entityManager,clientService);
            }
        }
        if(deltaRecords.containsKey(TERMINATE)){
            List<Service> terminateList=deltaRecords.get(TERMINATE);
            for(Service service:terminateList){
                ClientService clientService=serviceTransformer(service);
                clientDao.terminate(entityManager,clientService);
            }

        }
    }
}
