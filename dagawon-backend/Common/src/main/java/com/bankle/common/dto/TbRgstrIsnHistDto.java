package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbRgstrIsnHist}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbRgstrIsnHistDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    Long rgstrIsnSeq;
    @NotNull
    @Size(max = 20)
    String rgstrUnqNo;
    @NotNull
    LocalDateTime readDtm;
    @NotNull
    Integer issuIdNum;
    String content;
}