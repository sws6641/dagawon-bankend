package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "TET_FEE_ROM_D")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeeRomDetail extends BaseTimeEntityByAllDtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEE_ROM_D_KEY")
    private Long feeRomDKey;

    @Column(name = "STMT_CNCL_DSC")
    private String stmtCnclDsc;

    @Column(name = "FEE_STMT_DSC")
    private String feeStmtDsc;

    @Column(name = "CRD_DSC")
    private String crdDsc;

    @Column(name = "VR_ACCT_NO")
    private String vrAcctNo;

    @Column(name = "ROM_BNK_CD")
    private String romBnkCd;

    @Column(name = "ROM_ACCT_NO")
    private String romAcctNo;

    @Column(name = "ROMMN_NM")
    private String rommnNm;

    @Column(name = "STMT_AMT")
    private Long stmtAmt;

    @Column(name = "STMT_DT")
    private String stmtDt;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;
}
