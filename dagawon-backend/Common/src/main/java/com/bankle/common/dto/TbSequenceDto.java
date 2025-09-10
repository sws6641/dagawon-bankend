package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.bankle.common.entity.TbSequence}
 */
@Value
public class TbSequenceDto implements Serializable {
    LocalDate id;
    @NotNull
    @Size(max = 100)
    String seqName;
    @NotNull
    Integer seqNumber;
}