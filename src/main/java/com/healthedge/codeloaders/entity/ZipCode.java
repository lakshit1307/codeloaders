package com.healthedge.codeloaders.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("PMD")
@Entity
@Table(name = "T_ZIP")
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
public class ZipCode extends BaseEntity{

    @Id
    @NotNull
    @Column(name = "ZIP_CD")
    private String zipCode;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        super.setCode(zipCode);
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZipCode zipCode1 = (ZipCode) o;

        return zipCode != null ? zipCode.equals(zipCode1.zipCode) : zipCode1.zipCode == null;
    }

    @Override
    public int hashCode() {
        return zipCode != null ? zipCode.hashCode() : 0;
    }
}
