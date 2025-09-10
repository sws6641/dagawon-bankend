package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.bankle.common.entity.TbTrnTgError}
 */
@Value
public class TbTrnTgErrorDto implements Serializable {
    @Size(max = 2)
    String tgDsc;
    @Size(max = 4)
    String errCd;
    String errMsg;
}