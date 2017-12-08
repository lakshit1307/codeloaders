package com.healthedge.codeloaders.service;


import com.healthedge.codeloaders.dao.ClientDao;
import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.ClientService;
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


    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private ClientDao clientDao;


    public FindDelta() {
        LOGGER.info("PayorDelta initialized");
    }


    public  List<Service> getDeltaFromCloader(String codeType, Date payorCodeVersion){


            updateCloaderCodeVesion();
            Date cloaderCodeVersion=latestCloaderCodeVersion.get(codeType);

            if(cloaderCodeVersion.after(payorCodeVersion)){

                    LOGGER.info(codeType +" delta creation started");
                    List<Service> completeCloaderDelta=serviceDao.getDeltaCodes(payorCodeVersion,codeType);

                return completeCloaderDelta;
                }
                else{
                    LOGGER.info(codeType+" is upto date with CLOADER db");
                    return null;
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

    public Map<String,List<Service>> prepareDelta(List<Service> delta, List<String> payorCodes) {

        Map<String,List<Service>> deltaLoad=new HashMap<>();
        List<Service> createList=new ArrayList<>();
        List<Service> appendList=new ArrayList<>();
        List<Service> terminateList=new ArrayList<>();
        List<Service> existingCodeList=new ArrayList<>();
        List<Service> tempList=new ArrayList<>(delta);

        for(String code:payorCodes){
            for(Service service:delta){
                if(service.getServiceCode().equals(code)){
                    existingCodeList.add(service);
                    tempList.remove(service);
                }
            }
        }

        for(Service service: existingCodeList){
            if(service.getAction().equals(CREATE)){
                createList.add(service);
            }
            else
                if(service.getAction().equals(APPEND)){
                appendList.add(service);
                }
            else
                terminateList.add(service);
            }
         for(Service service:tempList){
            createList.add(service);
         }
        deltaLoad.put(CREATE,createList);
        deltaLoad.put(APPEND,appendList);
        deltaLoad.put(TERMINATE,terminateList);

        return deltaLoad;
    }
}
