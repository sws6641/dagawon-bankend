package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrPayment}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbEscrPaymentDto implements Serializable {
    Long escrPmntSeq;
    @Size(max = 2)
    String chrgGbCd;
    @Size(max = 2)
    String procCd;
    @Size(max = 3)
    String depBnkCd;
    @Size(max = 20)
    String depAcctNo;
    String depPlanDt;
    LocalDateTime depDtm;
    BigDecimal depAmt;
    @Size(max = 3)
    String payBnkCd;
    @Size(max = 20)
    String payAcctNo;
    String payPlanDt;
    LocalDateTime payDtm;
    BigDecimal payAmt;
    @Size(max = 20)
    String escrNo;
    Long refRltnsSeq;
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
}