package com.bankle.common.comBiz.mesg.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

public class MesgSvo {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MesgSearchIn {
        @Schema(description = "템플릿 구분 코드", name = "tpltSeq", example = "S01001")
        @Size(min = 6, max = 6, message = "템플릿 구분 코드는 6자리 입니다.")
        String tpltSeq;
        @Schema(description = "수신번호", name = "rcvNo", example = "")
        String rcvNo;
        @Schema(description = "발신번호", name = "sndNo", example = "")
        String sndNo;
        @Schema(description = "수신자회원번호", name = "addreMembNo", example = "")
        String addreMembNo;
        @Schema(description = "에스크로 번호", name = "escrNo", example = "")
        String escrNo;
        @Schema(description = "수신자명", name = "addreNm", example = "")
        String addreNm;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MesgSearchOut {
        @Schema(description = "메시지 전송 이력 시퀀스", name = "seq", example = "")
        String seq;
        @Schema(description = "에스크로 번호", name = "escrNo", example = "")
        String escrNo;
        @Schema(description = "접수번호", name = "acptNo", example = "")
        String acptNo;
        @Schema(description = "템플릿 일련번호", name = "tpltSeq", example = "")
        String tpltSeq;
        @Schema(description = "템플릿 구분코드", name = "tpltGbCd", example = "")
        String tpltGbCd;
        @Schema(description = "템플릿내용종류구분코드", name = "tpltCntsKndGbCd", example = "")
        String tpltCntsKndGbCd;
        @Schema(description = "메시지 제목", name = "msgTitle", example = "")
        String msgTitle;
        @Schema(description = "메시지 전송내용", name = "msgTransCnts", example = "")
        String msgTransCnts;
        @Schema(description = "메시지 대체 전송 내용", name = "altMsgTransCnts", example = "")
        String altMsgTransCnts;
        @Schema(description = "발신번호", name = "sndNo", example = "")
        String sndNo;
        @Schema(description = "발신일시", name = "sndDtm", example = "")
        LocalDateTime sndDtm;
        @Schema(description = "수신번호", name = "rcvNo", example = "")
        String rcvNo;
        @Schema(description = "수신자회원번호", name = "addreMembNo", example = "")
        String addreMembNo;
        @Schema(description = "수신자명", name = "addreNm", example = "")
        String addreNm;
        @Schema(description = "수신 FCM_ID", name = "rcvFcmId", example = "")
        String rcvFcmId;
        @Schema(description = "결과코드", name = "rslTCd", example = "")
        String rslTCd;
        @Schema(description = "생성일시", name = "crtDtm", example = "")
        String crtDtm;
        @Schema(description = "생성회원번호", name = "crtMembNo", example = "")
        String crtMembNo;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MesgTpltOut {
        @Schema(name = "tpltSeq", description = "템플릿 일련번호", example = "")
        String tpltSeq;
        @Schema(name = "tpltGbCd", description = "템플릿 구분 코드", example = "")
        String tpltGbCd;
        @Schema(name = "tpltKndGbCd", description = "템플릿 종류 구분 코드", example = "")
        String tpltKndGbCd;
        @Schema(name = "tpltTtl", description = "템플릿 제목", example = "")
        String tpltTtl;
        @Schema(name = "tpltCnts", description = "템플릿 내용", example = "")
        String tpltCnts;
        @Schema(name = "tpltCd", description = "카카오톡 템플릿 코드", example = "")
        String tpltCd;
        @Schema(name = "altTpltCd", description = "대체 템플릿 코드", example = "")
        String altTpltCd;
        @Schema(name = "altTpltCnts", description = "대체 템플릿 내용", example = "")
        String altTpltCnts;
        @Schema(name = "gidTelno", description = "안내 전화번호", example = "")
        String gidTelno;
        @Schema(name = "useYn", description = "사용 여부", example = "")
        String useYn;
        @Schema(name = "btnCd", description = "버튼 코드", example = "")
        String btnCd;
        @Schema(name = "btnNm", description = "버튼 명", example = "")
        String btnNm;
        @Schema(name = "tpltUri", description = "템플릿 연결주소", example = "")
        String tpltUri;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MesgTpltIn {
        @Schema(name = "tpltSeq", description = "템플릿 일련번호", example = "")
        String tpltSeq;
        @Schema(name = "tpltGbCd", description = "템플릿 구분 코드", example = "")
        String tpltGbCd;
        @Schema(name = "tpltKndGbCd", description = "템플릿 종류 구분 코드", example = "")
        String tpltKndGbCd;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MesgPushIn {
        @Schema(description = "FCM_ID", name = "fcmId", example = "ZdadV1234GYH&%$#$Ssdfgsdfgsd!$$444423444")
        String fcmId;
        @Schema(description = "템플릿 제목", name = "tpltTtl", example = "템플릿 제목")
        String tpltTtl;
        @Schema(description = "템플릿 내용", name = "tpltCnts", example = "템플릿 내용")
        String tpltCnts;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendPushIn {
        String cphnNo;
        String membNo;
        String fcmId;
        String msg;
        String title;
    }
}
