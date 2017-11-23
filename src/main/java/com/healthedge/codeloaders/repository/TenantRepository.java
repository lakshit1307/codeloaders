package com.healthedge.codeloaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthedge.codeloaders.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
	
	@Query("SELECT t from Tenant t")
	public List<Tenant> getAll();

}
