package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbRgstrCaseInfoHist}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbRgstrCaseInfoHistDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
    Long caseNoSeq;
    @Size(max = 20)
    String escrNo;
    Long rgstrCaseNo;
    @Size(max = 8)
    String srchDt;
    @Size(max = 8)
    String acptDt;
    @Size(max = 20)
    String acptNo;
    @Size(max = 100)
    String cptRego;
    @Size(max = 100)
    String regoDept;
    @Size(max = 100)
    String lotnumAddr;
    @Size(max = 5000)
    String rgstrPrps;
    @Size(max = 100)
    String procStat;
    @Size(max = 1)
    String chgYn;
}