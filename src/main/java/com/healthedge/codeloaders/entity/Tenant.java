package com.healthedge.codeloaders.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@SuppressWarnings({"PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal"})
@Entity
@Table(name = "M_TENANT")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdDate", "updatedDate"},
        allowGetters = true)
public class Tenant /* extends BaseDate */ implements Serializable {

	@Id
	@NotNull
	// @GeneratedValue(generator = "InvSeq")
	// @SequenceGenerator(name = "InvSeq", sequenceName = "INV_SEQ", allocationSize
	// = 5)
	@Column(name = "TENANT_ID",nullable = false)
	private int tenantId;

	@Column(name = "NAME",nullable = false)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_AUTO_LOAD",nullable = false)
	private int isAutoLoad;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	@OneToMany(mappedBy = "tenant",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	private List<TenantEnv> tenantEnv;

	@Column(name = "CREATED_BY",nullable = false,updatable = false)
	private String createdBy;

	@Column(name = "CREATED_DATE",nullable = false,updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = false)
	private String updatedBy;

	@Column(name = "UPDATED_DATE", nullable = false)
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

    @JsonIgnore
    public List<TenantEnv> getTenantEnv() {
        return tenantEnv;
    }

    public void setTenantEnv(List<TenantEnv> tenantEnv) {
        this.tenantEnv = tenantEnv;
    }

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
