package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntityByCrtDtmAndMembNo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "TB_TRN_TG_MASTER")
public class TbTrnTgMaster extends BaseTimeEntityByCrtDtmAndMembNo {
    @Id
    @Size(max = 20)
    @Column(name = "TG_CD", nullable = false, length = 20)
    private String tgCd;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 2)
    @Column(name = "TG_DSC", length = 2)
    private String tgDsc;

    @Size(max = 2)
    @Column(name = "SND_RCV_DSC", length = 2)
    private String sndRcvDsc;

    @Size(max = 10)
    @Column(name = "TG_TRNS_MTD", length = 10)
    private String tgTrnsMtd;

    @Size(max = 1000)
    @Column(name = "TG_TRNS_URL", length = 1000)
    private String tgTrnsUrl;

    @Size(max = 5000)
    @Column(name = "TG_DESC", length = 5000)
    private String tgDesc;

    @Column(name = "TG_SEQ")
    private Long tgSeq;

    @Size(max = 2)
    @Column(name = "`TG_ITM_CD`", length = 2)
    private String tgItmCd;

    @Size(max = 100)
    @Column(name = "TG_ITM_NM", length = 100)
    private String tgItmNm;

    @Size(max = 2)
    @Column(name = "ITM_PRPT_CD", length = 2)
    private String itmPrptCd;

    @Column(name = "ITM_LEN", precision = 5)
    private BigDecimal itmLen;

    @Size(max = 1)
    @Column(name = "ESNTL_YN", length = 1)
    private String esntlYn;

    @Size(max = 20)
    @Column(name = "COMM_GRP_CD", length = 20)
    private String commGrpCd;

    @Size(max = 1000)
    @Column(name = "RMK", length = 1000)
    private String rmk;
}