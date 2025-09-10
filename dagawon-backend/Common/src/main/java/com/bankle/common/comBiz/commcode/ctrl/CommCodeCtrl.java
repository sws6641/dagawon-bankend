package com.bankle.common.comBiz.commcode.ctrl;

import com.bankle.common.comBiz.commcode.svc.CommCodeSvc;
import com.bankle.common.comBiz.commcode.vo.CommCodeCvo;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @package  :  com.bankle.common.comBiz.commcode.ctrl
 * @class    :  CommCodeCtrl.java
 * @date     :  5/21/24
 * @author   :  lsh
 * @version  :  1.
**/
@Tag(name = "COMM-99.공통코드 서비스", description = "공통코드 CRUD 서비스")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CommCodeCtrl {
    private final CustomeModelMapper customeModelMapper;

    private final CommCodeSvc commCodeSvc;

    /**
     * @Package com.bankle.common.comBiz.commcode.ctrl
     * @Class   CommCodeCtrl.class
     * @method  searchCommCodeMulti
     * @Author  sh.lee
     * @date    2024-05-21
     * @version 1.0.0
     */
    @Operation(summary = "다중 공통코드 조회", description = "다중 공통코드 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다중 공통코드 조회 성공", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping(value = "/comm/searchcommcodemulti")
    public ResponseEntity<?> searchCommCodeMulti(@RequestBody List<String> multiGrpCd) {
        try {
            return ResData.SUCCESS(commCodeSvc.searchCommCodeMultiList(multiGrpCd));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    /**
     * @Package com.bankle.common.comBiz.commcode.ctrl
     * @Class   CommCodeCtrl.class
     * @method  searchCommCodeMap
     * @Author  sh.lee
     * @date    2024-05-21
     * @version 1.0.0
     */
    @Operation(summary = "공통코드 조회(return Map)", description = "공통코드 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공통코드 조회 성공", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping(value = "/comm/searchcommcodemap")
    public ResponseEntity<?> searchCommCodeMap(@RequestParam
                                            @Parameter(description = "그룹 코드", example = "AGR_CD") String grpCd) {
        try {
            return ResData.SUCCESS(commCodeSvc.searchCommCodeMap(grpCd));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    /**
     * @Package com.bankle.common.comBiz.commcode.ctrl
     * @Class   CommCodeCtrl.class
     * @method  searchCommCode
     * @Author  sh.lee
     * @date    2024-05-21
     * @version 1.0.0
     */
    @Operation(summary = "공통코드 조회(return List)", description = "공통코드 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공통코드 조회 성공", content = @Content(schema = @Schema(implementation = CommCodeCvo.SearchCommCodeReqCvo.class)))
    })
    @PostMapping(value = "/comm/searchcommcode")
    public ResponseEntity<?> searchCommCode(@RequestParam
                                               @Parameter(description = "그룹 코드", example = "AGR_CD") String grpCd) {
        try {
            return ResData.SUCCESS(commCodeSvc.searchCommCodeList(grpCd));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }
}
