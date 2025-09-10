package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbTrnVrAcctDeposit}
 */
@Value
public class TbTrnVrAcctDepositDto implements Serializable {
    Long id;
    @Size(max = 20)
    String vrAcctNo;
    @Size(max = 3)
    String depBnkCd;
    @Size(max = 20)
    String depAcctNo;
    @Size(max = 100)
    String depNm;
    @Size(max = 8)
    String depDt;
    BigDecimal depAmt;
    LocalDateTime crtDtm;
    @Size(max = 12)
    String crtMembNo;
    LocalDateTime chgDtm;
    @Size(max = 12)
    String chgMembNo;
}