package com.healthedge.codeloaders.entity;

import java.util.Date;

public class ClientZipToCarrLocEntity extends ClientBaseEntity {

    private Long zipToCarrLocalityId;

    private String carrierNbr;

    private String localityNbr;

    private Long versionId;

    private String cvcTypeCd;

    private String instActiveCd;

    private String conceptFulfilledCd;

    private Date versionEffectiveDate;

    private Date versionExpiryDate;

    private Date endorEffectiveDate;

    private Date endorExpiryDate;

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

    public Date getVersionEffectiveDate() {
        return versionEffectiveDate;
    }

    public void setVersionEffectiveDate(Date versionEffectiveDate) {
        this.versionEffectiveDate = versionEffectiveDate;
    }

    public Date getVersionExpiryDate() {
        return versionExpiryDate;
    }

    public void setVersionExpiryDate(Date versionExpiryDate) {
        this.versionExpiryDate = versionExpiryDate;
    }

    public Date getEndorEffectiveDate() {
        return endorEffectiveDate;
    }

    public void setEndorEffectiveDate(Date endorEffectiveDate) {
        this.endorEffectiveDate = endorEffectiveDate;
    }

    public Date getEndorExpiryDate() {
        return endorExpiryDate;
    }

    public void setEndorExpiryDate(Date endorExpiryDate) {
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
