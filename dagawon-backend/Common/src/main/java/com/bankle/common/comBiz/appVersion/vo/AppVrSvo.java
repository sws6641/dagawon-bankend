package com.bankle.common.comBiz.appVersion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


public class AppVrSvo {
    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveAppVrInSvo {
        @Size(min = 1 ,max = 100)
        @NotEmpty(message = "앱버전은 필수 입력값 입니다.")
        @Schema(description = "앱버전", example = "1.0.0+1")
        String appVr;
        @Schema(description = "내용", example = "오류 수정")
        String content;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchLatestAppVrOutSvo {
        @Schema(description = "시퀀스", example = "1")
        Long vrSeq;
        @Schema(description = "앱버전", example = "1.0.0+1")
        String appVr;
        @Schema(description = "내용", example = "오류 수정")
        String content;
        @Schema(description = "생성일자", example = "2024-06-19")
        String crtDtm;
        @Schema(description = "생성회원번호", example = "000000000000")
        String crtMembNo;
    }
}