package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrFeeCalc}
 */
@Value
public class TbEscrFeeCalcDto implements Serializable {
    Long id;
    @Size(max = 2)
    String prdtTpCd;
    @Size(max = 2)
    String prdtDtlGbCd;
    @Size(max = 100)
    String section;
    BigDecimal startAmt;
    BigDecimal endAmt;
    BigDecimal baseAmt;
    BigDecimal baseFeeAmt;
    BigDecimal feeRate;
    @Size(max = 4000)
    String rmk;
    @NotNull
    Instant crtDtm;
    @NotNull
    @Size(max = 12)
    String crtMembNo;
    @NotNull
    Instant chgDtm;
    @NotNull
    @Size(max = 12)
    String chgMembNo;
}