//package kr.co.anbu.controller;
//
//import kr.co.anbu.component.coocon.CooconService;
//import kr.co.anbu.infra.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/real")
//public class RealNameAccountRestController {
//
//    private final Response response;
//    private final CooconService cooconService;
//
//    @PostMapping("/account-no")
//    public ResponseEntity<?> realName(@RequestBody HashMap<String, Object> body,
//                                      HttpServletResponse httpServletResponse){
//        try{
//            HashMap<String, Object> result = cooconService.checkAccount(body, httpServletResponse);
//
//            return response.success(result,"success", HttpStatus.OK);
//        }catch (Exception e){
//
//            log.error("======================================================================");
//            log.error("쿠콘 계좌 실명 조회 Exception");
//            log.error("======================================================================");
//            e.printStackTrace();
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
