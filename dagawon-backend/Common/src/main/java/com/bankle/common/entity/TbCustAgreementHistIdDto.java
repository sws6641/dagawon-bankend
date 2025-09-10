package com.bankle.common.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link TbCustAgreementHistId}
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbCustAgreementHistIdDto implements Serializable {
    @NotNull
    @Size(max = 12)
    String membNo;
    @NotNull
    @Size(max = 8)
    String reformDt;
    @NotNull
    @Size(max = 2)
    String agreeCd;
    @NotNull
    @Size(max = 2)
    String agreeDtlCd;
}