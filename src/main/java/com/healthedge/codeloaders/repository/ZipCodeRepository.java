package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipCode,Long> {
}
