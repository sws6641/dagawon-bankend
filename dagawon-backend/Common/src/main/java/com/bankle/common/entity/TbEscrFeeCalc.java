package com.bankle.common.entity;

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
@Table(name = "TB_ESCR_FEE_CALC")
public class TbEscrFeeCalc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false)
    private Long id;

    @Size(max = 2)
    @Column(name = "PRDT_TP_CD", length = 2)
    private String prdtTpCd;

    @Size(max = 2)
    @Column(name = "PRDT_DTL_GB_CD", length = 2)
    private String prdtDtlGbCd;

    @Size(max = 100)
    @Column(name = "SECTION", length = 100)
    private String section;

    @Column(name = "START_AMT", precision = 18)
    private BigDecimal startAmt;

    @Column(name = "END_AMT", precision = 18)
    private BigDecimal endAmt;

    @Column(name = "BASE_AMT", precision = 18)
    private BigDecimal baseAmt;

    @Column(name = "BASE_FEE_AMT", precision = 18)
    private BigDecimal baseFeeAmt;

    @Column(name = "FEE_RATE", precision = 18, scale = 3)
    private BigDecimal feeRate;

    @Size(max = 4000)
    @Column(name = "RMK", length = 4000)
    private String rmk;

    @NotNull
    @Column(name = "CRT_DTM", nullable = false)
    private Instant crtDtm;

    @Size(max = 12)
    @NotNull
    @Column(name = "CRT_MEMB_NO", nullable = false, length = 12)
    private String crtMembNo;

    @NotNull
    @Column(name = "CHG_DTM", nullable = false)
    private Instant chgDtm;

    @Size(max = 12)
    @NotNull
    @Column(name = "CHG_MEMB_NO", nullable = false, length = 12)
    private String chgMembNo;

}