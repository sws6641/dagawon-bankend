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
@Table(name = "TB_TRN_VR_ACCT_DEPOSIT")
public class TbTrnVrAcctDeposit extends BaseTimeEntity {
    @Id
    @Column(name = "VR_ACCT_DEP_SEQ", nullable = false)
    private Long id;

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
    @Column(name = "DEP_DT", length = 8)
    private String depDt;

    @Column(name = "DEP_AMT", precision = 15)
    private BigDecimal depAmt;
}