package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_ESCR_FEE")
public class TbEscrFee extends BaseTimeEntity {
    @Id
    @Column(name = "FEE_SEQ", nullable = false)
    private Long id;

    @Column(name = "FR_AMT", precision = 15)
    private BigDecimal frAmt;

    @Column(name = "TO_AMT", precision = 15)
    private BigDecimal toAmt;

    @Column(name = "BASE_FEE", precision = 15)
    private BigDecimal baseFee;

    @Column(name = "FEE_RT", precision = 6, scale = 4)
    private BigDecimal feeRt;
}