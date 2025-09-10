package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrVrAcct}
 */
@Value
public class TbEscrVrAcctDto implements Serializable {
    Long id;
    @Size(max = 20)
    String escrNo;
    @Size(max = 2)
    String chrgGbCd;
    @Size(max = 20)
    String vrAcctNo;
    @Size(max = 1)
    String useYn;
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
}