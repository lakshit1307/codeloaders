package com.healthedge.codeloaders.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("PMD")
@Entity
@Table(name = "T_ZIP_TO_CARRIER_LOCALITY")
@IdClass(BaseEntityIdentifier.class)
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
        @Column(name = "VERSION_END")),
        @AttributeOverride(name = "code", column =
        @Column(name = "ZIP_CD"))})
public class ZipToCarrierLocality extends BaseEntity{


    @NotNull
    @Column(name = "CARR_NBR")
    private String carrierNbr;

    @NotNull
    @Column(name = "LOCALITY_NBR")
    private String localityNbr;

    @NotNull
    @Column(name = "CVC_TYPE_CD")
    private String cvcTypeCd="c";

    @NotNull
    @Column(name = "INST_ACTIVE_CD")
    private String instActiveCd="a";

    @NotNull
    @Column(name = "CONCEPT_FULFILLED_CD")
    private String conceptFulfilledCd="t";

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZipToCarrierLocality that = (ZipToCarrierLocality) o;

        if (carrierNbr != null ? !carrierNbr.equals(that.carrierNbr) : that.carrierNbr != null) return false;
        if (localityNbr != null ? !localityNbr.equals(that.localityNbr) : that.localityNbr != null) return false;
        if (cvcTypeCd != null ? !cvcTypeCd.equals(that.cvcTypeCd) : that.cvcTypeCd != null) return false;
        if (instActiveCd != null ? !instActiveCd.equals(that.instActiveCd) : that.instActiveCd != null) return false;
        if (conceptFulfilledCd != null ? !conceptFulfilledCd.equals(that.conceptFulfilledCd) : that.conceptFulfilledCd != null)
            return false;
        return super.getCode() != null ? super.getCode().equals(that.getCode()) : that.getCode() == null;
    }

    @Override
    public int hashCode() {
        int result = carrierNbr != null ? carrierNbr.hashCode() : 0;
        result = 31 * result + (localityNbr != null ? localityNbr.hashCode() : 0);
        result = 31 * result + (cvcTypeCd != null ? cvcTypeCd.hashCode() : 0);
        result = 31 * result + (instActiveCd != null ? instActiveCd.hashCode() : 0);
        result = 31 * result + (conceptFulfilledCd != null ? conceptFulfilledCd.hashCode() : 0);
        result = 31 * result + (super.getCode() != null ? super.getCode().hashCode() : 0);
        return result;
    }
}



