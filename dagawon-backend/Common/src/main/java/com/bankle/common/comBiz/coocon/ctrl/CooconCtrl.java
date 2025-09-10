package com.bankle.common.comBiz.coocon.ctrl;

import com.bankle.common.comBiz.coocon.svc.CooconSvc;
import com.bankle.common.comBiz.coocon.vo.CooconCvo;
import com.bankle.common.comBiz.coocon.vo.CooconSvo;
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

import java.util.List;

/**
 * @package  :  com.bankle.common.comBiz.coocon.ctrl
 * @class    :  CooconCtrl.java
 * @date     :  6/19/24
 * @author   :  lsh
 * @version  :  1.
**/
@Tag(name = "COMM-06.쿠콘 서비스", description = "COMM - 쿠콘 서비스")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CooconCtrl {

    private final CooconSvc cooconSvc;
    private final CustomeModelMapper customeModelMapper;

    @Operation(summary = "쿠콘 - 예금주 조회 ", description = "쿠콘 - 예금주 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠콘 - 예금주 조회 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/coocon/searchacctno")
    public ResponseEntity<?> searchAcctNo(@RequestBody List<CooconCvo.AcctReqData> cooconCvoList) {
        try {
            return ResData.SUCCESS(
                    cooconSvc.searchAcctNo(
                            cooconCvoList.stream().map(cvo -> customeModelMapper.mapping(cvo, CooconSvo.AcctReqData.class)).toList()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }
}
