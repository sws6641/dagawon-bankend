package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbTrnTgLog}
 */
@Value
public class TbTrnTgLogDto implements Serializable {
    Long id;
    @Size(max = 20)
    String escrNo;
    @Size(max = 2)
    String tgKndGbCd;
    @Size(max = 20)
    String tgCd;
    Long tgSeq;
    String tgCnts;
    String lkSndTg;
    String lkRcvTg;
    Instant lkRcvDtm;
    @Size(max = 2)
    String sndRcvRslt;
    String sndRcvRsltMsg;
    LocalDateTime crtDtm;
    String crtMembNo;
}