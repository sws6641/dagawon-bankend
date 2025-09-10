package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbCustLoginHist}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbCustLoginHistDto implements Serializable {
    LocalDateTime crtDtm;
    @NotNull
    String crtMembNo;
    Long seq;
    @NotNull
    @Size(max = 12)
    String membNo;
    @NotNull
    @Size(max = 2)
    String membGbCd;
    @NotNull
    @Size(max = 50)
    String pinNo;
    @NotNull
    @Size(max = 500)
    String acsBrw;
    @NotNull
    @Size(max = 500)
    String acsDvce;
    @NotNull
    @Size(max = 100)
    String acsIpAddr;
}