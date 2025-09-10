package com.bankle.common.dto;

import com.bankle.common.entity.TbCustAgreementHistIdDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbCustAgreementHist}
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbCustAgreementHistDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
    TbCustAgreementHistIdDto id;
    @NotNull
    @Size(max = 1)
    String defaultYn;
    @NotNull
    @Size(max = 1)
    String agreeYn;
    @NotNull
    @Size(max = 8)
    String agreeDt;
}