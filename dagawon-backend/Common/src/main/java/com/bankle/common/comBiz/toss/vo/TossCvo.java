package com.bankle.common.comBiz.toss.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

public class TossCvo {

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TossTrReqCvo {

        @Schema(description = "에스크로 Key" ,example = "121212")
        String escroKey;

        @Schema(description = "결제 요청 Key" ,example = "1121312")
        String paymentKey;

        @Schema(description = "결제 금액" ,example = "10000000")
        BigDecimal amount;

        @Schema(description = "Order ID" ,example = "1212123")
        String orderId;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TossTrResCvo {

        @Schema(description = "결과 코드" ,example = "000")
        String code;

        @Schema(description = "결과 메세지" ,example = "실패")
        String message;
    }
    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TossCncelReqCvo {

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
