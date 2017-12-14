package com.healthedge.codeloaders.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.entity.ClientService;


@Service
public class ClientServiceRepositoryImpl implements ClientServiceRepository {

	@Override
	public Date getPayorCodeVersion(String codeType, EntityManager entityManager) {

		Query query = entityManager.createNativeQuery("SELECT EFF_START_DT FROM (SELECT * FROM SERVICE "
				+ "WHERE SERV_TYPE_CD= ?1 ORDER BY EFF_START_DT DESC) WHERE ROWNUM=1");
		query.setParameter(1, codeType);
		List<Date> dates = query.getResultList();
		if (dates.isEmpty())
			return new Date(42198);
		else
			return dates.get(0);
	}

	@Override
	public List<String> getDistinctPayorCodes(EntityManager entityManager) {

		List<String> distinctCodes;
		distinctCodes = entityManager.createNativeQuery("SELECT DISTINCT SERV_TYPE_CD FROM SERVICE").getResultList();
		return distinctCodes;
	}

	/*@Override
	public void update(EntityManager entityManager, List<ClientService> clientServices) {
		entityManager.getTransaction().begin();
		for (ClientService clientService : clientServices) {
			Query query = entityManager.createNativeQuery(
					"UPDATE SERVICE SET SERV_SHORT_DSC= ?1,SERV_LONG_DSC= ?2,ALT_DSC= ?3,EFF_START_DT=?4,EFF_END_DT=?5 WHERE SERV_CD= ?6");
			query.setParameter(1, clientService.getServiceShortDesciption());
			query.setParameter(2, clientService.getServiceLongDesciption());
			query.setParameter(3, clientService.getServiceAlternateDesciption());
			query.setParameter(4,clientService.getEffectiveStartDate());
			query.setParameter(5, clientService.getEffectiveEndDate());
			query.setParameter(6, clientService.getServiceCode());
			query.executeUpdate();
		}
		entityManager.getTransaction().commit();
	}*/

    @Override
    public void update(EntityManager entityManager, List<ClientService> clientServices) {
        entityManager.getTransaction().begin();
        for (ClientService clientService : clientServices) {
            Query query = entityManager.createNativeQuery(
                    "UPDATE SERVICE SET SERV_SHORT_DSC= ?1,SERV_LONG_DSC= ?2,ALT_DSC= ?3 WHERE SERV_CD= ?4");
            query.setParameter(1, clientService.getServiceShortDesciption());
            query.setParameter(2, clientService.getServiceLongDesciption());
            query.setParameter(3, clientService.getServiceAlternateDesciption());
            query.setParameter(4, clientService.getServiceCode());
            query.executeUpdate();
        }
        entityManager.getTransaction().commit();
    }

	@Override
	public void terminate(EntityManager entityManager, List<ClientService> clientServices) {
		entityManager.getTransaction().begin();
		for (ClientService clientService : clientServices) {
			Query query = entityManager.createNativeQuery("UPDATE SERVICE SET EFF_END_DT= ?1 WHERE SERV_CD= ?2");
			query.setParameter(1, clientService.getEffectiveEndDate());
			query.setParameter(2, clientService.getServiceCode());
			query.executeUpdate();
		}
		entityManager.getTransaction().commit();

	}

	@Override
	public void save(EntityManager entityManager, List<ClientService> clientServices) {
		entityManager.getTransaction().begin();
		for (ClientService clientService : clientServices) {
			entityManager.persist(clientService);
		}
		entityManager.getTransaction().commit();

	}

	@Override
	public List<String> getDistinctPayorCodes(EntityManager entityManager, String codeType) {

		Query query = entityManager.createNativeQuery("SELECT SERV_CD FROM SERVICE WHERE SERV_TYPE_CD= ?1");
		query.setParameter(1, codeType);
		List<String> payorCodes = query.getResultList();
		return payorCodes;
	}

}
