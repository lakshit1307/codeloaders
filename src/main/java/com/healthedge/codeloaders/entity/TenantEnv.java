package com.healthedge.codeloaders.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.LastModifiedDate;

@SuppressWarnings("PMD")
@Entity
@Table(name = "M_TENANT_ENV")
public class TenantEnv implements Serializable {

	@Id
	@NotNull
	@Column(name = "TENANT_ENV_ID")
	private int tenantEnvId;

	// @NotNull
	@ManyToOne
	@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID")
	private Tenant tenant;

	@NotNull
	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DB_URL")
	private String dbUrl;

	@Column(name = "DB_USER_NAME")
	private String dbUserName;

	@Column(name = "DB_PASSWORD")
	private String dbPassword;

	@Column(name = "IS_AUTO_LOAD")
	private int isAutoLoad;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate = new Date();

	@Column(name = "UPDATED_BY", nullable = false, updatable = true)
	private String updatedBy;

	@Column(name = "UPDATED_DATE", nullable = false, updatable = true)
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate = new Date();

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

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "TenantEnv [tenantEnvId=" + tenantEnvId + ", name=" + name + ", description=" + description + ", dbUrl="
				+ dbUrl + ", dbUserName=" + dbUserName + ", dbPassword=" + dbPassword + ", isAutoLoad=" + isAutoLoad
				+ ", isActive=" + isActive + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}
	
	

}
