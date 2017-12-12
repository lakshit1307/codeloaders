package com.healthedge.codeloaders.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantRequest {

	private Integer tenantId;

	private Integer tenantEnvId;

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getTenantEnvId() {
		return tenantEnvId;
	}

	public void setTenantEnvId(Integer tenantEnvId) {
		this.tenantEnvId = tenantEnvId;
	}

}
