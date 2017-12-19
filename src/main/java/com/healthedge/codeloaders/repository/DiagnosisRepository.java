package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, String> {
}
