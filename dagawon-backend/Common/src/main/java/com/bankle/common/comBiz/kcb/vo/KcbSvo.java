package com.bankle.common.comBiz.kcb.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@RequiredArgsConstructor
public class KcbSvo {

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KcbTrInSvo {

        String srvcDsc;

        String membNm;

        String telopCd;

        String hpNo;

        String birthDt;

        String regBackNo;

        String smsRsndYn;

        String agree1;

        String agree2;

        String agree3;

        String agree4;

        String trSqn;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KcbTrOutSvo {

        String rsltCd;

        String rsltMsg;

        String trSqn;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KcbRsltInSvo {

        @Schema(description = "서비스구분코드 [1: SMS방식, 2: PASS방식]", example = "1")
        @Size(min = 1, max = 1, message = "서비스구분코드는 한자리 입니다.")
        String srvcDsc;

        @Schema(description = "휴대폰번호", example = "01012341234")
        @NotEmpty(message = "휴대폰 번호를 입력하여 주세요.")
        @Size(min = 1, max = 11, message = "휴대폰번호를 입력하여주세요")
        String hpNo;

        @Schema(description = "SMS인증번호", example = "091093")
        @NotEmpty(message = "인증번호를 입력하여 주세요.")
        String smsCtfcNo;

        @Schema(example = "거래일련번호")
        String trSqn;

    }

    @Getter
    @Setter
    public static class KcbRsltOutSvo {
        @Schema(description = "결과코드")
        String rsltCd;
        @Schema(description = "결과메세지")
        String rsltMsg;
        @Schema(description = "거래일련번호")
        String trSqn;
        @Schema(description = "CI 갱신여부")
        String ciUpdate;
        @Schema(description = "성명")
        String membNm;
        @Schema(description = "휴대폰번호")
        String hpNo;
        @Schema(description = "통신사코드")
        String telopCd;
        @Schema(description = "생년월일")
        String birthDt;
        @Schema(description = "성별코드")
        String sex;
        @Schema(description = "내/외국인코드")
        String ntvFrnrDsc;
        @Schema(description = "Device정보 (디바이스고유식별자)")
        String di;
        @Schema(description = "연계정보(개인을 식별하기 위한 값")
        String ci;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopUpKcbTrOutSvo {
        @Schema(example = "거래요청 성공 여부")
        String chkAuthYn;
        @Schema(example = "회원사코드")
        String kcbCpCd;
        @Schema(example = "kcb PopUp URL")
        String kcbPopUrl;
        @Schema(example = "토큰")
        String mdlTkn;
        @Schema(example = "결과코드")
        String rsltCd;
        @Schema(example = "결과메세지")
        String rsltMsg;
        @Schema(description = "거래일련번호")
        String trSqn;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KcbPopUpRsltInSvo {
        @Schema(description = "kcb 토큰" ,example = "e886d6cb999b4b41b3026d071db85f35")
        @NotBlank(message = "kcb 토큰을 입력하여 주세요.")
        String mdlTkn;
    }
}
