package com.bankle.common.comBiz.mesg.ctrl;

import com.bankle.common.comBiz.mesg.svc.MesgSvc;
import com.bankle.common.comBiz.mesg.vo.MesgCvo;
import com.bankle.common.comBiz.mesg.vo.MesgSvo;
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
 * @author :  lsh
 * @version :  1.
 * @package :  com.bankle.common.comBiz.mesg.ctrl
 * @class :  MesgCtrl.java
 * @date :  5/13/24
 **/
@Tag(name = "MESG-01.메세지 공통", description = "메세지 공통")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MesgCtrl {

    private final CustomeModelMapper customeModelMapper;

    private final MesgSvc mesgSvc;

    @Operation(summary = "메세지 전송이력 리스트 조회", description = "카카오톡, 푸쉬, 문자 전송 이력 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = @Content(schema = @Schema(implementation = MesgCvo.MesgSearchRes.class)))
    })
    @PostMapping(value = "/mesg/sendhistlist")
    public ResponseEntity<?> mesgHistList(@RequestBody MesgCvo.MesgSearchReq mesgSearchReq) {
        try {
            return ResData.SUCCESS(
                    customeModelMapper.mapping(
                            mesgSvc.mesgHistList(
                                    customeModelMapper.mapping(mesgSearchReq, MesgSvo.MesgSearchIn.class)
                            ), MesgCvo.MesgSearchRes.class
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    @Operation(summary = "메세지 템플릿 가져오기", description = "메세지 템플릿 가져오기 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메세지 템플릿 가져오기 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/mesg/searchmesgtplt")
    public ResponseEntity<?> searchMesgTplt(
            MesgCvo.MesgTpltReq tpltReq
    ) {
        try {
            return ResData.SUCCESS(mesgSvc.findMesgTplt(customeModelMapper.mapping(tpltReq,MesgSvo.MesgTpltIn.class)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    @Operation(summary = "푸쉬 발송", description = "푸쉬 발송 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "푸쉬 발송 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/mesg/sendpush")
    public ResponseEntity<?> sendPush() {
        try {
            return ResData.SUCCESS(mesgSvc.sendPush());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }
}
