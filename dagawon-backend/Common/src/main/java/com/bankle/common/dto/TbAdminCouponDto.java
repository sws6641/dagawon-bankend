package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.bankle.common.entity.TbAdminCoupon}
 */
@Value
public class TbAdminCouponDto implements Serializable {
    @Size(max = 100)
    String coupNo;
    @NotNull
    @Size(max = 100)
    String coupNm;
    @Size(max = 2)
    String coupTpCd;
    BigDecimal disCntVal;
    @NotNull
    @Size(max = 8)
    String coupInputDt;
    @NotNull
    @Size(max = 8)
    String coupEdDt;
    @NotNull
    @Size(max = 1)
    String ableYn;
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