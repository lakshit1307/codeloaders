package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service,String> {
}
