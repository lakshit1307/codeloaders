package com.healthedge.codeloaders.entity;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class ClientBaseEntity {

    private Date lastTransactionDate;
    private String lastTransactionUserText;
    private Long txCnt;
    private Date effectiveStartDate;
    private Date effectiveEndDate;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
