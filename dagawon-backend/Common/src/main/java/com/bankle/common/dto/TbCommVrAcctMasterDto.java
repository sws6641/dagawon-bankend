package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.bankle.common.entity.TbCommVrAcctMaster}
 */
@Value
public class TbCommVrAcctMasterDto implements Serializable {
    Long id;
    @Size(max = 50)
    String vrAcctNo;
    @Size(max = 2)
    String vrAcctGbCd;
    @Size(max = 1)
    String asgnYn;
}