package com.healthedge.codeloaders.controller;


import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.dao.TenantDao;
import com.healthedge.codeloaders.dao.TenantEnvironmentDao;
import com.healthedge.codeloaders.dto.EnvironmentDTO;
import com.healthedge.codeloaders.dto.TenantDTO;
import com.healthedge.codeloaders.entity.Tenant;
import com.healthedge.codeloaders.entity.TenantEnv;
import com.healthedge.codeloaders.util.SequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TenantController {

    private static final Logger LOGGER=LoggerFactory.getLogger(TenantController.class);

    private TenantController(){
        LOGGER.info("Tenant controller class is initialized");
    }

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private TenantEnvironmentDao tenantEnvironmentDao;

    @PostMapping("/tenants")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTenant(@RequestBody TenantDTO tenantDTO){

    Tenant tenant=new Tenant();
    tenant.setTenantId(SequenceGenerator.getNextSeq("M_TENANT"));
    tenant.setName(tenantDTO.getName());
    tenant.setDescription(tenantDTO.getDescription());
    tenant.setIsAutoLoad(tenantDTO.getIsAutoLoad());
    tenant.setIsActive(tenantDTO.getIsActive());
    //time and user details
    tenant.setCreatedDate(new Date());
    tenant.setUpdatedDate(new Date());
    tenant.setUpdatedBy(CodeLoaderConstants.TRANSACTION_USER);
    tenant.setCreatedBy(CodeLoaderConstants.TRANSACTION_USER);
    tenantDao.save(tenant);

    List<EnvironmentDTO> environmentDTOList;
    environmentDTOList=tenantDTO.getEnvironments();
    for(EnvironmentDTO environmentDTO:environmentDTOList)
    {
        TenantEnv tenantEnv=new TenantEnv();
        tenantEnv.setTenantEnvId((SequenceGenerator.getNextSeq("M_TENANT_ENV")));
        tenantEnv.setName(environmentDTO.getName());
        tenantEnv.setDescription(environmentDTO.getDescription());
        tenantEnv.setDbUrl(environmentDTO.getDbUrl());
        tenantEnv.setDbPassword(environmentDTO.getDbPassword());
        tenantEnv.setDbUserName(environmentDTO.getDbUserName());
        tenantEnv.setIsAutoLoad(environmentDTO.getIsAutoLoad());
        tenantEnv.setIsActive(environmentDTO.getIsActive());
        tenantEnv.setTenant(tenant);
        //time and user details
        tenantEnv.setCreatedDate(new Date());
        tenantEnv.setUpdatedDate(new Date());
        tenantEnv.setUpdatedBy(CodeLoaderConstants.TRANSACTION_USER);
        tenantEnv.setCreatedBy(CodeLoaderConstants.TRANSACTION_USER);
        tenantEnvironmentDao.save(tenantEnv);
    }

    }

    @PostMapping("/tenants/search")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TenantDTO searchTenant(@RequestBody TenantDTO tenantDTO){
        String name=tenantDTO.getName();
        Tenant tenant=tenantDao.findByName(name);


        TenantDTO tenantDTO1=new TenantDTO();
        tenantDTO1.setDescription(tenant.getDescription());
        tenantDTO1.setIsAutoLoad(tenant.getIsAutoLoad());
        tenantDTO1.setName(tenant.getName());
        tenantDTO1.setTenantId(tenant.getTenantId());

        List<EnvironmentDTO> environments= new ArrayList<>();
        for(TenantEnv tenantEnv: tenant.getTenantEnv()){
            EnvironmentDTO environmentDTO=new EnvironmentDTO();
            environmentDTO.setName(tenantEnv.getName());
            environmentDTO.setDbPassword(tenantEnv.getDbPassword());
            environmentDTO.setDbUrl(tenantEnv.getDbUrl());
            environmentDTO.setDbUserName(tenantEnv.getDbUserName());
            environmentDTO.setDescription(tenantEnv.getDescription());
            environmentDTO.setIsAutoLoad(tenantEnv.getIsAutoLoad());
            environmentDTO.setTenantEnvId(tenantEnv.getTenantEnvId());
            environments.add(environmentDTO);
        }
        tenantDTO1.setEnvironments(environments);
        return  tenantDTO1;

    }

    @GetMapping("/tenants/{tenantId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TenantDTO getTenant(@PathVariable(value="tenantId") int tenantId){
        Tenant tenant=tenantDao.getTenantById(tenantId);
        TenantDTO tenantDTO1=new TenantDTO();
        tenantDTO1.setDescription(tenant.getDescription());
        tenantDTO1.setIsAutoLoad(tenant.getIsAutoLoad());
        tenantDTO1.setName(tenant.getName());
        tenantDTO1.setTenantId(tenant.getTenantId());

        List<EnvironmentDTO> environments= new ArrayList<>();

        for(TenantEnv tenantEnv: tenant.getTenantEnv()){
            EnvironmentDTO environmentDTO=new EnvironmentDTO();
            environmentDTO.setName(tenantEnv.getName());
            environmentDTO.setDbPassword(tenantEnv.getDbPassword());
            environmentDTO.setDbUrl(tenantEnv.getDbUrl());
            environmentDTO.setDbUserName(tenantEnv.getDbUserName());
            environmentDTO.setDescription(tenantEnv.getDescription());
            environmentDTO.setIsAutoLoad(tenantEnv.getIsAutoLoad());
            environmentDTO.setTenantEnvId(tenantEnv.getTenantEnvId());
            environments.add(environmentDTO);
        }
        tenantDTO1.setEnvironments(environments);
        return  tenantDTO1;
    }

    @PutMapping("/tenants/{tenantId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTenant(@PathVariable(value = "tenantId") int tenantId, @Valid @RequestBody TenantDTO tenantDTO){

        Tenant tenant=tenantDao.getTenantById(tenantId);
        //tenant.setTenantId(tenantDTO.getTenantId());
        //tenant.setName(tenantDTO.getName());
        //Exceptions to be thrown if PUT contains the above two fields
        tenant.setDescription(tenantDTO.getDescription());
        tenant.setIsAutoLoad(tenantDTO.getIsAutoLoad());
        tenant.setIsActive(tenantDTO.getIsActive());
        //time and user details
        tenant.setUpdatedDate(new Date());
        tenant.setUpdatedBy(CodeLoaderConstants.TRANSACTION_USER);
        tenantDao.save(tenant);


        for(EnvironmentDTO environmentDTO:tenantDTO.getEnvironments())
        {
            TenantEnv tenantEnv=tenantEnvironmentDao.getByEnvId(environmentDTO.getTenantEnvId());
            //tenantEnv.setName(environmentDTO.getName());
            //tenantEnv.setTenant(tenant);
            //tenantEnv.setTenantEnvId(environmentDTO.getTenantEnvId());
            //Exceptions to be thrown if PUT contains the above three fields
            tenantEnv.setDescription(environmentDTO.getDescription());
            tenantEnv.setDbUrl(environmentDTO.getDbUrl());
            tenantEnv.setDbPassword(environmentDTO.getDbPassword());
            tenantEnv.setDbUserName(environmentDTO.getDbUserName());
            tenantEnv.setIsAutoLoad(environmentDTO.getIsAutoLoad());
            tenantEnv.setIsActive(environmentDTO.getIsActive());

            //time and user details
            tenantEnv.setUpdatedDate(new Date());
            tenantEnv.setUpdatedBy(CodeLoaderConstants.TRANSACTION_USER);
            tenantEnvironmentDao.save(tenantEnv);
        }

    }

    @PostMapping("/tenants/{tenantId}/environments")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTenantEnvironments(@PathVariable(value = "tenantId") int tenantId,
                                         @Valid @RequestBody TenantDTO tenantDTO){
        //check if tenantId exists
        Tenant tenant=tenantDao.getTenantById(tenantId);
        tenant.setUpdatedDate(new Date());
        tenant.setUpdatedBy(CodeLoaderConstants.TRANSACTION_USER);
        tenant.setCreatedBy(CodeLoaderConstants.TRANSACTION_USER);
        tenantDao.save(tenant);

        for(EnvironmentDTO environmentDTO:tenantDTO.getEnvironments())
        {
            TenantEnv tenantEnv=new TenantEnv();
            tenantEnv.setName(environmentDTO.getName());
            tenantEnv.setDescription(environmentDTO.getDescription());
            tenantEnv.setTenantEnvId(environmentDTO.getTenantEnvId());
            tenantEnv.setDbUrl(environmentDTO.getDbUrl());
            tenantEnv.setDbPassword(environmentDTO.getDbPassword());
            tenantEnv.setDbUserName(environmentDTO.getDbUserName());
            tenantEnv.setIsAutoLoad(environmentDTO.getIsAutoLoad());
            tenantEnv.setIsActive(environmentDTO.getIsActive());
            tenantEnv.setTenant(tenant);
            //time and user details
            tenantEnv.setCreatedDate(new Date());
            tenantEnv.setUpdatedDate(new Date());
            tenantEnv.setUpdatedBy(CodeLoaderConstants.TRANSACTION_USER);
            tenantEnv.setCreatedBy(CodeLoaderConstants.TRANSACTION_USER);
            tenantEnvironmentDao.save(tenantEnv);
        }

    }

    @PutMapping(value="/tenants/{tenantId}/environments")
    public void updateTenantEnvironment(@PathVariable(value = "tenantId") int tenantId,@Valid @RequestBody TenantDTO tenantDTO){
        Tenant tenant=tenantDao.getTenantById(tenantId);
        List<EnvironmentDTO> environmentDTOList=new ArrayList<>(tenantDTO.getEnvironments());

        for(EnvironmentDTO environmentDTO:environmentDTOList){
            TenantEnv tenantEnv=new TenantEnv();
            tenantEnv.setTenantEnvId(environmentDTO.getTenantEnvId());
            tenantEnv.setIsAutoLoad(environmentDTO.getIsAutoLoad());
            tenantEnv.setIsActive(environmentDTO.getIsActive());
            tenantEnv.setDbUrl(environmentDTO.getDbUrl());
            tenantEnv.setDbUserName(environmentDTO.getDbUserName());
            tenantEnv.setDbPassword(environmentDTO.getDbPassword());
            tenantEnv.setDescription(environmentDTO.getDescription());
            tenantEnvironmentDao.update(tenantEnv);
        }

    }

    @GetMapping("/tenants/{tenantId}/environments/{environmentId}")
    public @ResponseBody EnvironmentDTO getTenantEnvironment(@PathVariable(value="tenantId") int tenantId,@PathVariable(value = "environmentId") int tenantEnvId){
        Tenant tenant=tenantDao.getTenantById(tenantId);
        //check if tenantId and environmentId exists
        EnvironmentDTO environmentDTO=new EnvironmentDTO();
        for(TenantEnv tenantEnv: tenant.getTenantEnv()){
            if(tenantEnv.getTenantEnvId()==tenantEnvId){
                environmentDTO.setName(tenantEnv.getName());
                environmentDTO.setDbPassword(tenantEnv.getDbPassword());
                environmentDTO.setDbUrl(tenantEnv.getDbUrl());
                environmentDTO.setDbUserName(tenantEnv.getDbUserName());
                environmentDTO.setDescription(tenantEnv.getDescription());
                environmentDTO.setIsAutoLoad(tenantEnv.getIsAutoLoad());
                environmentDTO.setTenantEnvId(tenantEnv.getTenantEnvId());
                LOGGER.info(environmentDTO.toString());
                break;
            }
        }

        return  environmentDTO;
    }

}
