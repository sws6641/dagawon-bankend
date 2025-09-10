//package kr.co.anbu.controller;
//
//import kr.co.anbu.component.toss.TossPaymentsService;
//import kr.co.anbu.infra.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/toss")
//public class PaymentRestController {
//
//    private final TossPaymentsService TossCardPaymentsService;
//    private final Response response;
//
//    /**
//     * 결제 승인요청
//     * @param escrMKey
//     * @param params
//     * @return
//     */
//    @PostMapping("/approval/{escrMKey}")
//    public ResponseEntity<?> reqApproval(@PathVariable String escrMKey,
//                                         @RequestBody HashMap<String, String> params,
//                                         @RequestParam String paymentKey,
//                                         @RequestParam String orderId,
//                                         @RequestParam String amount){
//
//        log.info("결제 승인요청 시작 ==================================");
//        try {
//            params.put("paymentKey", paymentKey);
//            params.put("orderId", orderId);
//            params.put("amount", amount);
//
//            return response.success(
//                    TossCardPaymentsService.reqApproval(escrMKey, params),
//                    "success",
//                    HttpStatus.OK);
//        } catch (IOException | InterruptedException e) {
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
