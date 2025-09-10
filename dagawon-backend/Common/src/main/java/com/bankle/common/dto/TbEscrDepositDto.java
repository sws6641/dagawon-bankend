package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrDeposit}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbEscrDepositDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
    Long id;
    @Size(max = 20)
    String escrNo;
    @Size(max = 2)
    String chrgGbCd;
    @Size(max = 20)
    String vrAcctNo;
    @Size(max = 3)
    String depBnkCd;
    @Size(max = 20)
    String depAcctNo;
    @Size(max = 100)
    String depNm;
    LocalDateTime depDtm;
    BigDecimal depAmt;
}