package com.bankle.common.comBiz.kakao.ctrl;

import com.bankle.common.comBiz.kakao.svc.KakaoSvc;
import com.bankle.common.comBiz.kakao.vo.KakaoCvo;
import com.bankle.common.comBiz.kakao.vo.KakaoSvo;
import com.bankle.common.commvo.ResData;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.util.CustomeModelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package  :  com.bankle.common.comBiz.kakao.ctrl
 * @class    :  KakaoCtrl.java
 * @date     :  6/18/24
 * @author   :  lsh
 * @version  :  1.
**/
@Tag(name = "COMM-05.카카오 전자서명", description = "카카오 전자서명 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoCtrl {

    private final KakaoSvc kakaoSvc;
    private final CustomeModelMapper customeModelMapper;

    @Operation(summary = "카카오 단건 문서 전자 서명 요청", description = "카카오 단건 문서 전자 서명 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전자 서명 요청 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/kakaocert/requestsign")
    public ResponseEntity<?> requestSign(@RequestBody KakaoCvo.KakaoSignReq kakaocertReq) {
        try {
            return ResData.SUCCESS(kakaoSvc.requestSign(kakaocertReq));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    @Operation(summary = "전자 서명 요청 진행 상태 확인", description = "전자 서명 요청 진행 상태 확인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전자 서명 요청 진행 상태 확인 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/kakaocert/getsignstatus/{receiptID}")
    public ResponseEntity<?> getSignStatus(
            @Parameter(name = "receiptID", description = "접수ID", example = "02307240230400000010000000000002", required = true)
            @PathVariable("receiptID") String receiptID
    ) {
        try {
            return ResData.SUCCESS(kakaoSvc.getSignStatus(receiptID));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    @Operation(summary = "완료된 전자 서명 검증 및 전자 서명 값을 반환", description = "완료된 전자 서명 검증 및 전자 서명 값을 반환 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "반환 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/kakaocert/veriftSign/{receiptID}")
    public ResponseEntity<?> verifySign(
            @Parameter(name = "receiptID", description = "접수ID", example = "02307240230400000010000000000002", required = true)
            @PathVariable("receiptID") String receiptID
    ) {
        try {
            return ResData.SUCCESS(kakaoSvc.verifySign(receiptID));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }
}
