package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@Table(name = "TET_ESCR_PMNT_D")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractEscrowPmnt extends BaseTimeEntityByAllDtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESCR_PMNT_D_KEY")
    private Long escrPmntDKey;

    @Column(name = "CHRG_DSC")
    private String chrgDsc;

    @Column(name = "PMNT_BNK_CD")
    private String pmntBnkCd;

    @Column(name = "PMNT_ACCT_NO")
    private String pmntAcctNo;

    @Column(name = "PMNT_DT")
    private String pmntDt;

    @Column(name = "PMNT_AMT")
    private Long pmntAmt;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;

    @Column(name = "ESCR_PRTY_D_KEY")
    private Long escrPrtyDKey;
}
