package com.healthedge.codeloaders.service;


import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.entity.TenantEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@org.springframework.stereotype.Service
public class FindDelta extends ServiceEntityToClientEntity{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDao.class);
    private static Map<String,Date> latestCloaderCodeVersion=new HashMap<>();
    private static final String CREATE="CREATE";
    private static final String APPEND="APPEND";
    private static final String TERMINATE="TERMINATE";
    private Map<String, List<Service>> result = new ConcurrentHashMap<>();

    @Autowired
    private ServiceDao serviceDao;


    public FindDelta() {
        LOGGER.info("PayorDelta initialized");
    }


    public  Map<String,List<Service>> getPayorDelta(TenantEnv tenantEnv, String codeType,Date payorCodeVersion){


            updateCloaderCodeVesion();
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

                result.put(CREATE, createList);
                result.put(APPEND, appendList);
                result.put(TERMINATE, terminateList);

                }
                else{
                    LOGGER.info(codeType+" is upto date with CLOADER db");
                }
            return result;
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
