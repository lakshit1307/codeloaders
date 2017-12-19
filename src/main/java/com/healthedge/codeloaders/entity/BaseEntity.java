package com.healthedge.codeloaders.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {

	private Date lastTransactionDate;

	private String lastTransactionUserText;

	private Long txCnt;

	private Date versionStart;

	private String action;

	private Date versionEnd;

	private Date effectiveStartDate;

	private Date effectiveEndDate;

	public Date getVersionEnd() {
		return versionEnd;
	}

	public void setVersionEnd(Date versionEnd) {
		this.versionEnd = versionEnd;
	}

	public Date getVersionStart() {
		return versionStart;
	}

	public void setVersionStart(Date version) {
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
