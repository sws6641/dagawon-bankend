package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;


/**
 * 거래 에스크로 관계자 상세
 */
@Entity
@Table(name = "TET_ESCR_PRTY_D")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractEscrowParty extends BaseTimeEntityByAllDtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESCR_PRTY_D_KEY")
    private Long escrPrtyDKey;

    @Column(name = "PRTY_DSC")
    private String prtyDsc;

    @Column(name = "PRTY_NM")
    private String prtyNm;

    @Column(name = "PRTY_HP_NO")
    private String prtyHpNo;

    @Column(name = "PRTY_BIRTH_DT")
    private String prtyBirthDt;

    @Column(name = "PRTY_SEX")
    private String prtySex;

    @Column(name = "PRTY_EMAIL")
    private String prtyEmail;

    @Column(name = "TR_ASNT_DT")
    private String trAsntDt;

    @Column(name = "TR_ASNT_YN")
    private String trAsntYn;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;

    @Transient
    private String prtyDscValue;

}
