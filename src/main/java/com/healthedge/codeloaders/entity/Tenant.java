package com.healthedge.codeloaders.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.LastModifiedDate;

@SuppressWarnings({"PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal"})
@Entity
@Table(name = "M_TENANT")
public class Tenant /* extends BaseDate */ implements Serializable {

	@Id
	@NotNull
	// @GeneratedValue(generator = "InvSeq")
	// @SequenceGenerator(name = "InvSeq", sequenceName = "INV_SEQ", allocationSize
	// = 5)
	@Column(name = "TENANT_ID")
	private int tenantId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_AUTO_LOAD")
	private int isAutoLoad;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
//	@JoinColumn(name = "TENANT_ID")
	private List<TenantEnv> tenantEnv = new ArrayList<>();

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

	public List<TenantEnv> getTenantEnv() {
		return tenantEnv;
	}

	public void setTenantEnv(List<TenantEnv> tenantEnv) {
		this.tenantEnv = tenantEnv;
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
		return "Tenant [tenantId=" + tenantId + ", name=" + name + ", description=" + description + ", isAutoLoad="
				+ isAutoLoad + ", isActive=" + isActive + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}
	
	

}
