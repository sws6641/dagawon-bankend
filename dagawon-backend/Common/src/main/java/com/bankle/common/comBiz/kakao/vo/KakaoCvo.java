package com.bankle.common.comBiz.kakao.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class KakaoCvo {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoSignReq {
        @Schema(description = "에스크로 번호", example = "20240508000011")
        String escrNo;
        @Schema(description = "수신자 생년월일(yyyyMMdd)", example = "19910415")
        String rcvBirthDt;
        @Schema(description = "수신자 성명", example = "이상협")
        String rcvNm;
        @Schema(description = "수신자 핸드폰번호", example = "010-9927-4288")
        String rcvCphnNo;
    }

    @Getter
    @Setter
    public static class KakaoCertReq {
        @Schema(description = "에스크로 번호", example = "20240508000011")
        String escrNo;
        KakaoCertSignReq sign;
        KakaoEnvCertReq env;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoCertSignReq {
        @Schema(description = "수신자 생년월일(yyyyMMdd)", example = "19910415")
        String rcvBirthDt;
        @Schema(description = "수신자 성명", example = "이상협")
        String rcvNm;
        @Schema(description = "수신자 핸드폰번호", example = "010-9927-4288")
        String rcvCphnNo;
        @Schema(description = "인증요청 메시지 부가 내용", example = " ")
        String extraMessage;
        @Schema(description = "인증요청 메세지 제목", example = "에스크로 이용 동의")
        String signTitle;
        @Schema(description = "token value", example = "전자서명 원문")
        String token;
        @Schema(description = "이용기관이 API 요청마다 생성한 메모 값", example = " ")
        String payLoad;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoEnvCertReq {
        @Schema(description = "고객센터 전화번호  , 카카오톡 인증메시지 중 \"고객센터\" 항목에 표시", example = "1600-9999")
        String sndNo;
        @Schema(description = "별칭코드, 이용기관이 생성한 별칭코드 (파트너 사이트에서 확인가능)\n" +
                "카카오톡 인증메시지 중 \"요청기관\" 항목에 표시\n" +
                "별칭코드 미 기재시 이용기관의 이용기관명이 \"요청기관\" 항목에 표시", example = "")
        String subClientId;
        @Schema(description = "인증요청 만료시간(초), 최대값 : 1000  인증요청 만료시간(초) 내에 미인증시, 만료 상태로 처리됨 (권장 : 300)", example = "300")
        Integer expeiresIn;
//        @Schema(description = "인증서 발급유형 선택\n" +
//                "true : 휴대폰 본인인증만을 이용해 인증서 발급\n" +
//                "false : 본인계좌 점유 인증을 이용해 인증서 발급", example = "true")
//        Boolean allowSimpleRegistYN;
//        @Schema(description = "수신자 실명확인 여부\n" +
//                "true : 카카오페이가 본인인증을 통해 확보한 사용자 실명과 ReceiverName 값을 비교\n" +
//                "false : 카카오페이가 본인인증을 통해 확보한 사용자 실명과 RecevierName 값을 비교하지 않음.", example = "true")
//        Boolean verifyNameYN;
    }

}
