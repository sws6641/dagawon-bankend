package com.bankle.common.comBiz.infoTech.ctrl;



import com.bankle.common.comBiz.infoTech.svc.InfoTechSvc;
import com.bankle.common.comBiz.infoTech.vo.InfoTechCvo;
import com.bankle.common.comBiz.infoTech.vo.InfoTechSvo;
import com.bankle.common.commvo.ResData;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name ="COMM-03.InfoTech 등기부등본 API" , description = "등기고유정보 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class InfoTechCtrl {

    private final InfoTechSvc infoTechSvc;
    private final CustomeModelMapper customeModelMapper;
    private final String COMM_BASE_PATH = "/comm";

    @Operation(summary = "01.등기고유번호으로 주소 검색 API", description = "등기고유번호으로 등기정보를 검색한다.")
    @ApiResponses(value = {
            @ApiResponse( responseCode="200", description = "등기정보 조회 성공", content = @Content(schema = @Schema(implementation = InfoTechCvo.InfoTechResCvo.class)))
    })
    @PostMapping(value = COMM_BASE_PATH + "/searchaddr/{uniqNo}")
    public ResponseEntity<?> searchAddr(@PathVariable(name = "uniqNo") String uniqNo) {
        try {
            InfoTechCvo.InfoTechResCvo res = infoTechSvc.fidAddr(uniqNo);
            if(res.getResCd() != null) {
                return ResData.SUCCESS(res, "등기정보 조회 성공");
            }else{
                return ResData.FAIL("등기정보 조회 실패");
            }

        } catch (Exception e) {
            log.error("등기정보 조회: " + e.getMessage());
            return ResData.FAIL("등기정보 조회 중 오류가 발생하였습니다.");
        }
    }

    @Operation(summary = "02.주소으로 등기고유번호 검색 API", description = "List<String> 형태로 등기고유번호를 return")
    @ApiResponses(value = {
            @ApiResponse( responseCode="200", description = "등기정보 조회 성공", content = @Content(schema = @Schema(implementation = List.class)))
    })
    @PostMapping(value = COMM_BASE_PATH + "/searchuniqno/{addr}")
    public ResponseEntity<?> searchUniqNo(@PathVariable(name = "addr") String addr) {
        try {
            List<InfoTechCvo.SearchAddrResCvo> res = infoTechSvc.fidUniqNo(addr);

            if(res != null && !res.isEmpty()) {
                return ResData.SUCCESS(res, "주소 조회 성공");
            }else{
                return ResData.FAIL("주소 조회 실패");
            }

        } catch (Exception e) {
            log.error("등기정보 조회: " + e.getMessage());
            return ResData.FAIL(e.getMessage());
        }
    }

    @Operation(summary = "03.등기변동 여부 판단 API (등기신청사건 조회)", description = " 등기변동 내용이 있을 시 : Y / 등기변동 내용이 없을 시 : N ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등기변동 여부 판단 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = COMM_BASE_PATH + "/searchrgstrchgyn")
    public ResponseEntity<?> searchRgstrChgYn(@RequestBody
                                                  // @Valid
                                                  InfoTechCvo.SearchRgstrChgYnReqCvo req) {
        try {
            String res = infoTechSvc.fidRgstrIcdntInfo(customeModelMapper.mapping(req, InfoTechSvo.SearchRgstrChgYnInSvo.class));
            if ("".equals(res)) {
                return ResData.FAIL(res, "조회 데이터가 존재하지 않습니다.");
            } else {
                return ResData.SUCCESS(res, "등기변동 여부 판단 성공");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResData.FAIL(e.getMessage());
        }
    }



    @Operation(summary = "04.등기부등본 발급 API", description = "등기부등본 발급 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등기부등본 발급 성공", content = @Content(schema = @Schema(implementation = boolean.class)))
    })
    @PostMapping(value = COMM_BASE_PATH + "/searchrgstrisn")
    public ResponseEntity<?> searchRgstrIsn(@RequestBody @Valid InfoTechCvo.RgstrIsnReqCvo req) {
        try {
           infoTechSvc.oprRgstrIsn(customeModelMapper.mapping(req ,  InfoTechSvo.RgstrIsnInSvo.class));
           return ResData.SUCCESS(true, " 등기부등본 발급 성공");
        } catch (Exception e) {
            log.error(" 등기부등본 발급가능 여부 판단 조회중 오류발생: " + e.getMessage());
            return ResData.FAIL(e.getMessage());
        }
    }




}
