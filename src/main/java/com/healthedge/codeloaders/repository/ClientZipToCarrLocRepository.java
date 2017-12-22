package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.entity.ClientZipToCarrLocEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
public class ClientZipToCarrLocRepository implements ClientServiceRepository {
    @Override
    public Date getPayorCodeVersion(String codeType, EntityManager entityManager) {

        Query query = entityManager.createNativeQuery("SELECT EFF_START_DT FROM (SELECT * FROM ZIP_TO_CARRIER_LOCALITY " +
                "ORDER BY EFF_START_DT DESC) WHERE ROWNUM=1");
        List<Date> dates = query.getResultList();
        if (dates.isEmpty())
            return new Date(42198);
        else
            return dates.get(0);
    }

    @Override
    public List<String> getDistinctPayorCodes(EntityManager entityManager) {
        List<String> distinctCodes;
        distinctCodes = entityManager.createNativeQuery("SELECT DISTINCT ZIP_CD FROM ZIP_TO_CARRIER_LOCALITY").getResultList();
        return distinctCodes;
    }

    @Override
    public void update(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        entityManager.getTransaction().begin();
        for (ClientZipToCarrLocEntity clientZipToCarrLocEntity : (List<ClientZipToCarrLocEntity>) clientEntity) {
            Query query = entityManager.createNativeQuery(
                    "UPDATE ZIP_TO_CARRIER_LOCALITY SET CARR_NBR= ?1,LOCALITY_NBR= ?2,CVC_TYPE_CD=?3,INST_ACTIVE_CD=?4" +
                            "CONCEPT_FULFILLED_CD=?5,TX_CNT=?6,LAST_TX_DT=?7,LAST_TX_USER_TXT=?8 WHERE ZIP_CD= ?9");
            query.setParameter(1, clientZipToCarrLocEntity.getCarrierNbr());
            query.setParameter(2, clientZipToCarrLocEntity.getLocalityNbr());
            query.setParameter(3, clientZipToCarrLocEntity.getCvcTypeCd());
            query.setParameter(4, clientZipToCarrLocEntity.getInstActiveCd());
            query.setParameter(5, clientZipToCarrLocEntity.getConceptFulfilledCd());
            query.setParameter(6, clientZipToCarrLocEntity.getTxCnt());
            query.setParameter(7, clientZipToCarrLocEntity.getLastTransactionDate());
            query.setParameter(8, clientZipToCarrLocEntity.getLastTransactionUserText());
            query.setParameter(9, clientZipToCarrLocEntity.getZipCode());
            query.executeUpdate();
        }
        entityManager.getTransaction().commit();

    }

    @Override
    public void terminate(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {

    }

    @Override
    public void save(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {

        entityManager.getTransaction().begin();

        for (ClientZipToCarrLocEntity clientZipToCarrLocEntity : (List<ClientZipToCarrLocEntity>) clientEntity) {

            Query query=entityManager.createNativeQuery("INSERT INTO ZIP (ZIP_CD,TX_CNT,LAST_TX_DT,LAST_TX_USER_TXT) " +
                    "values(?1,?2,?3,?4)");
            query.setParameter(1,clientZipToCarrLocEntity.getZipCode());
            query.setParameter(2,clientZipToCarrLocEntity.getTxCnt());
            query.setParameter(3,clientZipToCarrLocEntity.getLastTransactionDate());
            query.setParameter(4,clientZipToCarrLocEntity.getLastTransactionUserText());
            query.executeUpdate();

            query=entityManager.createNativeQuery("INSERT INTO ZIP_TO_CARRIER_LOCALITY (ZIP_TO_CARR_LOCALITY_ID,EFF_START_DT,CARR_NBR,\n" +
                    "LOCALITY_NBR,VER_ID,CVC_TYPE_CD,INST_ACTIVE_CD,CONCEPT_FULFILLED_CD,VER_EFF_DT,\n" +
                    "VER_EXPIRE_DT,ENDOR_EFF_DT,ENDOR_EXPIRE_DT,TX_CNT,LAST_TX_DT,LAST_TX_USER_TXT,SCHED_ID,ZIP_CD) " +
                    "values (?1,?2,?3,?4,?5,?6,?7,?8,\n" +
                    "?9,?10,?11,?12,?13,?14,?15,?16,?17);"); //TODO modify the query to pick next value in sequence

            query.setParameter(1,clientZipToCarrLocEntity.getZipToCarrLocalityId());
            query.setParameter(2,clientZipToCarrLocEntity.getEffectiveStartDate());
            query.setParameter(3,clientZipToCarrLocEntity.getCarrierNbr());
            query.setParameter(4,clientZipToCarrLocEntity.getLocalityNbr());
            query.setParameter(5,clientZipToCarrLocEntity.getVersionId());
            query.setParameter(6,clientZipToCarrLocEntity.getCvcTypeCd());
            query.setParameter(7,clientZipToCarrLocEntity.getInstActiveCd());
            query.setParameter(8,clientZipToCarrLocEntity.getConceptFulfilledCd());
            query.setParameter(9,clientZipToCarrLocEntity.getVersionEffectiveDate());
            query.setParameter(10,clientZipToCarrLocEntity.getVersionExpiryDate());
            query.setParameter(11,clientZipToCarrLocEntity.getEndorEffectiveDate());
            query.setParameter(12,clientZipToCarrLocEntity.getEndorExpiryDate());
            query.setParameter(13,clientZipToCarrLocEntity.getTxCnt());
            query.setParameter(14,clientZipToCarrLocEntity.getLastTransactionDate());
            query.setParameter(15,clientZipToCarrLocEntity.getLastTransactionUserText());
            query.setParameter(16,clientZipToCarrLocEntity.getScheduleId());
            query.setParameter(17,clientZipToCarrLocEntity.getZipCode());

            query.executeUpdate();
        }

        entityManager.getTransaction().commit();



    }

    @Override
    public List<String> getDistinctPayorCodes(EntityManager entityManager, String clientEntity) {

        Query query = entityManager.createNativeQuery("SELECT ZIP_CD FROM ZIP_TO_CARRIER_LOCALITY");
        List<String> payorCodes = query.getResultList();
        return payorCodes;
    }
}
