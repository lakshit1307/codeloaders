package com.healthedge.codeloaders.dao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.repository.TenantRepository;


@Service
public class TenantDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(TenantDao.class);

	@Autowired
	private TenantRepository tenantRepository;

	public TenantDao () {
		LOGGER.info("TenantDao class initialized");
	}

	public List<Tenant> getAllTenant(){
		return tenantRepository.findAll();
	}
	
	public void save(final List<Tenant> tenants) {
		tenantRepository.save(tenants);
	}
	
	public void save(final Tenant tenant) {
		tenantRepository.save(tenant);
	}

	public Tenant findByName(String name){
		Tenant tenant=tenantRepository.findByName(name);
		return tenant;
	}


    public Tenant getTenantById(int tenantId) {
	  Tenant tenant=  tenantRepository.findByTenantId(tenantId);
	  return tenant;
    }

    public void update(Tenant tenant) {
    }
}
