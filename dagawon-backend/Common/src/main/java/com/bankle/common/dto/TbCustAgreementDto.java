package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbCustAgreement}
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TbCustAgreementDto implements Serializable {
    Long id;
    @NotNull
    @Size(max = 8)
    String reformDt;
    @NotNull
    @Size(max = 1)
    String defaultYn;
    @NotNull
    @Size(max = 2)
    String agreeCd;
    @NotNull
    @Size(max = 2)
    String agreeDtlCd;
    @NotNull
    @Size(max = 2)
    String formCd;
    String agreeText;
    @Size(max = 1000)
    String agreeUrl;
    @Size(max = 1000)
    String agreeFile;
    @NotNull
    Long sort;
    @NotNull
    @Size(max = 1)
    String delYn;
    @NotNull
    LocalDateTime crtDtm;
    @NotNull
    @Size(max = 12)
    String crtMembNo;
    @NotNull
    LocalDateTime chgDtm;
    @NotNull
    @Size(max = 12)
    String chgMembNo;
}