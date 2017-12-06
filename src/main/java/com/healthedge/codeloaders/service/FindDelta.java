package com.healthedge.codeloaders.service;

import com.healthedge.codeloaders.dao.ClientDao;
import com.healthedge.codeloaders.dao.ServiceDao;

import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.entity.TenantEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.persistence.EntityManager;
import java.util.*;

@org.springframework.stereotype.Service
public class FindDelta {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDao.class);
    private static Map<String,Date> latestCloaderCodeVersion=new HashMap<>();
    private static final String CREATE="CREATE";
    private static final String APPEND="APPEND";
    private static final String TERMINATE="TERMINATE";

    @Autowired
    private ServiceDao serviceDao;

    public FindDelta() {
        LOGGER.info("PayorDelta initialized");
        //updateCloaderCodeVesion();
    }


    public  void getPayorDelta(TenantEnv tenantEnv, String codeType,Date toCodeVersion){

        Boolean toCodeVersionflag=false;

        updateCloaderCodeVesion();
        EntityManagerService entityManagerService=new EntityManagerService();
        EntityManager entityManager=entityManagerService.
                createEntityManagerService(tenantEnv.getDbUrl(),tenantEnv.getDbUserName(),tenantEnv.getDbPassword());

        ClientDao clientDao=new ClientDao();


        if(!toCodeVersionflag){

            Date payorCodeVersion=clientDao.getPayorVersionOneCode(codeType,entityManager);
            Date cloaderCodeVersion=latestCloaderCodeVersion.get(codeType);


            if(cloaderCodeVersion.after(payorCodeVersion)){
                    LOGGER.info(codeType +" delta creation started");
                    List<Service> completeDelta=serviceDao.getDeltaCodes(payorCodeVersion,codeType);
                    List<Service> createList=new ArrayList<>();
                    List<Service> appendList=new ArrayList<>();
                    List<Service> terminateList=new ArrayList<>();

                    for(Service service:completeDelta){

                        if(service.getAction().equals(CREATE)){
                            createList.add(service);
                        }
                        else if(service.getAction().equals(APPEND)){
                            appendList.add(service);
                        }
                        else if(service.getAction().equals(TERMINATE)){
                            terminateList.add(service);
                        }
                    }




                for(Service service:createList){
                    ClientService clientService=new ClientService();
                    clientService.setEffectiveStartDate(service.getEffectiveStartDate());
                    clientService.setEffectiveEndDate(service.getEffectiveEndDate());
                    clientService.setServiceCode(service.getServiceCode());
                    clientService.setServiceAlternateDesciption(service.getServiceAlternateDesciption());
                    clientService.setServiceLongDesciption(service.getServiceLongDesciption());
                    clientService.setServiceShortDesciption(service.getServiceShortDesciption());
                    clientService.setServiceTypeCode(service.getServiceTypeCD());
                    clientService.setStandardizedServiceCode(service.getStandardizedServiceCode());
                    clientService.setTransactionCount(service.getTxCnt());
                    clientService.setLastTransactionDate(service.getLastTransactionDate());
                    clientService.setLastTransactionUserText(service.getLastTransactionUserText());
                    clientDao.save(entityManager,clientService);

                }

                for(Service service:appendList){
                    ClientService clientService=new ClientService();
                    clientService.setEffectiveStartDate(service.getEffectiveStartDate());
                    clientService.setEffectiveEndDate(service.getEffectiveEndDate());
                    clientService.setServiceCode(service.getServiceCode());
                    clientService.setServiceAlternateDesciption(service.getServiceAlternateDesciption());
                    clientService.setServiceLongDesciption(service.getServiceLongDesciption());
                    clientService.setServiceShortDesciption(service.getServiceShortDesciption());
                    clientService.setServiceTypeCode(service.getServiceTypeCD());
                    clientService.setStandardizedServiceCode(service.getStandardizedServiceCode());
                    clientService.setTransactionCount(service.getTxCnt());
                    clientService.setLastTransactionDate(service.getLastTransactionDate());
                    clientService.setLastTransactionUserText(service.getLastTransactionUserText());
                    clientDao.update(entityManager,clientService);

                }

                for(Service service:terminateList){
                    ClientService clientService=new ClientService();
                    clientService.setEffectiveStartDate(service.getEffectiveStartDate());
                    clientService.setEffectiveEndDate(service.getEffectiveEndDate());
                    clientService.setServiceCode(service.getServiceCode());
                    clientService.setServiceAlternateDesciption(service.getServiceAlternateDesciption());
                    clientService.setServiceLongDesciption(service.getServiceLongDesciption());
                    clientService.setServiceShortDesciption(service.getServiceShortDesciption());
                    clientService.setServiceTypeCode(service.getServiceTypeCD());
                    clientService.setStandardizedServiceCode(service.getStandardizedServiceCode());
                    clientService.setTransactionCount(service.getTxCnt());
                    clientService.setLastTransactionDate(service.getLastTransactionDate());
                    clientService.setLastTransactionUserText(service.getLastTransactionUserText());
                    clientDao.terminate(entityManager,clientService);

                }


                }
                else{
                    LOGGER.info(codeType+" is upto date with CLOADER db");
                }

        }

        else{

        }

    }
    private void updateCloaderCodeVesion(){

        Date codeVersion;
        List<String> allCodeTypes=serviceDao.getDistinctCodeTypes();
        for (int i=0;i<allCodeTypes.size();++i){
            codeVersion=serviceDao.getCodeVersion(allCodeTypes.get(i));
            latestCloaderCodeVersion.put(allCodeTypes.get(i),codeVersion);
        }
    }




}
