package com.bankle.common.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.bankle.common.entity.TbCustCouponHist}
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TbCustCouponHistDto implements Serializable {
    @Size(max = 20)
    String seq;
    @Size(max = 12)
    String membNo;
    @Size(max = 100)
    String coupNo;
    @Size(max = 100)
    String coupNm;
    @Size(max = 2)
    String coupTpCd;
    BigDecimal disCntVal;
    @Size(max = 8)
    String expireDtm;
    @Size(max = 1)
    String useYn;
    Instant useDtm;
    Instant crtDtm;
    @Size(max = 12)
    String crtMembNo;
    Instant chgDtm;
    @Size(max = 12)
    String chgMembNo;
}