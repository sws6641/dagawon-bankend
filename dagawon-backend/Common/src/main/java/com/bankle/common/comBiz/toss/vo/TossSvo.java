package com.bankle.common.comBiz.toss.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

public class TossSvo {

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TossTrInSvo {

        @Schema(description = "에스크로 Key" ,example = "121212")
        String escroKey;

        @Schema(description = "결제 요청 Key" ,example = "1121312")
        String paymentKey;

        @Schema(description = "결제 금액" ,example = "10000000")
        BigDecimal amount;

        @Schema(description = "Order ID" ,example = "1212123")
        String orderId;

        @Schema(description = "취소 이유" ,example = "단순변심")
        String cancelReason;


    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TossTrOutSvo {

        @Schema(description = "결과 코드" ,example = "000")
        String code;

        @Schema(description = "결과 메세지" ,example = "실패")
        String message;

        @Schema(description = "결과 수단" ,example = "card")
        String method;

        @Schema(description = "에스크로 Key" ,example = "121212")
        String escroKey;

        @Schema(description = "결제 요청 Key" ,example = "1121312")
        String paymentKey;

        @Schema(description = "결제 금액" ,example = "10000000")
        BigDecimal amount;

        @Schema(description = "Order ID" ,example = "1212123")
        String orderId;

        @Schema(description = "결과 상태" ,example = "DONE")
        String status;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TossCncelInSvo {

        @Schema(description = "에스크로 Key" ,example = "121212")
        String escroKey;

        @Schema(description = "결제 요청 Key" ,example = "1121312")
        String paymentKey;

        @Schema(description = "취소 이유" ,example = "단순변심")
        String cancelReason;

        @Schema(description = "취소 금액" ,example = "취소 금액")
        BigDecimal cancelAmount;
    }
}
