package com.healthedge.codeloaders.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("PMD")
@Entity
@Table(name = "T_SERVICE")
public class Service implements Serializable {

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

	@Column(name = "EFF_END_DT")
	private Date effectiveEndDate;

	@Column(name = "EFF_START_DT")
	private Date effectiveStartDate;

	@Column(name = "LAST_TX_DT")
	private Date lastTransactionDate;

	@Column(name = "LAST_TX_USER_TXT")
	private String lastTransactionUserText;

	@Column(name = "TX_CNT")
	private Long txCnt;

	@Column(name = "CODE_PROCESSING_HISTORY_ID")
	private Integer codeProcessingHistoryId;

	@Column(name = "VERSION")
	private Date version;

	@Column(name = "ACTION")
	private String action;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
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

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(Date lastTransactionUserDate) {
		this.lastTransactionDate = lastTransactionUserDate;
	}

	public String getLastTransactionUserText() {
		return lastTransactionUserText;
	}

	public void setLastTransactionUserText(String lastTransactionUserText) {
		this.lastTransactionUserText = lastTransactionUserText;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getWorkFlowCode() {
		return workFlowCode;
	}

	public void setWorkFlowCode(String workFlowCode) {
		this.workFlowCode = workFlowCode;
	}

	public Long getTxCnt() {
		return txCnt;
	}

	public void setTxCnt(Long txCnt) {
		this.txCnt = txCnt;
	}

	public Integer getCodeProcessingHistoryId() {
		return codeProcessingHistoryId;
	}

	public void setCodeProcessingHistoryId(Integer codeProcessingHistoryId) {
		this.codeProcessingHistoryId = codeProcessingHistoryId;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
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
