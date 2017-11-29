package com.healthedge.codeloaders.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.entity.TenantEnv;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest 
@AutoConfigureTestDatabase
public class TenantDaoTest {
	
	@Autowired
	TenantDao tenantDao;

	@Test
	public void tenantPersist() {
		List<Tenant> tenants=tenantDao.getAllTenant();
        assertEquals("Check DB is empty first", 0, tenants.size());
        Tenant tenant=new Tenant();
		tenant.setDescription("new tenant");
		tenant.setIsActive(1);
		tenant.setIsAutoLoad(1);
		tenant.setName("NT");
		tenant.setTenantId(1);
		tenant.setUpdatedBy("pande");
		List<TenantEnv> tenantEnvs=new ArrayList<TenantEnv>();
		TenantEnv tenantEnv=new TenantEnv();
		tenantEnv.setDescription("dev env");
		tenantEnv.setIsActive(1);
		tenantEnv.setIsAutoLoad(1);
		tenantEnv.setName("dev");
		tenantEnv.setTenantEnvId(1);
//		tenantEnv.setTenantId(1);
		tenantEnv.setUpdatedBy("pande");
		tenantEnv.setDbPassword("root");
		tenantEnv.setDbUrl("jdbc:oracle:thin:@//localhost:1521/xe");
		tenantEnv.setDbUserName("client");
		tenantEnv.setCreatedBy("pande");
		tenant.setCreatedBy("pande");
		tenantEnvs.add(tenantEnv);
		tenantEnv.setTenant(tenant);
		tenant.setTenantEnv(tenantEnvs);
		tenantDao.save(tenant);
		tenants=tenantDao.getAllTenant();
        assertEquals("Check DB size of the tenants table", 1, tenants.size());
        assertEquals("Check DB size of the tenant-envs table", 1, tenants.get(0).getTenantEnv().size());
	}

}
