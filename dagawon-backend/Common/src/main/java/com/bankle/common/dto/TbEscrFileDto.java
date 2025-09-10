package com.bankle.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrFile}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbEscrFileDto implements Serializable {
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
    Long seq;
    @Size(max = 1000)
    String grpNo;
    @Size(max = 11)
    String wkCd;
    @Size(max = 2)
    String fileCd;
    @Size(max = 100)
    String fileNm;
    @Size(max = 100)
    String fileOrgnNm;
    @Size(max = 5)
    String fileExt;
    @Size(max = 1000)
    String filePth;
    BigDecimal fileSize;
    @Size(max = 12)
    String membNo;
    @Size(max = 20)
    String escrNo;
    @Size(max = 4000)
    String rmk;
    @NotNull
    @Size(max = 1)
    String delYn;
}