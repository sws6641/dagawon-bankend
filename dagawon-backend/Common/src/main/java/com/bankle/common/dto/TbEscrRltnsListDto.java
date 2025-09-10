package com.bankle.common.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.bankle.common.entity.TbEscrRltnsList}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbEscrRltnsListDto implements Serializable {
    Long escrRltnsSeq;
    @Size(max = 20) String escrNo;
    @Size(max = 2) String rltnsGbCd;
    @Size(max = 100) String rltnsNm;
    @Size(max = 20) String rltnsCphnNo;
    @Size(max = 8) String rltnsBirthDt;
    @Size(max = 2) String rltnsSexGb;
    @Size(max = 100) String rltnsEmail;
    @Size(max = 3) String rltnsBnkCd;
    @Size(max = 30) String rltnsAcctNo;
    @Size(max = 8) String trAgreeDt;
    @Size(max = 1) String trAgreeYn;
    @Size(max = 32) String kakaoReceiptId;
    Integer kakaoState;
    @Size(max = 14) String kakaoVerifyDt;
    @Size(max = 14) String kakaoExpireDt;
    @Size(max = 14) String kakaoCompleteDt;
    @Size(max = 14) String kakaoViewDt;
    @Size(max = 14) String kakaoRequestDt;
    LocalDateTime crtDtm;
    String crtMembNo;
    LocalDateTime chgDtm;
    String chgMembNo;
}