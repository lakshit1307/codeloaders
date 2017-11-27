package com.healthedge.codeloaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthedge.codeloaders.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

}
