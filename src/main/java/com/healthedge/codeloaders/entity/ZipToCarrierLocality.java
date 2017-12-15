package com.healthedge.codeloaders.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
        @AttributeOverride(name = "codeProcessingHistoryId", column =
        @Column(name = "CODE_PROCESSING_HISTORY_ID"))})
public class ZipToCarrierLocality extends BaseEntity{

    @Id
    @NotNull
    @Column(name = "ZIP_CODE")
    private String zipCode;
}
