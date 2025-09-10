package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntityByCrtDtm;
import com.bankle.common.entity.base.BaseTimeEntityByCrtDtmAndMembNo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_TRN_TG_LOG")
public class TbTrnTgLog extends BaseTimeEntityByCrtDtmAndMembNo {
    @Id
    @Column(name = "TG_LOG_SEQ", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 2)
    @Column(name = "TG_KND_GB_CD", length = 2)
    private String tgKndGbCd;

    @Size(max = 20)
    @Column(name = "TG_CD", length = 20)
    private String tgCd;

    @Column(name = "TG_SEQ")
    private Long tgSeq;

    @Lob
    @Column(name = "TG_CNTS")
    private String tgCnts;

    @Lob
    @Column(name = "LK_SND_TG")
    private String lkSndTg;

    @Lob
    @Column(name = "LK_RCV_TG")
    private String lkRcvTg;

    @Column(name = "LK_RCV_DTM")
    private Instant lkRcvDtm;

    @Size(max = 2)
    @Column(name = "SND_RCV_RSLT", length = 2)
    private String sndRcvRslt;

    @Lob
    @Column(name = "SND_RCV_RSLT_MSG")
    private String sndRcvRsltMsg;
}