//package kr.co.anbu.controller;
//
//import kr.co.anbu.domain.service.ContractEscrowRomService;
//import kr.co.anbu.infra.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/rom")
//public class ContractEscrowRomRestController {
//
//    private final ContractEscrowRomService contractEscrowRomService;
//    private final Response response;
//
//    /**
//     * 입금금액 조회
//     * @param escrMKey
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/all/{escrMKey}")
//    public ResponseEntity<?> all(@PathVariable Long escrMKey) throws Exception{
//
//        if(escrMKey == null)
//            return response.fail("잘못된 요청입니다.", HttpStatus.NO_CONTENT);
//
//        return response.success(contractEscrowRomService.getAllRom(escrMKey),
//                "success", HttpStatus.OK);
//    }
//}
