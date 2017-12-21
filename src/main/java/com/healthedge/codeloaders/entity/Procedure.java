package com.healthedge.codeloaders.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("PMD")
@Entity
@Table(name = "T_PROCEDURE")
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
public class Procedure extends BaseEntity{

    @Id
    @NotNull
    @Column(name = "PROC_CD")
    private String procedureCode;

    @Column(name = "PROC_TYPE_CD")
    private String procedureType;

    @Column(name = "SHORT_DSC")
    private String procedureDesc;

    @Column(name = "LONG_DSC")
    private String procedureDescLng;

    @Column(name = "ALT_DSC")
    private String procedureAltDsc;

    @Column(name = "STANDARDIZED_PROC_CD")
    private String procedureStd;

    @Column(name = "WRK_FLOW_CD")
    private String procedureWorkFlow;

    public String getProcedureCode() {
        return procedureCode;
    }

    public String getProcedureType() {
        return procedureType;
    }

    public String getProcedureDesc() {
        return procedureDesc;
    }

    public String getProcedureDescLng() {
        return procedureDescLng;
    }

    public String getProcedureAltDsc() {
        return procedureAltDsc;
    }

    public String getProcedureStd() {
        return procedureStd;
    }

    public String getProcedureWorkFlow() {
        return procedureWorkFlow;
    }

    public void setProcedureCode(String procedureCode) {
        super.setCode(procedureCode);
        this.procedureCode = procedureCode;
    }

    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    public void setProcedureDesc(String procedureDesc) {
        this.procedureDesc = procedureDesc;
    }

    public void setProcedureDescLng(String procedureDescLng) {
        this.procedureDescLng = procedureDescLng;
    }

    public void setProcedureAltDsc(String procedureAltDsc) {
        this.procedureAltDsc = procedureAltDsc;
    }

    public void setProcedureStd(String procedureStd) {
        this.procedureStd = procedureStd;
    }

    public void setProcedureWorkFlow(String procedureWorkFlow) {
        this.procedureWorkFlow = procedureWorkFlow;
    }
}
