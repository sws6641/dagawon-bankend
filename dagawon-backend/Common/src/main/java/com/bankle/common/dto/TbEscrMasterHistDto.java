package com.bankle.common.dto;

import com.bankle.common.entity.TbEscrMasterHist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link TbEscrMasterHist}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbEscrMasterHistDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
    Long histSeq;
    @NotNull
    @Size(max = 20)
    String escrNo;
    @Size(max = 2)
    String prdtTpCd;
    @Size(max = 2)
    String prdtGbCd;
    @Size(max = 2)
    String prdtDtlGbCd;
    @Size(max = 100)
    String trgtAddr;
    @Size(max = 100)
    String trgtAddrDtl;
    @Size(max = 20)
    String rgstrUnqNo;
    @Size(max = 2)
    String escrProgCd;
    @Size(max = 2)
    String escrDtlProgCd;
    @Size(max = 8)
    String cntrDrwupDt;
    @Size(max = 1)
    String feePayrYn;
    BigDecimal feeAmt;
    BigDecimal feeRfndAmt;
    @Size(max = 1)
    String isrnEntrYn;
    @Size(max = 20)
    String pnum;
    BigDecimal isrnFee;
    Long isrnDepSeq;
    @Size(max = 1)
    String isrnFeeDepYn;
    BigDecimal slPrc;
    BigDecimal bndMaxAmt;
    BigDecimal grntAmt;
    BigDecimal rntAmt;
    BigDecimal escrTrgtAmt;
    BigDecimal escrBlncAmt;
    Long escrPmntSeq;
    @Size(max = 8)
    String rtalStDt;
    @Size(max = 8)
    String rtalEndDt;
    @Size(max = 8)
    String rtalDecdDt;
    @Size(max = 8)
    String cntrClsDt;
    @Size(max = 2)
    String cntrClsRsnCd;
    String cntrClsRsnCnts;
    @Size(max = 1000)
    String cnfmDocUrl;
    @Size(max = 1000)
    String prdtDesUrl;
    @Size(max = 1000)
    String gtipUrl;
    Instant escrEndDtm;
    @Size(max = 4000)
    String rmk;
}