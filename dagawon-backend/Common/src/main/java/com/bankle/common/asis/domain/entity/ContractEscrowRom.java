package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import lombok.*;

import jakarta.persistence.*;
/**
 * 거래에스크로 입금 상세
 */
@Getter @Setter
@Entity
@Table(name = "TET_ESCR_ROM_D")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractEscrowRom extends BaseTimeEntityByAllDtm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESCR_ROM_D_KEY")
    private Long escrRomDKey;

    @Column(name = "CHRG_DSC")
    private String chrgDsc;

    @Column(name = "ROM_BNK_CD")
    private String romBnkCd;

    @Column(name = "ROM_ACCT_NO")
    private String romAcctNo;

    @Column(name = "ROMMN_NM")
    private String rommnNm;

    @Column(name = "ROM_DT")
    private String romDt;

    @Column(name = "ROM_AMT")
    private Long romAmt;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;

//    @Column(name = "VR_ACCT_ROM_I_KEY")
//    private String vrAcctRomIKey;
}
