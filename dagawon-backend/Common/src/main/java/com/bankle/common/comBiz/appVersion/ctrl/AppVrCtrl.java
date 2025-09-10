package com.bankle.common.comBiz.appVersion.ctrl;

import com.bankle.common.comBiz.appVersion.svc.AppVrSvc;
import com.bankle.common.comBiz.appVersion.vo.AppVrCvo;
import com.bankle.common.comBiz.appVersion.vo.AppVrSvo;
import com.bankle.common.commvo.ResData;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.util.CustomeModelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package  :  com.bankle.common.comBiz.commcode.ctrl
 * @class    :  AppVrCtrl.java
 * @date     :  6/19/24
 * @author   :  rojoon
 * @version  :  1.
**/
@Tag(name = "COMM-04.APP 버전 관리 서비스", description = "APP 버전 관리 서비스")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AppVrCtrl {
    private final CustomeModelMapper customeModelMapper;
    private final AppVrSvc appVrSvc;


    /**
     * @Package com.bankle.common.comBiz.appVersion.ctrl
     * @Class   AppVrCtrl.class
     * @method  saveAppVr
     * @Author  rojoon
     * @date    2024-06-19
     * @version 1.0.0
     */
    @Operation(summary = "01.앱버전 저장 서비스", description = "앱버전 저장 서비스")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앱버전 저장 성공", content = @Content(schema = @Schema(implementation = void.class)))
    })
    @PostMapping(value = "/comm/saveappvr")
    public ResponseEntity<?> saveAppVr(@RequestBody AppVrCvo.SaveAppVrReqCvo req) {
        try {
            appVrSvc.crtAppVr(customeModelMapper.mapping(req , AppVrSvo.SaveAppVrInSvo.class ));
            return ResData.SUCCESS("앱버전 저장 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResData.FAIL(e.getMessage());
        }
    }

    /**
     * @Package com.bankle.common.comBiz.appVersion.ctrl
     * @Class   AppVrCtrl.class
     * @method  searchLatestAppVr
     * @Author  rojoon
     * @date    2024-06-19
     * @version 1.0.0
     */
    @Operation(summary = "02.최신 앱 버전 조회 서비스", description = "최신 앱 버전 조회 서비스")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최신 앱 버전 조회 성공", content = @Content(schema = @Schema(implementation = AppVrCvo.SearchLatestAppVrResCvo.class)))
    })
    @PostMapping(value = "/comm/searchlatestappvr")
    public ResponseEntity<?> searchLatestAppVr() {
        try {
            return ResData.SUCCESS( appVrSvc.getLatestAppVr(),"앱 최신 버전 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResData.FAIL(e.getMessage());
        }
    }


}
