package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;


/**
 * 거래 에스크로 상세
 */
@Entity
@Getter @Setter
@Table(name = "TET_ESCR_D")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractEscrowDetail extends BaseTimeEntityByAllDtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESCR_D_KEY")
    private Long escrDKey;

    @Column(name = "CHRG_DSC")
    private String chrgDsc;

    @Column(name = "ESCR_AMT")
    private Long escrAmt;

    @Column(name = "ROM_PLN_DT")
    private String romPlnDt;

    @Column(name = "RL_ROM_AMT")
    private Long rlRomAmt;

    @Column(name = "ROM_FN_YN")
    private String romFnYn;

    @Column(name = "PMNT_FN_YN")
    private String pmntFnYn;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;
}
