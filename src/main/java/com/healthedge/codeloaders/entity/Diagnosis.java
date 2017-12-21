package com.healthedge.codeloaders.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@SuppressWarnings("PMD")
@Entity
@Table(name="T_DIAGNOSIS")
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
public class Diagnosis extends BaseEntity{

    @Id
    @NotNull
    @Column(name = "DIAG_CD")
    private String diagnosisCode;

    @Column(name = "DIAG_TYPE_CD")
    private String diagnosisTypeCode;

    @Column(name = "DIAG_SHORT_DSC")
    private String diagnosisShortDescription;

    @Column(name = "DIAG_LONG_DSC")
    private String diagnosisLongDescription;

    @Column(name = "ALT_DSC")
    private String alternateDescription;

    @Column(name = "DIAG_CAT_DSC")
    private String diagnosisCategoryDescription;

    @Column(name = "STANDARDIZED_DIAG_CD")
    private String standardizedDiagnosisCode;

    @Column(name = "WRK_FLOW_CD")
    private String workFlowCode;

    @Column(name = "CAT_NM")
    private String catNM;

    @Column(name = "LEVEL2_DIAG_CAT_CAT_NM")
    private String level2DiagCatCatNM;

    @Column(name = "LEVEL3_DIAG_CAT_CAT_NM")
    private String level3DiagCatCatNM;

    @Column(name = "LEVEL4_DIAG_CAT_CAT_NM")
    private String level4DiagCatCatNM;

    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        super.setCode(diagnosisCode);
        this.diagnosisCode = diagnosisCode;
    }

    public String getDiagnosisTypeCode() {
        return diagnosisTypeCode;
    }

    public void setDiagnosisTypeCode(String diagnosisTypeCode) {
        this.diagnosisTypeCode = diagnosisTypeCode;
    }

    public String getDiagnosisShortDescription() {
        return diagnosisShortDescription;
    }

    public void setDiagnosisShortDescription(String diagnosisShortDescription) {
        this.diagnosisShortDescription = diagnosisShortDescription;
    }

    public String getDiagnosisLongDescription() {
        return diagnosisLongDescription;
    }

    public void setDiagnosisLongDescription(String diagnosisLongDescription) {
        this.diagnosisLongDescription = diagnosisLongDescription;
    }

    public String getAlternateDescription() {
        return alternateDescription;
    }

    public void setAlternateDescription(String alternateDescription) {
        this.alternateDescription = alternateDescription;
    }

    public String getDiagnosisCategoryDescription() {
        return diagnosisCategoryDescription;
    }

    public void setDiagnosisCategoryDescription(String diagnosisCategoryDescription) {
        this.diagnosisCategoryDescription = diagnosisCategoryDescription;
    }

    public String getStandardizedDiagnosisCode() {
        return standardizedDiagnosisCode;
    }

    public void setStandardizedDiagnosisCode(String standardizedDiagnosisCode) {
        this.standardizedDiagnosisCode = standardizedDiagnosisCode;
    }

    public String getWorkFlowCode() {
        return workFlowCode;
    }

    public void setWorkFlowCode(String workFlowCode) {
        this.workFlowCode = workFlowCode;
    }

}
