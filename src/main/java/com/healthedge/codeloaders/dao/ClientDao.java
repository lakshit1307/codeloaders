package com.healthedge.codeloaders.dao;


import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.repository.ClientServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class ClientDao {

    private static final Logger LOGGER= LoggerFactory.getLogger("Client Dao initialzied");


    private ClientServiceRepository clientServiceRepository=new ClientServiceRepository() {

        @Override
        public Date getPayorCodeVersion(String codeType, EntityManager entityManager) {

            Query query= entityManager.createNativeQuery("SELECT EFF_START_DT FROM (SELECT * FROM SERVICES " +
                    "WHERE SERV_TYPE_CD= ?1 ORDER BY EFF_START_DT DESC) WHERE ROWNUM=1");
            query.setParameter(1,codeType);
            List<Date> dates= query.getResultList();
            if(dates.isEmpty())
                return null;
            else
                return dates.get(0) ;
        }

        @Override
        public List<String> getDistinctPayorCodes(EntityManager entityManager){

            List<String> distinctCodes;
            distinctCodes=entityManager.createNativeQuery("SELECT DISTINCT SERV_TYPE_CD FROM SERVICES")
                    .getResultList();
            return  distinctCodes;
        }

        @Override
        public void update(EntityManager entityManager,ClientService clientService){

            Query query=entityManager.createNativeQuery("UPDATE SERVICES SET SERV_SHORT_DSC= ?1,SERV_LONG_DSC= ?2,ALT_DSC= ?3 WHERE SERV_CD= ?4");
            query.setParameter(1,clientService.getServiceShortDesciption());
            query.setParameter(2,clientService.getServiceLongDesciption());
            query.setParameter(3,clientService.getServiceAlternateDesciption());
            query.setParameter(4,clientService.getServiceCode());
            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();

        }

        @Override
        public void terminate(EntityManager entityManager,ClientService clientService){

            Query query=entityManager.createNativeQuery("UPDATE SERVICES SET EFF_END_DT= ?1 WHERE SERV_CD= ?2");
            query.setParameter(1,clientService.getEffectiveEndDate());
            query.setParameter(2,clientService.getServiceCode());
            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();

        }
        @Override
        public void save(EntityManager entityManager,ClientService clientService){

            entityManager.getTransaction().begin();
            entityManager.persist(clientService);
            entityManager.getTransaction().commit();

        }
        @Override
        public List<String> getDistinctPayorCodes(EntityManager entityManager,String codeType){

            Query query=entityManager.createNativeQuery("SELECT SERV_CD FROM SERVICES WHERE SERV_TYPE_CD= ?1");
            query.setParameter(1,codeType);
            List<String> payorCodes=query.getResultList();

        return payorCodes;
        }


    };


    public Map<String,Date> getPayorVersionAllCodes(EntityManager entityManager){

        Map<String,Date> payorCodeTypeVersion=new HashMap<>();
        List<String> codeTypes;
        codeTypes=clientServiceRepository.getDistinctPayorCodes(entityManager);
        for(String codeType: codeTypes){
            payorCodeTypeVersion.put(codeType,clientServiceRepository.getPayorCodeVersion(codeType,entityManager));
        }

        return payorCodeTypeVersion;

    }


    public Date getPayorVersionOneCode(String codeType,EntityManager entityManager){

        return clientServiceRepository.getPayorCodeVersion(codeType,entityManager);

    }


    public void update(EntityManager entityManager, ClientService clientService){

        clientServiceRepository.update(entityManager,clientService);

    }

    public void terminate(EntityManager entityManager,ClientService clientService){
        clientServiceRepository.terminate(entityManager,clientService);
    }


    public void save(EntityManager entityManager,ClientService clientService){
        clientServiceRepository.save(entityManager,clientService);
    }


    public List<String> getPayorCodes(EntityManager entityManager,String codeType) {
        List<String> payorCode=clientServiceRepository.getDistinctPayorCodes(entityManager,codeType);
        return payorCode;
    }
}
