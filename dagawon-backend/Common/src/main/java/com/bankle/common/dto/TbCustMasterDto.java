package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbCustMaster}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbCustMasterDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
    @Size(max = 12)
    String membNo;
    @NotNull
    @Size(max = 50)
    String membNm;
    @NotNull
    @Size(max = 2)
    String statCd;
    @NotNull
    @Size(max = 8)
    String birthDt;
    @NotNull
    @Size(max = 1)
    String sexGb;
    @NotNull
    @Size(max = 1)
    String ntvFrnrGbCd;
    @NotNull
    @Size(max = 50)
    String pinNo;
    @Size(max = 100)
    String patnNo;
    @NotNull
    @Size(max = 100)
    String email;
    @Size(max = 10)
    String entrDt;
    @Size(max = 250)
    String fcmId;
    @NotNull
    @Size(max = 250)
    String ciKey;
    @NotNull
    @Size(max = 250)
    String diKey;
    @NotNull
    @Size(max = 1)
    String dvceKnd;
    @NotNull
    @Size(max = 150)
    String cphnNo;
    @NotNull
    Integer lognFailCnt;
    @NotNull
    @Size(max = 50)
    String dvceUnqNum;
}