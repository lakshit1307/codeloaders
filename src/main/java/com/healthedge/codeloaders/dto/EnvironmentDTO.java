package com.healthedge.codeloaders.dto;

import java.util.Date;

public class EnvironmentDTO {

    private int tenantEnvId;
    private String name;
    private String description;
    private String dbUrl;
    private String dbUserName;
    private String dbPassword;
    private int isAutoLoad;
    private int isActive;


    public int getTenantEnvId() {
        return tenantEnvId;
    }

    public void setTenantEnvId(int tenantEnvId) {
        this.tenantEnvId = tenantEnvId;
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

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
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

}
