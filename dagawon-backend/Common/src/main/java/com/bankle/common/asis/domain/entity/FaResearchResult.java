package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import lombok.*;

import jakarta.persistence.*;
@Entity
@Table(name = "TEI_FA_RSCH_RSLT_I")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaResearchResult extends BaseTimeEntityByAllDtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FA_RSCH_RSLT_I_KEY")
    private Long faRschRsltIKey;

    @Column(name = "RGST_CHG_RSCH_EPSD")
    private Double rgstChgRschEpsd;

    @Column(name = "RGST_CHG_DSC")
    private String rgstChgDsc;

    @Column(name = "RGST_CHG_BRKDN_RNO")
    private Double rgstChgBrkdnRno;

    @Column(name = "RGST_CHG_CNFM_CNTS")
    private String rgstChgCnfmCnts;

    @Column(name = "RGST_CHG_CNFM_DT")
    private String rgstChgCnfmDt;

    @Column(name = "RMK_FCT")
    private String rmkFct;

    @Column(name = "STRN_EANE_CD")
    private String strnEaneCd;

    @Column(name = "STRN_EANE_RSN")
    private String strnEaneRsn;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;
}
