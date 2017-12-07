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
@Table(name="CODE_FILE_STATUS")
public class FileStatus implements Serializable {

    public static final String FAILURE="FAILURE";
    public static final String IN_PROGRESS="IN_PROGRESS";
    public static final String PERSISTED ="PERSISTED";

    @Id
    @NotNull
    @Column(name= "SERV_TYPE_CD")
    private String codeType;

    @Column(name= "FILE_NAME")
    private String fileName;

    @Column(name = "STATUS")
    private String status;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
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
}
