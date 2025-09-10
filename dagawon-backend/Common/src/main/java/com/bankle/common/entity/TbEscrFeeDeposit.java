package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_ESCR_FEE_DEPOSIT")
public class TbEscrFeeDeposit extends BaseTimeEntity {
    @Id
    @Column(name = "ESCR_FEE_DEP_SEQ", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 2)
    @Column(name = "PAY_CLS_GB_CD", length = 2)
    private String payClsGbCd;

    @Size(max = 2)
    @Column(name = "FEE_PAY_GB_CD", length = 2)
    private String feePayGbCd;

    @Size(max = 2)
    @Column(name = "CARD_GB_CD", length = 2)
    private String cardGbCd;

    @Size(max = 20)
    @Column(name = "VR_ACCT_NO", length = 20)
    private String vrAcctNo;

    @Size(max = 3)
    @Column(name = "DEP_BNK_CD", length = 3)
    private String depBnkCd;

    @Size(max = 20)
    @Column(name = "DEP_ACCT_NO", length = 20)
    private String depAcctNo;

    @Size(max = 100)
    @Column(name = "DEP_NM", length = 100)
    private String depNm;

    @Size(max = 8)
    @Column(name = "PAY_DT", length = 8)
    private String payDt;

    @Column(name = "PAY_AMT", precision = 15)
    private BigDecimal payAmt;
}