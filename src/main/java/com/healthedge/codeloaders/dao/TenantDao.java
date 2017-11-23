package com.healthedge.codeloaders.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.repository.TenantRepository;


@Service
public class TenantDao {
	
	@Autowired
	private TenantRepository tenantRepository;
	
	public List<Tenant> getAllTenant(){
		return tenantRepository.getAll();
	}
	
	public void save(List<Tenant> tenants) {
		tenantRepository.save(tenants);
	}
	
	public void save(Tenant tenant) {
		tenantRepository.save(tenant);
	}

}
