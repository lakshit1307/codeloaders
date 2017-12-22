package com.healthedge.codeloaders.entity;

public class ClientDiagnosisEntity extends ClientBaseEntity{

    private String diagnosisCode;

    private String diagnosisTypeCode;

    private String diagnosisShortDescription;

    private String diagnosisLongDescription;

    private String alternateDescription;

    private String diagnosisCategoryDescription;

    private String standardizedDiagnosisCode;

    private String workFlowCode;

    private String catNM;

    private String level2DiagCatCatNM;

    private String level3DiagCatCatNM;

    private String level4DiagCatCatNM;

    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    public String getDiagnosisTypeCode() {
        return diagnosisTypeCode;
    }

    public void setDiagnosisTypeCode(String diagnosisTypeCode) {
        this.diagnosisTypeCode = diagnosisTypeCode;
    }

    public String getDiagnosisLongDescription() {
        return diagnosisLongDescription;
    }

    public void setDiagnosisLongDescription(String diagnosisLongDescription) {
        this.diagnosisLongDescription = diagnosisLongDescription;
    }

    public String getDiagnosisShortDescription() {
        return diagnosisShortDescription;
    }

    public void setDiagnosisShortDescription(String diagnosisShortDescription) {
        this.diagnosisShortDescription = diagnosisShortDescription;
    }

    public String getStandardizedDiagnosisCode() {
        return standardizedDiagnosisCode;
    }

    public void setStandardizedDiagnosisCode(String standardizedDiagnosisCode) {
        this.standardizedDiagnosisCode = standardizedDiagnosisCode;
    }

    public String getAlternateDescription() {
        return alternateDescription;
    }

    public void setAlternateDescription(String alternateDescription) {
        this.alternateDescription = alternateDescription;
    }

    public String getWorkFlowCode() {
        return workFlowCode;
    }

    public void setWorkFlowCode(String workFlowCode) {
        this.workFlowCode = workFlowCode;
    }

    public String getDiagnosisCategoryDescription() {
        return diagnosisCategoryDescription;
    }

    public void setDiagnosisCategoryDescription(String diagnosisCategoryDescription) {
        this.diagnosisCategoryDescription = diagnosisCategoryDescription;
    }

    public String getCatNM() {
        return catNM;
    }

    public void setCatNM(String catNM) {
        this.catNM = catNM;
    }

    public String getLevel2DiagCatCatNM() {
        return level2DiagCatCatNM;
    }

    public void setLevel2DiagCatCatNM(String level2DiagCatCatNM) {
        this.level2DiagCatCatNM = level2DiagCatCatNM;
    }



    public void setLevel3DiagCatCatNM(String level3DiagCatCatNM) {
        this.level3DiagCatCatNM = level3DiagCatCatNM;
    }

    public void setLevel4DiagCatCatNM(String level4DiagCatCatNM) {
        this.level4DiagCatCatNM = level4DiagCatCatNM;
    }
}
