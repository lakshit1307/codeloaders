package com.healthedge.codeloaders.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("PMD")
@Entity
@Table(name = "T_SERVICE")
@IdClass(BaseEntity.class)
@AttributeOverrides({
		@AttributeOverride(name = "lastTransactionDate", column =
		@Column(name = "LAST_TX_DT")),
		@AttributeOverride(name = "lastTransactionUserText", column =
		@Column(name = "LAST_TX_USER_TXT")),
		@AttributeOverride(name = "txCnt", column =
		@Column(name = "TX_CNT")),
		@AttributeOverride(name = "effectiveEndDate", column =
		@Column(name = "EFF_END_DT")),
		@AttributeOverride(name = "effectiveStartDate", column =
		@Column(name = "EFF_START_DT")),
		@AttributeOverride(name = "versionStart", column =
		@Column(name = "VERSION_START")),
		@AttributeOverride(name = "action", column =
		@Column(name = "ACTION")),
		@AttributeOverride(name = "versionEnd", column =
		@Column(name = "VERSION_END"))})
public class Service extends BaseEntity {

	@Id
	@NotNull
	@Column(name = "SERV_CD")
	private String serviceCode;

	@Column(name = "SERV_SHORT_DSC")
	private String serviceShortDesciption;

	@Column(name = "SERV_TYPE_CD")
	private String serviceTypeCD;

	@Column(name = "SERV_LONG_DSC")
	private String serviceLongDesciption;

	@Column(name = "ALT_DSC")
	private String serviceAlternateDesciption;

	@Column(name = "STANDARDIZED_SERV_CD")
	private String standardizedServiceCode;

	@Column(name = "WRK_FLOW_CD")
	private String workFlowCode;

	@Column(name = "CODE_PROCESSING_HISTORY_ID")
	private Integer codeProcessingHistoryId;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		super.setCode(serviceCode);
		this.serviceCode = serviceCode;
	}

	public String getServiceTypeCD() {
		return serviceTypeCD;
	}
	public void setServiceTypeCD(String serviceTypeCD) {
		this.serviceTypeCD = serviceTypeCD;
	}

	public String getServiceShortDesciption() {
		return serviceShortDesciption;
	}

	public void setServiceShortDesciption(String serviceShortDesciption) {
		this.serviceShortDesciption = serviceShortDesciption;
	}

	public String getServiceLongDesciption() {
		return serviceLongDesciption;
	}

	public void setServiceLongDesciption(String serviceLongDesciption) {
		this.serviceLongDesciption = serviceLongDesciption;
	}

	public String getServiceAlternateDesciption() {
		return serviceAlternateDesciption;
	}

	public void setServiceAlternateDesciption(String serviceAlternateDesciption) {
		this.serviceAlternateDesciption = serviceAlternateDesciption;
	}

	public String getStandardizedServiceCode() {
		return standardizedServiceCode;
	}

	public void setStandardizedServiceCode(String standardizedServiceCode) {
		this.standardizedServiceCode = standardizedServiceCode;
	}

	public String getworkFlowCode() {
		return workFlowCode;
	}

	public void setworkFlowCode(String wprl) {
		this.workFlowCode = wprl;
	}


	public String getWorkFlowCode() {
		return workFlowCode;
	}

	public void setWorkFlowCode(String workFlowCode) {
		this.workFlowCode = workFlowCode;
	}

	public Integer getCodeProcessingHistoryId() {
		return codeProcessingHistoryId;
	}

	public void setCodeProcessingHistoryId(Integer codeProcessingHistoryId) {
		this.codeProcessingHistoryId = codeProcessingHistoryId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Service service = (Service) o;

		if (serviceCode != null ? !serviceCode.equals(service.serviceCode) : service.serviceCode != null) return false;
		if (serviceLongDesciption != null ? !serviceLongDesciption.equals(service.serviceLongDesciption) : service.serviceLongDesciption != null) return false;
		if (serviceShortDesciption != null ? !serviceShortDesciption.equals(service.serviceShortDesciption) : service.serviceShortDesciption != null) return false;
		return serviceAlternateDesciption != null ? serviceAlternateDesciption.equals(service.serviceAlternateDesciption) : service.serviceAlternateDesciption == null;
	}

	@Override
	public int hashCode() {
		int result = serviceCode != null ? serviceCode.hashCode() : 0;
		result = 31 * result + (serviceLongDesciption != null ? serviceLongDesciption.hashCode() : 0);
		result = 31 * result + (serviceShortDesciption != null ? serviceShortDesciption.hashCode() : 0);
		result = 31 * result + (serviceAlternateDesciption != null ? serviceAlternateDesciption.hashCode() : 0);
		return result;
	}


}
