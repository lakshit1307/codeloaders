package com.healthedge.codeloaders.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.healthedge.codeloaders.entity.Service;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest 
@AutoConfigureTestDatabase
public class ServiceDaoTest {
	
	@Autowired
	private ServiceDao serviceDao;

	@Test
	public void servicePersistTest() {
		List<Service> services=serviceDao.getAll();
        assertEquals("Check DB is empty first", 0, services.size());
        Service service=new Service();
        service.setAction("ac");
		service.setCodeProcessingHistoryId(1212);
		service.setEffectiveEndDate(new Date());
		service.setEffectiveStartDate(new Date());
		service.setLastTransactionDate(new Date());
		service.setServiceAlternateDesciption("alt desc");
		service.setServiceLongDesciption("long desc");
		service.setServiceShortDesciption("short ");
		service.setStandardizedServiceCode("a2");
		service.setworkFlowCode("sd");
		service.setServiceCode("d");
		service.setLastTransactionUserText("user text");
		serviceDao.save(service);
		services=serviceDao.getAll();
        assertEquals("check Account has been created", 1, services.size());
	}
	
	@Test
	public void serviceUpdateCodeTest() {
		List<Service> services=serviceDao.getAll();
        assertEquals("Check DB is empty first", 0, services.size());
        Service service=new Service();
        service.setAction("ac");
		service.setCodeProcessingHistoryId(1212);
		service.setEffectiveEndDate(new Date());
		service.setEffectiveStartDate(new Date());
		service.setLastTransactionDate(new Date());
		service.setServiceAlternateDesciption("alt desc");
		service.setServiceLongDesciption("long desc");
		service.setServiceShortDesciption("short ");
		service.setStandardizedServiceCode("d");
		service.setworkFlowCode("sd");
		service.setServiceCode("d");
		service.setLastTransactionUserText("user text");
		serviceDao.save(service);
		services=serviceDao.getAll();
        service=new Service();
        service.setAction("up");
		service.setCodeProcessingHistoryId(1212);
		service.setEffectiveEndDate(new Date());
		service.setEffectiveStartDate(new Date());
		service.setLastTransactionDate(new Date());
		service.setServiceAlternateDesciption("new alt desc");
		service.setServiceLongDesciption("new long desc");
		service.setServiceShortDesciption("new short ");
		service.setStandardizedServiceCode("d");
		service.setworkFlowCode("sd");
		service.setServiceCode("d");
		service.setLastTransactionUserText("user text");
		service.setVersion("new version");
		serviceDao.update(service);
		services=serviceDao.getAll();
        assertEquals("check Account has been created", service, services.get(0));
	}

	@Test
	public void serviceTerminateCodeTest() {
		List<Service> services=serviceDao.getAll();
        assertEquals("Check DB is empty first", 0, services.size());
        Service service=new Service();
        service.setAction("ac");
		service.setCodeProcessingHistoryId(1212);
		service.setEffectiveStartDate(new Date());
		service.setLastTransactionDate(new Date());
		service.setServiceAlternateDesciption("alt desc");
		service.setServiceLongDesciption("long desc");
		service.setServiceShortDesciption("short ");
		service.setStandardizedServiceCode("d");
		service.setworkFlowCode("sd");
		service.setServiceCode("d");
		service.setLastTransactionUserText("user text");
		serviceDao.save(service);
		services=serviceDao.getAll();
        service.setAction("terminate");
		service.setEffectiveEndDate(new Date());
		serviceDao.terminate(service);
		services=serviceDao.getAll();
        assertEquals("check Account has been created", service, services.get(0));
	}

}
