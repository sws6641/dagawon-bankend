//package kr.co.anbu.controller;
//
//import kr.co.anbu.component.FCMSendPushService;
//import kr.co.anbu.component.directSend.DirectSendSmsService;
//import kr.co.anbu.component.directSend.DirectSendTemplateService;
//import kr.co.anbu.domain.entity.Members;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.infra.Response;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//
//@Slf4j
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class TestController {
//
//    private final DirectSendSmsService directSendSmsService;
//    private final MemberService memberService;
//    private final FCMSendPushService fcmService;
//    private final DirectSendTemplateService directSendTemplateService;
//
//    private final Response response;
//
//    @Value("${coocon.api.server}")
//    private String server;
//
//    @GetMapping("/sms/{hpNo}")
//    public ResponseEntity<?> sendSms(@PathVariable String hpNo) {
//        if (StringCustUtils.isEmpty(hpNo))
//            return response.fail("휴대폰 번호가 없습니다.", HttpStatus.BAD_REQUEST);
//
//        try {
//
//            Members memberByHpNo = memberService.getMemberByHpNo(hpNo);
//            String receiver = "{\"name\": \""+memberByHpNo.getMembNm()+"\", \"mobile\":\""+hpNo+"\", \"note1\":\"\", \"note2\":\"\", \"note3\":\"\", \"note4\":\"\", \"note5\":\"\"}";
//            receiver = "["+ receiver +"]";
//
//            HashMap<String, String> params = new HashMap<>();
//            params.put("title", "테스트");
//            params.put("message", "테스트 입니다.");
//            params.put("receiver",receiver);
//
//
//
//            return response.success(directSendSmsService.sendSms(params), "success", HttpStatus.OK);
//        } catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @PostMapping("/push")
//    public @ResponseBody ResponseEntity<?> pushSend(@RequestBody HashMap<String,String> body){
//
//        try {
//            String push = fcmService.sendPush(body);
//            return response.success(push,"success", HttpStatus.OK);
//        } catch (Exception e) {
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//
//        }
//    }
//
//    @GetMapping("/kakao/{templateType}")
//    public @ResponseBody ResponseEntity<?> getUserTemplateNo(@PathVariable String templateType) throws Exception {
//
//        return response.success(directSendTemplateService.getUserTemplateNo(templateType), "success", HttpStatus.OK);
//    }
//
//}
