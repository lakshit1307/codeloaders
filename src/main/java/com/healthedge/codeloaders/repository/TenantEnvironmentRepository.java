package com.healthedge.codeloaders.repository;


import com.healthedge.codeloaders.entity.TenantEnv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantEnvironmentRepository extends JpaRepository<TenantEnv,Integer>{


    @Query(value = "SELECT * FROM M_TENANT_ENV WHERE TENANT_ID=?1",nativeQuery = true)
    List<TenantEnv> findByTenantId(int id);

    TenantEnv findByTenantEnvId(int id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE M_TENANT_ENV SET DESCRIPTION=?1,DB_URL=?2,DB_USER_NAME=?3,DB_PASSWORD=?4,IS_AUTO_LOAD=?5,IS_ACTIVE=?6 WHERE TENANT_ENV_ID=?7",nativeQuery = true)
    void update(String description, String dbUrl, String dbUserName, String dbPassword,Integer isAutoLoad, Integer isActive, Integer tenantEnvId);
}
