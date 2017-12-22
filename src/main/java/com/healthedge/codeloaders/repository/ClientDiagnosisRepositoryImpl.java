package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.entity.ClientDiagnosisEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
public class ClientDiagnosisRepositoryImpl implements ClientServiceRepository{
    @Override
    public Date getPayorCodeVersion(String codeType, EntityManager entityManager) {
        Query query = entityManager.createNativeQuery("SELECT EFF_START_DT FROM (SELECT * FROM DIAGNOSIS "
                + "WHERE DIAG_TYPE_CD=:code_type ORDER BY EFF_START_DT DESC) WHERE ROWNUM=1");
        query.setParameter("code_type", codeType);
        List<Date> dates = query.getResultList();
        if (dates.isEmpty())
            return new Date(42198);
        else
            return dates.get(0);
    }

    @Override
    public List<String> getDistinctPayorCodes(EntityManager entityManager) {
        return null;
    }

    @Override
    public void update(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        entityManager.getTransaction().begin();
        for (ClientDiagnosisEntity clientDiagnosisEntity : (List<ClientDiagnosisEntity>) clientEntity) {
            Query query = entityManager.createNativeQuery(
                    "UPDATE DIAGNOSIS SET DIAG_SHORT_DSC=:shortdesc,DIAG_LONG_DSC=:longdesc,ALT_DSC=:fulldesc,TX_CNT=:tx_cnt," +
                            "LAST_TX_DT=:last_tx_dt,LAST_TX_USER_TXT=:last_tx_usr_txt WHERE DIAG_CD=:code");
            query.setParameter("shortdesc", clientDiagnosisEntity.getDiagnosisShortDescription());
            query.setParameter("longdesc", clientDiagnosisEntity.getDiagnosisLongDescription());
            query.setParameter("fulldesc", clientDiagnosisEntity.getAlternateDescription());
            query.setParameter("tx_cnt",clientDiagnosisEntity.getTxCnt());
            query.setParameter("last_tx_dt",clientDiagnosisEntity.getLastTransactionDate());
            query.setParameter("last_tx_usr_txt",clientDiagnosisEntity.getLastTransactionUserText());
            query.setParameter("code", clientDiagnosisEntity.getCode());
            query.executeUpdate();
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void terminate(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        entityManager.getTransaction().begin();
        for (ClientDiagnosisEntity clientDiagnosisEntity : (List<ClientDiagnosisEntity>) clientEntity) {
            Query query = entityManager.createNamedQuery("UPDATE DIAGNOSIS SET EFF_END_DT=:eff_end_date WHERE DIAG_CD=:code");
            query.setParameter("eff_end_date", clientDiagnosisEntity.getEffectiveEndDate());
            query.setParameter("code", clientDiagnosisEntity.getCode());
            query.executeUpdate();
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void save(EntityManager entityManager, List<? extends ClientBaseEntity> clientEntity) {
        entityManager.getTransaction().begin();

        for (ClientDiagnosisEntity clientDiagnosisEntity : (List<ClientDiagnosisEntity>) clientEntity) {

            Query query=entityManager.createNativeQuery("INSERT INTO DIAGNOSIS (DIAG_CD,DIAG_TYPE_CD,DIAG_SHORT_DSC,\n" +
                    "DIAG_LONG_DSC,ALT_DSC,EFF_START_DT,EFF_END_DT,STANDARDIZED_DIAG_CD,WRK_FLOW_CD,\n" +
                    "TX_CNT,LAST_TX_DT,LAST_TX_USER_TXT) " +
                    "values (?1,?2,?3,?4,?5,?6,?7,?8,\n" +
                    "?9);");

            query.setParameter(1,clientDiagnosisEntity.getCode());
            query.setParameter(2,clientDiagnosisEntity.getDiagnosisTypeCode());
            query.setParameter(3,clientDiagnosisEntity.getDiagnosisShortDescription());
            query.setParameter(4,clientDiagnosisEntity.getDiagnosisLongDescription());
            query.setParameter(5,clientDiagnosisEntity.getAlternateDescription());
            query.setParameter(6,clientDiagnosisEntity.getEffectiveStartDate());
            query.setParameter(7,clientDiagnosisEntity.getEffectiveEndDate());
            query.setParameter(8,clientDiagnosisEntity.getStandardizedDiagnosisCode());
            query.setParameter(9,clientDiagnosisEntity.getWorkFlowCode());

            query.executeUpdate();
        }

        entityManager.getTransaction().commit();
    }

    @Override
    public List<String> getDistinctPayorCodes(EntityManager entityManager, String codeType) {
        Query query = entityManager.createNativeQuery("SELECT DIAG_CD FROM DIAGNOSIS WHERE DIAG_TYPE_CD=:code_type");
        query.setParameter("code_type", codeType);
        List<String> payorCodes = query.getResultList();
        return payorCodes;
    }
}
