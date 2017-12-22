package com.healthedge.codeloaders.entity;

public class ClientZipToCarrLocEntity extends ClientBaseEntity {

    private Long zipToCarrLocalityId;

    private String carrierNbr;

    private String localityNbr;

    private Long versionId;

    private String cvcTypeCd;

    private String instActiveCd;

    private String conceptFulfilledCd;

    private Long versionEffectiveDate;

    private Long versionExpiryDate;

    private Long endorEffectiveDate;

    private Long endorExpiryDate;

    private Long scheduleId;

    private String zipCode;

    public Long getZipToCarrLocalityId() {
        return zipToCarrLocalityId;
    }

    public void setZipToCarrLocalityId(Long zipToCarrLocalityId) {
        this.zipToCarrLocalityId = zipToCarrLocalityId;
    }

    public String getCarrierNbr() {
        return carrierNbr;
    }

    public void setCarrierNbr(String carrierNbr) {
        this.carrierNbr = carrierNbr;
    }

    public String getLocalityNbr() {
        return localityNbr;
    }

    public void setLocalityNbr(String localityNbr) {
        this.localityNbr = localityNbr;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public String getCvcTypeCd() {
        return cvcTypeCd;
    }

    public void setCvcTypeCd(String cvcTypeCd) {
        this.cvcTypeCd = cvcTypeCd;
    }

    public String getInstActiveCd() {
        return instActiveCd;
    }

    public void setInstActiveCd(String instActiveCd) {
        this.instActiveCd = instActiveCd;
    }

    public String getConceptFulfilledCd() {
        return conceptFulfilledCd;
    }

    public void setConceptFulfilledCd(String conceptFulfilledCd) {
        this.conceptFulfilledCd = conceptFulfilledCd;
    }

    public Long getVersionEffectiveDate() {
        return versionEffectiveDate;
    }

    public void setVersionEffectiveDate(Long versionEffectiveDate) {
        this.versionEffectiveDate = versionEffectiveDate;
    }

    public Long getVersionExpiryDate() {
        return versionExpiryDate;
    }

    public void setVersionExpiryDate(Long versionExpiryDate) {
        this.versionExpiryDate = versionExpiryDate;
    }

    public Long getEndorEffectiveDate() {
        return endorEffectiveDate;
    }

    public void setEndorEffectiveDate(Long endorEffectiveDate) {
        this.endorEffectiveDate = endorEffectiveDate;
    }

    public Long getEndorExpiryDate() {
        return endorExpiryDate;
    }

    public void setEndorExpiryDate(Long endorExpiryDate) {
        this.endorExpiryDate = endorExpiryDate;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
