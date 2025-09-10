//package kr.co.anbu.controller;
//
//import kr.co.anbu.domain.service.ContractEscrowPartyService;
//import kr.co.anbu.infra.ContractPartiesDto;
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
//@RequestMapping("/api/parties")
//public class ContractEscrowPartiesRestController {
//
//    private final Response response;
//    private final ContractEscrowPartyService contractEscrowPartyService;
//
//    /**
//     * 이해관계자 전체 조회
//     * @param escrMKey
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/all/{escrMKey}")
//    public ResponseEntity<?> party(@PathVariable Long escrMKey) throws Exception{
//
//        if(escrMKey == null)
//            return response.fail("잘못된 요청입니다." , HttpStatus.BAD_REQUEST);
//
//        ContractPartiesDto party = contractEscrowPartyService.getParty(escrMKey);
//
//        return (party == null)
//                ? response.fail("fail", HttpStatus.BAD_REQUEST)
//                : response.success(party, "success", HttpStatus.OK);
//    }
//
//
//}
