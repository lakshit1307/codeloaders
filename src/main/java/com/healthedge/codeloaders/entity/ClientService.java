package com.healthedge.codeloaders.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SERVICES")
public class ClientService {

	@Id
	@NotNull
	// @GeneratedValue(generator = "InvSeq")
	// @SequenceGenerator(name = "InvSeq", sequenceName = "INV_SEQ", allocationSize
	// = 5)
	@Column(name = "SERV_CD")
	private String serviceCode;

	@Column(name = "SERV_TYPE_CD")
	private String serviceTypeCode;

	@Column(name = "SERV_SHORT_DSC")
	private String serviceShortDesciption;

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
	private Long transactionCount;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
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

	public String getWorkFlowCode() {
		return workFlowCode;
	}

	public void setWorkFlowCode(String workFlowCode) {
		this.workFlowCode = workFlowCode;
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

	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	public String getLastTransactionUserText() {
		return lastTransactionUserText;
	}

	public void setLastTransactionUserText(String lastTransactionUserText) {
		this.lastTransactionUserText = lastTransactionUserText;
	}

	public Long getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(Long transactionCount) {
		this.transactionCount = transactionCount;
	}

}
