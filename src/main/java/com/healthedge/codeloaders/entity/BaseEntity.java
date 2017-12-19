package com.healthedge.codeloaders.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {

	private Date lastTransactionDate;

	private String lastTransactionUserText;

	private Long txCnt;

	private Long versionStart;

	private String action;

	private Long versionEnd;

	private Date effectiveStartDate;

	private Date effectiveEndDate;

	@Transient
	private String code;

	@Transient
	public String getCode() {
		return code;
	}

	@Transient
	public void setCode(String code) {
		this.code = code;
	}

	public Long getVersionEnd() {
		return versionEnd;
	}

	public void setVersionEnd(Long versionEnd) {
		this.versionEnd = versionEnd;
	}

	public Long getVersionStart() {
		return versionStart;
	}

	public void setVersionStart(Long version) {
		this.versionStart = version;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public Long getTxCnt() {
		return txCnt;
	}

	public void setTxCnt(Long txCnt) {
		this.txCnt = txCnt;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

}
