//package kr.co.anbu.controller;
//
//import kr.co.anbu.domain.entity.RgstrIcdnt;
//import kr.co.anbu.domain.service.FaResearchResultService;
//import kr.co.anbu.domain.service.RgstIncdntService;
//import kr.co.anbu.infra.FaResearchResultDto;
//import kr.co.anbu.infra.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/fa/result")
//@Slf4j
//public class FaResearchResultRestController {
//
//    private final Response response;
//
//    private final FaResearchResultService faResearchResultService;
//    private final RgstIncdntService rgstIncdntService;
//
//    /**
//     * FA 결과(보험형)
//     * @param escrMKey
//     * @return
//     */
//    @GetMapping("/insure/{escrMKey}")
//    public ResponseEntity<?> insure(@PathVariable Long escrMKey){
//
//        if(escrMKey == null)
//            return response.fail("fail", HttpStatus.BAD_REQUEST);
//
//        try {
//            List<FaResearchResultDto> byEscrMKey = faResearchResultService.findByEscrMKey(escrMKey);
//            return response.success(byEscrMKey, "success", HttpStatus.OK);
//
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/basic/{escrMKey}")
//    public ResponseEntity<?> basic(@PathVariable Long escrMKey){
//
//        if(escrMKey == null)
//            return response.fail("fail", HttpStatus.BAD_REQUEST);
//
//        try{
//            List<RgstrIcdnt> byEscrMKey = rgstIncdntService.findByEscrMKey(escrMKey);
//            return response.success(byEscrMKey, "success", HttpStatus.OK);
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//    }
//}
