package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbMesgSendHist}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TbMesgSendHistDto implements Serializable {
    Long seq;
    @Size(max = 20)
    String escrNo;
    @Size(max = 20)
    String acptNo;
    @Size(max = 6)
    String tpltSeq;
    @Size(max = 2)
    String tpltGbCd;
    @Size(max = 2)
    String tpltCntsKndGbCd;
    @Size(max = 100)
    String msgTitle;
    String msgTransCnts;
    String altMsgTransCnts;
    @Size(max = 20)
    String sndNo;
    LocalDateTime sndDtm;
    @Size(max = 20)
    String rcvNo;
    @Size(max = 12)
    String addreMembNo;
    @Size(max = 100)
    String addreNm;
    @Size(max = 200)
    String rcvFcmId;
    @Size(max = 2)
    String rsltCd;
    LocalDateTime crtDtm;
    String crtMembNo;
}