package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TB_ESCR_PAYMENT")
public class TbEscrPayment extends BaseTimeEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESCR_PMNT_SEQ", nullable = false)
    private Long escrPmntSeq;

    @Size(max = 2)
    @Column(name = "CHRG_GB_CD", length = 2)
    private String chrgGbCd;

    @Size(max = 2)
    @Column(name = "PROC_CD", length = 2)
    private String procCd;

    @Size(max = 3)
    @Column(name = "DEP_BNK_CD", length = 3)
    private String depBnkCd;

    @Size(max = 20)
    @Column(name = "DEP_ACCT_NO", length = 20)
    private String depAcctNo;

    @Column(name = "DEP_PLAN_DT")
    private String depPlanDt;

    //@Size(max = 8)
    @Column(name = "DEP_DTM")
    private LocalDateTime depDtm;

    @Column(name = "DEP_AMT", precision = 15)
    private BigDecimal depAmt;

    @Size(max = 3)
    @Column(name = "PAY_BNK_CD", length = 3)
    private String payBnkCd;

    @Size(max = 20)
    @Column(name = "PAY_ACCT_NO", length = 20)
    private String payAcctNo;

    @Column(name = "PAY_PLAN_DT")
    private String payPlanDt;

    //@Size(max = 8)
    @Column(name = "PAY_DTM")
    private LocalDateTime payDtm;

    @Column(name = "PAY_AMT", precision = 15)
    private BigDecimal payAmt;

    @Column(name = "ESCR_NO", length = 20)
    @Size(max = 20)
    private String escrNo;

    @Column(name = "REF_RLTNS_SEQ")
    private Long refRltnsSeq;
}