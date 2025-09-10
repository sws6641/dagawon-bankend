package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_MESG_TPLT")
public class TbMesgTplt extends BaseTimeEntity {
    @Id
    @Size(max = 6)
    @Column(name = "TPLT_SEQ", nullable = false, length = 6)
    private String tpltSeq;

    @Size(max = 2)
    @Column(name = "TPLT_GB_CD", length = 2)
    private String tpltGbCd;

    @Size(max = 2)
    @Column(name = "TPLT_KND_GB_CD", length = 2)
    private String tpltKndGbCd;

    @Size(max = 100)
    @Column(name = "TPLT_TTL", length = 100)
    private String tpltTtl;

    @Size(max = 5000)
    @Column(name = "TPLT_CNTS", length = 5000)
    private String tpltCnts;

    @Size(max = 2)
    @Column(name = "TPLT_CD", length = 2)
    private String tpltCd;

    @Size(max = 2)
    @Column(name = "ALT_TPLT_CD", length = 2)
    private String altTpltCd;

    @Size(max = 5000)
    @Column(name = "ALT_TPLT_CNTS", length = 5000)
    private String altTpltCnts;

    @Size(max = 50)
    @Column(name = "GID_TELNO", length = 50)
    private String gidTelno;

    @Size(max = 1)
    @Column(name = "USE_YN", length = 1)
    private String useYn;

    @Size(max = 2)
    @Column(name = "BTN_CD", length = 2)
    private String btnCd;

    @Size(max = 100)
    @Column(name = "BTN_NM", length = 100)
    private String btnNm;

    @Size(max = 100)
    @Column(name = "TPLT_URI", length = 100)
    private String tpltUri;
}