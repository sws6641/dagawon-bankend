package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_ESCR_MASTER_HIST")
public class TbEscrMasterHist extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HIST_SEQ", nullable = false)
    private Long histSeq;

    @Size(max = 20)
    @NotNull
    @Column(name = "ESCR_NO", nullable = false, length = 20)
    private String escrNo;

    @Size(max = 2)
    @Column(name = "PRDT_TP_CD", length = 2)
    private String prdtTpCd;

    @Size(max = 2)
    @Column(name = "PRDT_GB_CD", length = 2)
    private String prdtGbCd;

    @Size(max = 2)
    @Column(name = "PRDT_DTL_GB_CD", length = 2)
    private String prdtDtlGbCd;

    @Size(max = 100)
    @Column(name = "TRGT_ADDR", length = 100)
    private String trgtAddr;

    @Size(max = 100)
    @Column(name = "TRGT_ADDR_DTL", length = 100)
    private String trgtAddrDtl;

    @Size(max = 20)
    @Column(name = "RGSTR_UNQ_NO", length = 20)
    private String rgstrUnqNo;

    @Size(max = 2)
    @Column(name = "ESCR_PROG_CD", length = 2)
    private String escrProgCd;

    @Size(max = 2)
    @Column(name = "ESCR_DTL_PROG_CD", length = 2)
    private String escrDtlProgCd;

    @Size(max = 8)
    @Column(name = "CNTR_DRWUP_DT", length = 8)
    private String cntrDrwupDt;

    @Size(max = 1)
    @Column(name = "FEE_PAYR_YN", length = 1)
    private String feePayrYn;

    @Column(name = "FEE_AMT", precision = 15)
    private BigDecimal feeAmt;

    @Column(name = "FEE_RFND_AMT", precision = 15)
    private BigDecimal feeRfndAmt;

    @Size(max = 1)
    @Column(name = "ISRN_ENTR_YN", length = 1)
    private String isrnEntrYn;

    @Size(max = 20)
    @Column(name = "PNUM", length = 20)
    private String pnum;

    @Column(name = "ISRN_FEE", precision = 15)
    private BigDecimal isrnFee;

    @Column(name = "ISRN_DEP_SEQ")
    private Long isrnDepSeq;

    @Size(max = 1)
    @Column(name = "ISRN_FEE_DEP_YN", length = 1)
    private String isrnFeeDepYn;

    @Column(name = "SL_PRC", precision = 15)
    private BigDecimal slPrc;

    @Column(name = "BND_MAX_AMT", precision = 15)
    private BigDecimal bndMaxAmt;

    @Column(name = "GRNT_AMT", precision = 15)
    private BigDecimal grntAmt;

    @Column(name = "RNT_AMT", precision = 15)
    private BigDecimal rntAmt;

    @Column(name = "ESCR_TRGT_AMT", precision = 15)
    private BigDecimal escrTrgtAmt;

    @Column(name = "ESCR_BLNC_AMT", precision = 15)
    private BigDecimal escrBlncAmt;

    @Column(name = "ESCR_PMNT_SEQ")
    private Long escrPmntSeq;

    @Size(max = 8)
    @Column(name = "RTAL_ST_DT", length = 8)
    private String rtalStDt;

    @Size(max = 8)
    @Column(name = "RTAL_END_DT", length = 8)
    private String rtalEndDt;

    @Size(max = 8)
    @Column(name = "RTAL_DECD_DT", length = 8)
    private String rtalDecdDt;

    @Size(max = 8)
    @Column(name = "CNTR_CLS_DT", length = 8)
    private String cntrClsDt;

    @Size(max = 2)
    @Column(name = "CNTR_CLS_RSN_CD", length = 2)
    private String cntrClsRsnCd;

    @Lob
    @Column(name = "CNTR_CLS_RSN_CNTS")
    private String cntrClsRsnCnts;

    @Size(max = 1000)
    @Column(name = "CNFM_DOC_URL", length = 1000)
    private String cnfmDocUrl;

    @Size(max = 1000)
    @Column(name = "PRDT_DES_URL", length = 1000)
    private String prdtDesUrl;

    @Size(max = 1000)
    @Column(name = "GTIP_URL", length = 1000)
    private String gtipUrl;

    @Column(name = "ESCR_END_DTM")
    private Instant escrEndDtm;

    @Size(max = 4000)
    @Column(name = "RMK", length = 4000)
    private String rmk;

}