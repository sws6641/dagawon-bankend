package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbAdminCust}
 */
@Value
public class TbAdminCustDto implements Serializable {
    @Size(max = 100)
    String lognId;
    @Size(max = 100)
    String pwd;
    @Size(max = 2)
    String mngrGbCd;
    @Size(max = 100)
    String mngrNm;
    BigDecimal lognFailCnt;
    @Size(max = 50)
    String cphnNo;
    LocalDateTime crtDtm;
    @Size(max = 12)
    String crtMembNo;
    LocalDateTime chgDtm;
    @Size(max = 12)
    String chgMembNo;
}