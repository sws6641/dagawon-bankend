package com.bankle.common.comBiz.coocon.vo;

import com.bankle.common.util.httpapi.vo.BaseResponseVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

public class CooconSvo {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcctComm {
        @Schema(description = "인증키\n" +
                "- 이용기업 인증키값(이용기업 별로 발급됨)", example = "")
        @Size(max = 20)
        @JsonProperty("SECR_KEY")
        String secrKey;
        @Schema(description = "API명 " +
                "- ACCTNM_RCMS_WAPI", example = "ACCTNM_RCMS_WAPI")
        @Size(max = 16)
        @JsonProperty("KEY")
        String key;
        @Schema(description = "캐릭터셋" +
                "- 캐릭터셋 선택\n" +
                "- 예)\"UTF-8\" 또는 \"EUC_KR\" 등\n" +
                "- 미입력시 EUC_KR로 처리됨", example = "UTF-8", nullable = true)
        @JsonProperty("CHAR_SET")
        String charSet;
        @Schema(description = "개발입력부", example = "")
        @JsonProperty("REQ_DATA")
        List<AcctReqData> reqData;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcctReqData {
        @Schema(description = "은행 코드", example = "088")
        @Size(max = 3)
        @JsonProperty("BANK_CD")
        String bankCd;
        @Schema(description = "조회 계좌 번호", example = "110085035301")
        @Size(max = 20)
        @JsonProperty("SEARCH_ACCT_NO")
        String searchAcctNo;
        @Schema(description = "사업자 번호\n" +
                "1. 예금주 실명조회 : 사업자번호 필드 필수 \n" +
                "- 사업자 조회 : 사업자번호 10자리\n" +
                "- 개인조회 : 주민등록상 생년월일(YYYYMMDD) 6자리\n" +
                "2. 예금주 성명 조회 : 미입력해야함.", example = "650604", nullable = true)
        @JsonProperty("ACNM_NO")
        String acnmNo;
        @Schema(description = "거래 일련 번호\n" +
                "- 일별로 중복되면 안됨\n" +
                " 중계 사용시 맨 앞자리 1 자리는\n" +
                " \"0\"으로 고정하여 세팅, 이후 나머지 6자리를 사용", example = "0182739")
        @Size(max = 7)
        @JsonProperty("TRSC_SEQ_NO")
        String trscSeqNo;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcctResData extends BaseResponseVo {
        @Schema(description = "처리 결과 코드", example = "0000")
        @Size(max = 8)
        @JsonProperty("RSLT_CD")
        String rsltCd;
        @Schema(description = "결과 메시지", example = "")
        @Size(max = 40)
        @JsonProperty("RSLT_MG")
        String rsltMg;
        @JsonProperty("RESP_DATA")
        @Schema(description = "결과 데이터\n" +
                "정상일 때만 출력", example = "", nullable = true)
        List<AcctRespData> respData;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcctRespData {
        @Schema(description = "예금주명\n" +
                "- 한글 10 자리, 그외 20자리", example = "")
        @Size(max = 20)
        @JsonProperty("ACCT_NM")
        String acctNm;
        @Schema(description = "거래 일련 번호", example = "")
        @Size(max = 7)
        @JsonProperty("TRSC_SEQ_NO")
        String trscSeqNo;
    }
}
