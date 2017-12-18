package com.healthedge.codeloaders.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@SuppressWarnings("PMD")
@Entity
@Table(name = "ZIP_TO_CARRIER_LOCALITY")
@AttributeOverrides({
        @AttributeOverride(name = "lastTransactionDate", column =
        @Column(name = "LAST_TX_DT")),
        @AttributeOverride(name = "lastTransactionUserText", column =
        @Column(name = "LAST_TX_USER_TXT")),
        @AttributeOverride(name = "txCnt", column =
        @Column(name = "TX_CNT")),
        @AttributeOverride(name = "versionStart", column =
        @Column(name = "VERSION_START")),
        @AttributeOverride(name = "action", column =
        @Column(name = "ACTION")),
        @AttributeOverride(name = "versionEnd", column =
        @Column(name = "VERSION_END"))})
public class ZipToCarrierLocality extends BaseEntity{


    @NotNull
    @Column(name = "ZIP_TO_CARR_LOCALITY_ID")
    private Long  zipToCarrierLocalityId;
    @NotNull
    @Column(name = "EFF_START_DT")
    private Date effStartDate;
    @NotNull
    @Column(name = "CARR_NBR")
    private String carrierNbr;
    @NotNull
    @Column(name = "LOCALITY_NBR")
    private String localityNbr;
    @NotNull
    @Column(name = "VER_ID")
    private Date version;
    @NotNull
    @Column(name = "CVC_TYPE_CD")
    private String cvcTypeCd="c";
    @NotNull
    @Column(name = "INST_ACTIVE_CD")
    private String instActiveCd="a";
    @NotNull
    @Column(name = "CONCEPT_FULFILLED_CD")
    private String conceptFulfilledCd="t";
    @NotNull
    @Column(name = "VER_EFF_DT")
    private Date verEffectiveDate;
    @NotNull
    @Column(name = "VER_EXPIRE_DT")
    private Date verExpiryDate;
    @NotNull
    @Column(name = "ENDOR_EFF_DT")
    private Date endorEffectiveDate;
    @NotNull
    @Column(name = "ENDOR_EXPIRE_DT")
    private Date endorExpireDate;
    @NotNull
    @Column(name = "SCHED_ID")
    private Long schedulerId= Long.valueOf("MEDICARE_ID");
    @NotNull
    @Column(name = "ZIP_CD")
    private String zipCode;

    public Long getZipToCarrierLocalityId() {
        return zipToCarrierLocalityId;
    }

    public void setZipToCarrierLocalityId(Long zipToCarrierLocalityId) {
        this.zipToCarrierLocalityId = zipToCarrierLocalityId;
    }

    public Date getEffStartDate() {
        return effStartDate;
    }

    public void setEffStartDate(Date effStartDate) {
        this.effStartDate = effStartDate;
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

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
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

    public Date getVerEffectiveDate() {
        return verEffectiveDate;
    }

    public void setVerEffectiveDate(Date verEffectiveDate) {
        this.verEffectiveDate = verEffectiveDate;
    }

    public Date getVerExpiryDate() {
        return verExpiryDate;
    }

    public void setVerExpiryDate(Date verExpiryDate) {
        this.verExpiryDate = verExpiryDate;
    }

    public Date getEndorEffectiveDate() {
        return endorEffectiveDate;
    }

    public void setEndorEffectiveDate(Date endorEffectiveDate) {
        this.endorEffectiveDate = endorEffectiveDate;
    }

    public Date getEndorExpireDate() {
        return endorExpireDate;
    }

    public void setEndorExpireDate(Date endorExpireDate) {
        this.endorExpireDate = endorExpireDate;
    }

    public Long getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(Long schedulerId) {
        this.schedulerId = schedulerId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}



