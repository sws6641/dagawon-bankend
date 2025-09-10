package com.bankle.common.dto;

import com.bankle.common.entity.TbMesgTplt;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link TbMesgTplt}
 */
@Value
public class TbMesgTpltDto implements Serializable {
    @Size(max=6)
    String tpltSeq;
    @Size(max = 2)
    String tpltGbCd;
    @Size(max = 2)
    String tpltKndGbCd;
    @Size(max = 100)
    String tpltTtl;
    @Size(max = 5000)
    String tpltCnts;
    @Size(max = 2)
    String tpltCd;
    @Size(max = 2)
    String altTpltCd;
    @Size(max = 5000)
    String altTpltCnts;
    @Size(max = 50)
    String gidTelno;
    @Size(max = 1)
    String useYn;
    @Size(max = 2)
    String btnCd;
    @Size(max = 100)
    String btnNm;
    @Size(max = 100)
    String tpltUri;
    LocalDateTime crtDtm;
    @Size(max = 12)
    String crtMembNo;
    LocalDateTime chgDtm;
    @Size(max = 12)
    String chgMembNo;
}