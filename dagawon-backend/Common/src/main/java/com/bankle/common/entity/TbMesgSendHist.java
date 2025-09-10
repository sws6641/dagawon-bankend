package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntityByCrtDtm;
import com.bankle.common.entity.base.BaseTimeEntityByCrtDtmAndMembNo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TB_MESG_SEND_HIST")
public class TbMesgSendHist extends BaseTimeEntityByCrtDtmAndMembNo {
    @Id
    @Column(name = "SEQ", nullable = false)
    private Long seq;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 20)
    @Column(name = "ACPT_NO", length = 20)
    private String acptNo;

    @Size(max = 6)
    @Column(name = "TPLT_SEQ", length = 6)
    private String tpltSeq;

    @Size(max = 2)
    @Column(name = "TPLT_GB_CD", length = 2)
    private String tpltGbCd;

    @Size(max = 2)
    @Column(name = "TPLT_CNTS_KND_GB_CD", length = 2)
    private String tpltCntsKndGbCd;

    @Size(max = 200)
    @Column(name = "MSG_TITLE", length = 200)
    private String msgTitle;

    @Lob
    @Column(name = "MSG_TRANS_CNTS")
    private String msgTransCnts;

    @Lob
    @Column(name = "ALT_MSG_TRANS_CNTS")
    private String altMsgTransCnts;

    @Size(max = 20)
    @Column(name = "SND_NO", length = 20)
    private String sndNo;

    @Column(name = "SND_DTM")
    private LocalDateTime sndDtm;

    @Size(max = 20)
    @Column(name = "RCV_NO", length = 20)
    private String rcvNo;

    @Size(max = 12)
    @Column(name = "ADDRE_MEMB_NO", length = 12)
    private String addreMembNo;

    @Size(max = 100)
    @Column(name = "ADDRE_NM", length = 100)
    private String addreNm;

    @Size(max = 200)
    @Column(name = "RCV_FCM_ID", length = 200)
    private String rcvFcmId;

    @Size(max = 2)
    @Column(name = "RSLT_CD", length = 2)
    private String rsltCd;
}