package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrFeeDeposit}
 */
@Value
public class TbEscrFeeDepositDto implements Serializable {
    Long id;
    @Size(max = 20)
    String escrNo;
    @Size(max = 2)
    String payClsGbCd;
    @Size(max = 2)
    String feePayGbCd;
    @Size(max = 2)
    String cardGbCd;
    @Size(max = 20)
    String vrAcctNo;
    @Size(max = 3)
    String depBnkCd;
    @Size(max = 20)
    String depAcctNo;
    @Size(max = 100)
    String depNm;
    @Size(max = 8)
    String payDt;
    BigDecimal payAmt;
    LocalDateTime crtDtm;
    @Size(max = 12)
    String crtMembNo;
    LocalDateTime chgDtm;
    @Size(max = 12)
    String chgMembNo;
}