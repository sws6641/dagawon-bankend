package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrFeePayment}
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbEscrFeePaymentDto implements Serializable {
    Long id;
    @Size(max = 20)
    String escrNo;
    @Size(max = 2)
    String payClsGbCd;
    @Size(max = 100)
    String cardNm;
    @Size(max = 20)
    String cardNo;
    BigDecimal payAmt;
    @Size(max = 250)
    String fnlTrKey;
    @Size(max = 250)
    String payKey;
    LocalDateTime reqDtm;
    LocalDateTime aprvDtm;
    @Size(max = 1000)
    String cardSlpUrl;
    @Size(max = 1000)
    String rcptUrl;
    @Size(max = 1)
    String rsltProcYn;
    @Size(max = 2)
    String rsltCd;
    String rsltMsg;
    LocalDateTime crtDtm;
    String crtMembNo;
}