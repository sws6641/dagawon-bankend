//package kr.co.anbu.controller;
//
//import kr.co.anbu.domain.entity.CodeDetail;
//import kr.co.anbu.domain.service.CodeService;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.infra.Response;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicReference;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/app")
//public class AppRestController {
//
//    private final CodeService codeService;
//    private final MemberService memberService;
//
//    private final Response response;
//
//    /**
//     * 기종별 현재 최종 앱버전 조회
//     *
//     * @param os
//     * @return
//     */
//    @GetMapping("/version/{os}")
//    public ResponseEntity<?> version(@PathVariable String os) {
//
//        List<CodeDetail> codeDetails = codeService.findByGrpCd("APP_FNL_VER").getCodeDetails();
//
//        AtomicReference<CodeDetail> detail = new AtomicReference<>(new CodeDetail());
//        codeDetails.stream().filter(d -> StringCustUtils.equals(d.getCodeDetailId().getCmnCd(), os)
//                        && StringCustUtils.equals(d.getUseYn(), "Y"))
//                .findFirst()
//                .ifPresent(detail::set);
//
//        return response.success(detail.get(), "Success", HttpStatus.OK);
//    }
//
//    /**
//     * 앱설정정보 저장
//     *
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/save")
//    public ResponseEntity<?> save(@RequestBody HashMap<String, String> body) {
//        try {
//            return response.success(memberService.setAppInfo(body), "success", HttpStatus.OK);
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 앱설정정보 조회
//     * @param udid
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/{udid}")
//    public ResponseEntity<?> info(@PathVariable String udid) throws Exception {
//
//        if(StringCustUtils.isEmpty(udid))
//            return response.fail("udid is null", HttpStatus.BAD_REQUEST);
//
//        try {
//            return response.success(memberService.getAppInfo(udid), "success", HttpStatus.OK);
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 시스템일자
//     *
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/sysdate")
//    public ResponseEntity<?> sysdate() {
//
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("systemDt", StringCustUtils.getDate(""));
//
//        try {
//            return response.success(paramMap, "success", HttpStatus.OK);
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
