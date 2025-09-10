package com.bankle.common.comBiz.kcb.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@RequiredArgsConstructor
public class KcbCvo {

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KcbTrReqCvo {

        @Schema(description = "서비스구분코드 [1: SMS방식, 2: PASS방식]" ,example = "1")
        @Size(min= 1, max = 1 , message = "서비스구분코드는 한자리 입니다.")
        String srvcDsc;

        @Schema(description = "성명", example ="홍길동")
        @NotEmpty(message = "성명을 입력하여 주세요.")
        @Pattern(regexp = "^[가-힝a-zA-Z ]*", message = "한글과 영어만 입력 가능 합니다.")
        String membNm;

        @Schema(description = "통신사코드 stk:01,kt:02,LGU+:03,알뜰stk:04,알뜰kt:05,알뜰LGU+:06", example = "01")
        @NotEmpty(message = "통신사 코드를 선택하여주세요")
        String telopCd;

        @Schema(description = "휴대폰번호" , example = "01012341234")
        @NotEmpty(message = "휴대폰 번호를 입력하여 주세요.")
        @Size(min= 1, max = 11 , message = "휴대폰번호를 입력하여주세요")
        String hpNo;

        @Schema(description = "생년월일(YYYYMMDD)")
        @Size(min= 8, max = 8 , message = "생년월일은 8자리 입니다.")
//        @Pattern(regexp = "/([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))/", message = "올바른 생년월일 6자리를 입력하여주세요")
        String birthDt;

        @Schema(description = "주민번호뒷자리")
        @Size(min= 1, max = 1 , message = "주민번호 뒷자리를 입력하여주세요")
        String regBackNo;

        @Schema(description = "SMS 재전송 여부(Y: 재전송건. N: 첫 요청건)")
        @NotEmpty(message = "SMS 재전송 여부 입력하여 주세요.")
        String smsRsndYn;

        @Schema(description = "개인정보 수집/이용/취급위탁 동의(Y/N)")
        @NotEmpty(message = "개인정보 수집/이용/취급위탁 동의를 입력하여 주세요.")
        String agree1;

        @Schema(description = "고유식별정보처리 동의(Y/N)")
        @NotEmpty(message = "고유식별정보처리 동의를 입력하여 주세요.")
        String agree2;

        @Schema(description = "본인확인서비스 이용약관(Y/N)")
        @NotEmpty(message = "본인확인서비스 이용약관을 입력하여 주세요.")
        String agree3;

        @Schema(description = "통신사 이용약관 동의(Y/N)")
        @NotEmpty(message = "통신사 이용약관 동의를 입력하여 주세요.")
        String agree4;

        @Schema(description = "거래일련번호(재전송 시 필요)")
        String trSqn;
    }

    @Getter
    @Setter
    public static class KcbTrResCvo {
        @Schema(example = "결과코드")
        String rsltCd;
        @Schema(example = "결과메세지")
        String rsltMsg;
        @Schema(example = "거래일련번호")
        String trSqn;

    }

    @Getter
    @Setter
    public static class PopUpKcbTrResCvo {
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
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KcbPopUpRsltReqCvo {
        @Schema(description = "kcb 토큰" ,example = "e886d6cb999b4b41b3026d071db85f35")
        @NotBlank(message = "kcb 토큰을 입력하여 주세요.")
        String mdlTkn;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KcbRsltResCvo {
        @Schema(example = "결과코드")
        String rsltCd;
        @Schema(example = "결과메세지")
        String rsltMsg;
        @Schema(example = "거래일련번호")
        String trSqn;
        @Schema(example = "CI 갱신여부")
        String ciUpdate;
        @Schema(example = "성명")
        String membNm;
        @Schema(example = "휴대폰번호")
        String hpNo;
        @Schema(example = "통신사코드")
        String telopCd;
        @Schema(example = "생년월일")
        String birthDt;
        @Schema(example = "성별코드")
        String sex;
        @Schema(example = "내/외국인코드")
        String ntvFrnrDsc;
        @Schema(example = "Device정보 (디바이스고유식별자)")
        String di;
        @Schema(example = "연계정보(개인을 식별하기 위한 값")
        String ci;
    }
    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KcbRsltReqCvo {

        @Schema(description = "서비스구분코드 [1: SMS방식, 2: PASS방식]" ,example = "1")
        @Size(min= 1, max = 1 , message = "서비스구분코드는 한자리 입니다.")
        String srvcDsc;

        @Schema(description = "휴대폰번호" , example = "01012341234")
        @NotEmpty(message = "휴대폰 번호를 입력하여 주세요.")
        @Size(min= 1, max = 11 , message = "휴대폰번호를 입력하여주세요")
        String hpNo;

        @Schema(description = "SMS인증번호" ,example = "091093")
        @NotEmpty(message = "인증번호를 입력하여 주세요.")
        String smsCtfcNo;

        @Schema(example = "거래일련번호")
        String trSqn;

    }

}

