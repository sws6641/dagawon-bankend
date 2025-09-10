package com.bankle.common.comBiz.kcb.ctrl;

import com.bankle.common.comBiz.kcb.svc.KcbSvc;
import com.bankle.common.comBiz.kcb.vo.KcbCvo;
import com.bankle.common.comBiz.kcb.vo.KcbSvo;
import com.bankle.common.commvo.ResData;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.util.CustomeModelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @package      : com.bankle.common.comBiz.kcb.ctrl
* @name         : KcbCtrl.java     
* @date         : 2024-05-02 오후 3:20
* @author       : Juheon Kim
* @version      : 1.0.0
**/
@Tag(name = "COMM-01.KCB 본인 인증", description = "KCB 본인 인증 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class KcbCtrl {

    private final CustomeModelMapper cmMapper;

    private final KcbSvc kcbSvc;

    
    /**
    *
    *@Package       : com.bankle.common.comBiz.kcb.ctrl
    *@name          : KcbCtrl.java
    *@date          : 2024-05-02 오후 3:20
    *@author        : Juheon Kim
    *@version       : 1.0.0
    **/
    @Operation(summary = "1.KCB 거래요청 (SMS 인증)", description = "KCB 거래요청 서비스" +
            "\n- srvcDsc:   서비스구분코드 (1: SMS방식, 2: PASS방식)" +
            "\n- membNm:    회원명" +
            "\n- telopCd:   통신사코드 (stk:01,kt:02,LGU+:03,알뜰stk:04,알뜰kt:05,알뜰LGU+:06)" +
            "\n- hpNo:      휴대폰번호" +
            "\n- birthDt:   생년월일(YYYYMMDD)" +
            "\n- regBackNo: 주민번호뒷자리" +
            "\n- smsRsndYn: SMS 재전송 여부(Y: 재전송건. N: 첫 요청건)" +
            "\n- agree1:    개인정보 수집/이용/취급위탁 동의(Y/N)" +
            "\n- agree2:    고유식별정보처리 동의(Y/N)" +
            "\n- agree3:    본인확인서비스 이용약관(Y/N)" +
            "\n- agree4:    통신사 이용약관 동의(Y/N)" +
            "\n- trSqn :    거래일련번호(재전송 시 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "거래요청 성공", content = @Content(schema = @Schema(implementation = KcbCvo.KcbTrReqCvo.class)))
    })
    @GetMapping(value = "/comm/searchkcbreq")
    public ResponseEntity<?> searchKcbReq(@Valid KcbCvo.KcbTrReqCvo reqCvo) {
        try {
            KcbCvo.KcbTrResCvo resCvo;
            resCvo = cmMapper
                    .mapping(kcbSvc.fidKcbTrReq(cmMapper
                            .mapping(reqCvo, KcbSvo.KcbTrInSvo.class)), KcbCvo.KcbTrResCvo.class);
            String rsltCd = resCvo.getRsltCd();
            if ("B000".equals(rsltCd)) {
                return ResData.SUCCESS(resCvo, "KCB거래요청 성공");
            } else {
                return ResData.SUCCESS(resCvo, "KCB거래요청 실패");
            }
        } catch (Exception e) {
            log.error("KCB 거래요청 중 오류발생: " + e.getMessage());
            throw new DefaultException(e.getMessage());
        }
    }

    /**
    *
    *@Package       : com.bankle.common.comBiz.kcb.ctrl
    *@name          : KcbCtrl.java
    *@date          : 2024-05-02 오후 3:20
    *@author        : Juheon Kim
    *@version       : 1.0.0
    **/
    @Operation(summary = "2.본인인증 결과 (SMS 인증)", description = "본인인증 결과 서비스" +
            "\n- srvcDsc:   서비스구분코드 (1: SMS방식, 2: PASS방식)" +
            "\n- hpNo:      휴대폰번호" +
            "\n- smsCtfcNo: SMS인증번호" +
            "\n- trSqn:     거래일련번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "본인인증 성공", content = @Content(schema = @Schema(implementation = KcbCvo.KcbTrResCvo.class)))
    })
    @GetMapping(value = "/comm/searchkcbres")
    public ResponseEntity<?> searchKcbRes(@Valid KcbCvo.KcbRsltReqCvo reqCvo) {
        try {
            KcbCvo.KcbRsltResCvo resCvo = cmMapper
                    .mapping(kcbSvc.fidKcbRsltReq(cmMapper
                            .mapping(reqCvo, KcbSvo.KcbRsltInSvo.class)), KcbCvo.KcbRsltResCvo.class);
            String rsltCd = resCvo.getRsltCd();
            if ("B000".equals(rsltCd)) {
                return ResData.SUCCESS(resCvo, "본인인증 성공");
            } else {
                return ResData.SUCCESS(resCvo, "본인인증 실패");
            }
        } catch (Exception e) {
            log.error("KCB 거래요청 중 오류발생: " + e.getMessage());
            throw new DefaultException(e.getMessage());
        }
    }

    /**
    *
    *@Package       : com.bankle.common.comBiz.kcb.ctrl
    *@name          : KcbCtrl.java
    *@date          : 2024-05-02 오후 3:20
    *@author        : Juheon Kim
    *@version       : 1.0.0
    **/
    @Operation(summary = "3.KCB 거래요청 (팝업 인증)", description = "KCB 거래요청 서비스" +
            "\n- chkAuthYn:   거래요청 성공 여부" +
            "\n- kcbCpCd:     회원사코드" +
            "\n- kcbPopUrl:   kcb PopUp URL" +
            "\n- mdlTkn:      토큰" +
            "\n- rsltCd:      결과코드" +
            "\n- rsltMsg:     결과메세지"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "거래요청 성공", content = @Content(schema = @Schema(implementation = KcbCvo.PopUpKcbTrResCvo.class)))
    })
    @GetMapping(value = "/comm/searchpopupkcbreq")
    public ResponseEntity<?> searchPopUpKcbReq() {
        try {
            KcbCvo.PopUpKcbTrResCvo resCvo = cmMapper
                    .mapping(kcbSvc.KcbPopUpAuthReq(), KcbCvo.PopUpKcbTrResCvo.class);
            String rsltCd = resCvo.getRsltCd();
            if ("B000".equals(rsltCd)) {
                return ResData.SUCCESS(resCvo, "KCB거래요청 성공");
            } else {
                return ResData.SUCCESS(resCvo, "KCB거래요청 실패");
            }
        } catch (Exception e) {
            log.error("KCB 거래요청 중 오류발생: " + e.getMessage());
            throw new DefaultException(e.getMessage());
        }
    }

    /**
    *
    *@Package       : com.bankle.common.comBiz.kcb.ctrl
    *@name          : KcbCtrl.java
    *@date          : 2024-05-02 오후 3:21
    *@author        : Juheon Kim
    *@version       : 1.0.0
    **/
    @Operation(summary = "4.본인인증 결과 (팝업 인증)", description = "본인인증 결과 서비스" +
            "\n- mdlTkn:    kcb 토큰"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "본인인증 성공", content = @Content(schema = @Schema(implementation = KcbCvo.KcbRsltResCvo.class)))
    })
    @GetMapping(value = "/comm/searchpopupkcbres")
    public ResponseEntity<?> searchPopUpKcbRes(@Valid KcbCvo.KcbPopUpRsltReqCvo reqCvo) {
        try {
            KcbCvo.KcbRsltResCvo resCvo = cmMapper
                    .mapping(kcbSvc.KcbPopUpAuthRes(cmMapper
                            .mapping(reqCvo, KcbSvo.KcbPopUpRsltInSvo.class)), KcbCvo.KcbRsltResCvo.class);
            String rsltCd = resCvo.getRsltCd();

            if ("B000".equals(rsltCd)) {
                return ResData.SUCCESS(resCvo, "본인인증 성공");
            } else {
                return ResData.SUCCESS(resCvo, "본인인증 실패");
            }
        } catch (Exception e) {
            log.error("KCB 거래요청 중 오류발생: " + e.getMessage());
            throw new DefaultException(e.getMessage());
        }
    }
}
