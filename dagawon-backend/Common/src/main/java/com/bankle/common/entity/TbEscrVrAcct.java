package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_ESCR_VR_ACCT")
public class TbEscrVrAcct extends BaseTimeEntity {
    @Id
    @Column(name = "VR_ACCT_ASGN_SEQ", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 2)
    @Column(name = "CHRG_GB_CD", length = 2)
    private String chrgGbCd;

    @Size(max = 20)
    @Column(name = "VR_ACCT_NO", length = 20)
    private String vrAcctNo;

    @Size(max = 1)
    @Column(name = "USE_YN", length = 1)
    private String useYn;
}