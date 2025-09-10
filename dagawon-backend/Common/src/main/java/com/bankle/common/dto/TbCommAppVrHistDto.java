package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbCommAppVrHist}
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TbCommAppVrHistDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    Long vrSeq;
    @Size(max = 100)
    String appVr;
    String content;
}