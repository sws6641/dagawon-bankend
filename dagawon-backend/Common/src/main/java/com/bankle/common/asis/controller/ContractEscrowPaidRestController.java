//package kr.co.anbu.controller;
//
//import kr.co.anbu.domain.service.ContractEscrowPaidService;
//import kr.co.anbu.infra.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/paid")
//public class ContractEscrowPaidRestController {
//
//    private final ContractEscrowPaidService contractEscrowPaidService;
//    private final ContractEscrowPaidService paidService;
//    private final Response response;
//
//    /**
//     * 지급내역 승인
//     * @param body
//     * @return
//     */
//    @PostMapping("/approval")
//    public ResponseEntity<?> approval(@RequestBody HashMap<String, String> body){
//
//
//        log.debug("=====>> /api/paid/approval Call  escrMKey [" + body.get("escrMKey") + "]");
//
//        try {
//
//            HashMap<String, String> paramMap = new HashMap<>();
//            paramMap.put("escr_m_key", body.get("escrMKey"));
//
//            paidService.approval(paramMap);
//
//            log.debug("=====>> /api/paid/approval SUCCESS");
//            return response.success("지급완료", "success", HttpStatus.OK);
//
//        } catch (Exception e){
//            e.printStackTrace();
//            log.debug("=====>> /api/paid/approval Fail  [" + e.getMessage() + "]");
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
