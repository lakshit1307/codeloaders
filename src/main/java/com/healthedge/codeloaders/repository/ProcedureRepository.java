package com.healthedge.codeloaders.repository;

import com.healthedge.codeloaders.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository<Procedure,Long> {

}

