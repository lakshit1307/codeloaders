package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.ZipToCarrierLocality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ZipToCarrierLocalityRepository extends JpaRepository<ZipToCarrierLocality,Long>{
}
