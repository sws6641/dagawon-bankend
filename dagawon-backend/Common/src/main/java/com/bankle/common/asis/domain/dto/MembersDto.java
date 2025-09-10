package com.bankle.common.asis.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.asis.domain.entity.Members}
 */
@Value
public class MembersDto implements Serializable {
    LocalDateTime crtDtm;
    LocalDateTime chgDtm;
    @Size(max = 25)
    @NotEmpty
    String membNo;
    @Size(max = 100)
    @NotEmpty
    String pwd;
    String membDsc;
    String membNm;
    String telopCd;
    String hpNo;
    @Size(max = 8)
    String birthDt;
    String sex;
    String ntvFrnrDsc;
    @Email
    String email;
    String entrDt;
    String fnlCnctDt;
    String fcmId;
    String udid;
    String ci;
    String di;
    String dvcKnd;
    String entrStc;
    String escrNotiYn;
    String pushNotiYn;
    String katokNotiYn;
    String mktNotiYn;
}