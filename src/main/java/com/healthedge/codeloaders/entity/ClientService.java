package com.healthedge.codeloaders.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("PMD")
@Entity
@Table(name = "SERVICE")
@IdClass(ClientBaseEntity.class)
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
		@AttributeOverride(name = "code", column =
		@Column(name = "SERV_CD"))})
public class ClientService extends ClientBaseEntity {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

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

	public ClientService () {
		LOGGER.info("ClientService class initialized");
	}

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(final String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public String getServiceShortDesciption() {
		return serviceShortDesciption;
	}

	public void setServiceShortDesciption(final String serviceShortDesciption) {
		this.serviceShortDesciption = serviceShortDesciption;
	}

	public String getServiceLongDesciption() {
		return serviceLongDesciption;
	}

	public void setServiceLongDesciption(final String serviceLongDesciption) {
		this.serviceLongDesciption = serviceLongDesciption;
	}

	public String getServiceAlternateDesciption() {
		return serviceAlternateDesciption;
	}

	public void setServiceAlternateDesciption(final String serviceAlternateDesciption) {
		this.serviceAlternateDesciption = serviceAlternateDesciption;
	}

	public String getStandardizedServiceCode() {
		return standardizedServiceCode;
	}

	public void setStandardizedServiceCode(final String standardizedServiceCode) {
		this.standardizedServiceCode = standardizedServiceCode;
	}

	public String getWorkFlowCode() {
		return workFlowCode;
	}

	public void setWorkFlowCode(final String workFlowCode) {
		this.workFlowCode = workFlowCode;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(final Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(final Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(final Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	public String getLastTransactionUserText() {
		return lastTransactionUserText;
	}

	public void setLastTransactionUserText(final String lastTransactionUserText) {
		this.lastTransactionUserText = lastTransactionUserText;
	}

	public Long getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(final Long transactionCount) {
		this.transactionCount = transactionCount;
	}

}
