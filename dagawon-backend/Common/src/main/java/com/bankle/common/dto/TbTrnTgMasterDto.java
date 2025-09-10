package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbTrnTgMaster}
 */
@Value
public class TbTrnTgMasterDto implements Serializable {
    @Size(max = 20)
    String tgCd;
    @Size(max = 20)
    String escrNo;
    @Size(max = 2)
    String tgDsc;
    @Size(max = 2)
    String sndRcvDsc;
    @Size(max = 10)
    String tgTrnsMtd;
    @Size(max = 1000)
    String tgTrnsUrl;
    @Size(max = 5000)
    String tgDesc;
    Long tgSeq;
    @Size(max = 2)
    String tgItmCd;
    @Size(max = 100)
    String tgItmNm;
    @Size(max = 2)
    String itmPrptCd;
    BigDecimal itmLen;
    @Size(max = 1)
    String esntlYn;
    @Size(max = 20)
    String commGrpCd;
    @Size(max = 1000)
    String rmk;
    LocalDateTime crtDtm;
    String crtMembNo;
}