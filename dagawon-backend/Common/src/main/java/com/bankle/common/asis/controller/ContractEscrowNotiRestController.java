//package kr.co.anbu.controller;
//
//import kr.co.anbu.domain.service.ContractEscrowNotiService;
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
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/noti")
//public class ContractEscrowNotiRestController {
//
//    private final ContractEscrowNotiService contractEscrowNotiService;
//
//    private final Response response;
//
//    /**
//     * 알림내역 조회
//     * @param escrMKey
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/{escrMKey}")
//    public ResponseEntity<?> getNoties(@PathVariable Long escrMKey) throws Exception{
//
//        if(escrMKey == null)
//            return response.fail("잘못된 요청 입니다.", HttpStatus.BAD_REQUEST);
//
//        try {
//            return response.success(contractEscrowNotiService.getAllNoti(escrMKey),
//                    "success", HttpStatus.OK);
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
