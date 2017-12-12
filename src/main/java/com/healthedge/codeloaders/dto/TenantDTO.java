package com.healthedge.codeloaders.dto;

import java.util.List;

public class TenantDTO {

    private int tenantId;
    private String name;
    private String description;
    private int isAutoLoad;
    private int isActive;
    private List<EnvironmentDTO> environments;

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsAutoLoad() {
        return isAutoLoad;
    }

    public void setIsAutoLoad(int isAutoLoad) {
        this.isAutoLoad = isAutoLoad;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public List<EnvironmentDTO> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<EnvironmentDTO> environments) {
        this.environments = environments;
    }




}
