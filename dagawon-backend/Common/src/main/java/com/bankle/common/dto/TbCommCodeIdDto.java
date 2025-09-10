package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.bankle.common.entity.TbCommCodeId}
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TbCommCodeIdDto implements Serializable {
    @NotNull
    @Size(max = 20)
    String grpCd;
    @NotNull
    @Size(max = 10)
    String code;
}