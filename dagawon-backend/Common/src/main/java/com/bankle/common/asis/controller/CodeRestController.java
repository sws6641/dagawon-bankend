//package com.bankle.common.asis.controller;
//
//import com.bankle.common.asis.domain.entity.CodeMaster;
//import com.bankle.common.asis.domain.service.CodeService;
//import com.bankle.common.asis.domain.service.CodeServiceWithRedis;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/codes")
//public class CodeRestController {
//
//    private final CodeServiceWithRedis codeServiceWithRedis;
//
//    private final CodeService codeService;
//    private final Response response;
//
//    /**
//     * 전체코드 조회
//     * @return
//     */
//    @GetMapping("")
//    public ResponseEntity<?> getCodes(){
//        List<CodeMaster> codes = codeService.getCodes();
//
//        return response.success(codes, "OK", HttpStatus.OK);
//    }
//
//    /**
//     * 해당 그룹코드에 해당하는 코드 조회
//     * @param grpCd
//     * @return
//     */
//    @GetMapping("/{grpCd}")
//    public ResponseEntity<?> getCode(@PathVariable String grpCd){
//        CodeMaster byGrpCd = codeService.findByGrpCd(grpCd);
//
//        return response.success(byGrpCd, "OK", HttpStatus.OK);
//    }
//
//    /**
//     * Redis에 저장
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/redis/save")
//    public boolean saveToRedis() throws Exception{
//        codeServiceWithRedis.saveToRedis();
//
//        return true;
//    }
//
//    /**
//     * Redis에서 코드 조회
//     * @param grpCd
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/redis/{grpCd}")
//    public @ResponseBody Map<String, String> getCodeFromRedis(@PathVariable("grpCd") String grpCd) throws Exception{
//        return codeServiceWithRedis.getCodeFromRedis(grpCd);
//    }
//}
