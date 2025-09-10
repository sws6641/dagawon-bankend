package com.bankle.common.dto;

import com.bankle.common.entity.TbCustAlramYn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link TbCustAlramYn}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbCustAlramYnDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
    Long alramYnSeq;
    @NotNull
    @Size(max = 50)
    String membNo;
    @NotNull
    @Size(max = 1)
    String alramYn;
    @NotNull
    @Size(max = 2)
    String alramGbCd;
}