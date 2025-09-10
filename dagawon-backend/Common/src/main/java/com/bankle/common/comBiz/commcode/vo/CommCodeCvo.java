package com.bankle.common.comBiz.commcode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class CommCodeCvo {

    /**
     * 조회 요청 {@link com.nicekos.common.ctrl.CommCtrl}
     */
    @Getter
    @Setter
    public static class SearchCommCodeReqCvo {
        @NotEmpty
        @Size(max = 10)
        @Schema(description = "그룹코드", example = "ESTM_STAT")
        String grpCd;
        @Size(max = 10)
        @Schema(description = "코드", example = "10")
        String code;
        @Size(max = 100)
        @Schema(description = "코드명", example = "사전 견적서 발송")
        String codeNm;
        @Size(max = 100)
        @Schema(description = "그룹명", example = "견적서 상태 코드")
        String grpNm;
        @Size(max = 500)
        @Schema(description = "그룹 설명", example = "견적서의 상태코드를 보여준다.")
        String grpDesc;
        @Schema(description = "순번(정렬순서)", example = "2")
        BigDecimal num;
        @Size(max = 1000)
        @Schema(description = "기타코드1", example = "example")
        String etc1;
        @Size(max = 1000)
        @Schema(description = "기타코드2", example = "example")
        String etc2;
        @Size(max = 1000)
        @Schema(description = "기타코드3", example = "example")
        String etc3;
        @Size(max = 1)
        @Schema(description = "사용여부", example = "Y")
        String useYn;
    }
}
