package com.healthedge.codeloaders.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
public class BaseEntity implements Serializable{

    private Date lastTransactionDate;

    private String lastTransactionUserText;

    private Long txCnt;

    private Date version;

    private String action;

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
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


}
