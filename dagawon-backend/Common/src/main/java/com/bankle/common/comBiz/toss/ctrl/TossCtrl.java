package com.bankle.common.comBiz.toss.ctrl;

import com.bankle.common.comBiz.toss.svc.TossSvc;
import com.bankle.common.comBiz.toss.vo.TossCvo;
import com.bankle.common.comBiz.toss.vo.TossSvo;
import com.bankle.common.commvo.ResData;
import com.bankle.common.util.CustomeModelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*
*@Package       : com.bankle.common.comBiz.toss.ctrl
*@name          : TossCtrl.java
*@date          : 2024-05-07 오후 1:36
*@author        : Juheon Kim
*@version       : 1.0.0
**/
@Tag(name = "COMM-02.Toss 수수료 결제 승인 요청", description = "수수료 결제 승인 요청 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class TossCtrl {

    private final TossSvc tossSvc;

    private final CustomeModelMapper cmMapper;

    
    /**
    *
    * @name     : TossCtrl.approval
    * @author   : JuHeon Kim
    * @param    : 
    * @return   :
    **/
    @Operation(summary = "1.Toss 결제 승인 요청", description = "Toss 결제 서비스" +
            "\n- escroKey: 에스크로 계약 Key" +
            "\n- paymentKey: 결제의 키 값입니다. 최대 길이는 200자. 결제를 식별하는 역할로, 중복되지 않는 고유값" +
            "\n- amount:  결제 금액" +
            "\n- orderId: 결제 식별값. 무작위 고유한 값. 영문 대소문자, 숫자, 특수문자 -, _로 이루어진 6자 이상 64자 이하의 문자열"
    )
    @GetMapping("/comm/approval")
    public ResponseEntity<?> approval(@Valid TossCvo.TossTrReqCvo reqCvo) {
        log.info("결제 승인요청 시작 ==================================");
        try {
            return ResData.SUCCESS(
                    tossSvc.reqPayApproval(cmMapper
                            .mapping(reqCvo, TossSvo.TossTrInSvo.class)),
                    "TOSS 수수료 결제요청 성공");
        } catch (Exception e) {
            return ResData.FAIL(e.getMessage());
        }
    }
    
    /**
    *
    * @name     : TossCtrl.cancel
    * @author   : JuHeon Kim
    * @param    : 
    * @return   :
    **/
    @Operation(summary = "2.Toss 결제 취소", description = "Toss 결제 서비스" +
            "\n- escroKey: 에스크로 계약 Key" +
            "\n- paymentKey: 결제의 키 값입니다. 최대 길이는 200자. 결제를 식별하는 역할로, 중복되지 않는 고유값" +
            "\n- cancelReason: 결제를 취소하는 이유입니다. 최대 길이는 200자." +
            "\n- cancelAmount:  취소 금액"
    )
    @GetMapping("/comm/cancel")
    public ResponseEntity<?> cancel(@Valid TossCvo.TossCncelReqCvo reqCvo) {
        log.info("결제 취소 시작 ==================================");
        try {
            return ResData.SUCCESS(
                    tossSvc.reqPayCancel(cmMapper
                            .mapping(reqCvo, TossSvo.TossTrInSvo.class)),
                    "TOSS 수수료 결제 취소 성공");
        } catch (Exception e) {
            return ResData.FAIL(e.getMessage());
        }
    }
}


