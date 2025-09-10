package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbCommCode}
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TbCommCodeDto implements Serializable {
    TbCommCodeIdDto id;
    @NotNull
    @Size(max = 100)
    String codeNm;
    @NotNull
    @Size(max = 100)
    String grpNm;
    @Size(max = 500)
    String grpDesc;
    @NotNull
    Integer num;
    @Size(max = 1000)
    String etc1;
    @Size(max = 1000)
    String etc2;
    @Size(max = 1000)
    String etc3;
    @NotNull
    @Size(max = 1)
    String useYn;
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