package com.healthedge.codeloaders.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("PMD")
@Entity
@Table(name = "CODE_FILE_STATUS")
public class FileStatus implements Serializable {

	public static final String FAILURE = "FAILURE";
	public static final String IN_PROGRESS = "IN_PROGRESS";
	public static final String PERSISTED = "PERSISTED";

	@NotNull
	@Column(name = "FILE_TYPE")
	private String fileType;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "VERSION")
	private Long version;

	@Column(name = "TX_DATE")
	private Date txDate;

	@Id
	@Column(name = "TX_CNT")
	private Long txCnt;

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getTxDate() {
		return txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	public Long getTxCnt() {
		return txCnt;
	}

	public void setTxCnt(Long txCnt) {
		this.txCnt = txCnt;
	}

}
