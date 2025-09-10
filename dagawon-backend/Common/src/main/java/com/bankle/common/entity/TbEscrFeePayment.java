package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntityByCrtDtmAndMembNo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TB_ESCR_FEE_PAYMENT")
public class TbEscrFeePayment extends BaseTimeEntityByCrtDtmAndMembNo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEE_PAY_SEQ", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 2)
    @Column(name = "PAY_CLS_GB_CD", length = 2)
    private String payClsGbCd;

    @Size(max = 100)
    @Column(name = "CARD_NM", length = 100)
    private String cardNm;

    @Size(max = 20)
    @Column(name = "CARD_NO", length = 20)
    private String cardNo;

    @Column(name = "PAY_AMT", precision = 15)
    private BigDecimal payAmt;

    @Size(max = 250)
    @Column(name = "FNL_TR_KEY", length = 250)
    private String fnlTrKey;

    @Size(max = 250)
    @Column(name = "PAY_KEY", length = 250)
    private String payKey;

    @Column(name = "REQ_DTM")
    private LocalDateTime reqDtm;

    @Column(name = "APRV_DTM")
    private LocalDateTime aprvDtm;

    @Size(max = 1000)
    @Column(name = "CARD_SLP_URL", length = 1000)
    private String cardSlpUrl;

    @Size(max = 1000)
    @Column(name = "RCPT_URL", length = 1000)
    private String rcptUrl;

    @Size(max = 1)
    @Column(name = "RSLT_PROC_YN", length = 1)
    private String rsltProcYn;

    @Size(max = 2)
    @Column(name = "RSLT_CD", length = 2)
    private String rsltCd;

    @Lob
    @Column(name = "RSLT_MSG")
    private String rsltMsg;
}