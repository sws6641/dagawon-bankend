package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrFee}
 */
@Value
public class TbEscrFeeDto implements Serializable {
    Long id;
    BigDecimal frAmt;
    BigDecimal toAmt;
    BigDecimal baseFee;
    BigDecimal feeRt;
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
}