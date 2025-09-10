package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbTrnRshRsltMaster}
 */
@Value
public class TbTrnRshRsltMasterDto implements Serializable {
    Long id;
    Long escrNo;
    Long rgstrChgRshEpsd;
    @Size(max = 2)
    String rgstrChgGbCd;
    Long rgstrChgInfoCnt;
    @Size(max = 5000)
    String rgstrChgCnfmCnts;
    @Size(max = 8)
    String rgstrChgCnfmDt;
    @Size(max = 4000)
    String rmk;
    @Size(max = 2)
    String abnlEaneCd;
    @Size(max = 5000)
    String abnlEaneRsn;
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
}