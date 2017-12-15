package com.healthedge.codeloaders.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("PMD")
@Entity
@Table(name = "ZIP")
@AttributeOverrides({
        @AttributeOverride(name = "lastTransactionDate", column =
        @Column(name = "LAST_TX_DT")),
        @AttributeOverride(name = "lastTransactionUserText", column =
        @Column(name = "LAST_TX_USER_TXT")),
        @AttributeOverride(name = "txCnt", column =
        @Column(name = "TX_CNT")),
        @AttributeOverride(name = "version", column =
        @Column(name = "VERSION")),
        @AttributeOverride(name = "action", column =
        @Column(name = "ACTION"))})
public class ZipCode extends BaseEntity{

    @Id
    @NotNull
    @Column(name = "ZIP_CODE")
    private String zipCode;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
