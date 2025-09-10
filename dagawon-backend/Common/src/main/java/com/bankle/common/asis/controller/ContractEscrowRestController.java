//package kr.co.anbu.controller;
//
//import java.util.HashMap;
//
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.entity.Members;
//import kr.co.anbu.domain.mapper.AccountMapper;
//import kr.co.anbu.domain.mapper.IfTgInfoMapper;
//import kr.co.anbu.domain.service.ContractEscrowDetailService;
//import kr.co.anbu.domain.service.ContractEscrowPaidService;
//import kr.co.anbu.domain.service.ContractEscrowPartyService;
//import kr.co.anbu.domain.service.ContractEscrowService;
//import kr.co.anbu.domain.service.FeeRomDetailService;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.infra.ContractEscrowInfoDto;
//import kr.co.anbu.infra.Response;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/escrow")
//public class ContractEscrowRestController {
//
//    private final ContractEscrowService contractEscrowService;
//    private final ContractEscrowPartyService contractEscrowPartyService;
//    private final MemberService memberService;
//    private final ContractEscrowDetailService contractEscrowDetailService;
//    private final ContractEscrowPaidService paidService;
//    private final FeeRomDetailService feeRomDetailService;
//    private final ApplicationEventPublisher publisher;
//    private final Response response;
//    private final AccountMapper accountMapper;
//    private final IfTgInfoMapper ifTgInfoMapper;
//    /**
//     * 홈화면 카드조회
//     * @param udid
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/cards/{udid}")
//    public ResponseEntity<?> cards(@PathVariable("udid") String udid) throws Exception{
//
//        Members memberByUdid = memberService.getMemberByUdid(udid);
//        if(memberByUdid == null)
//            return response.fail("회원이 아닙니다.", HttpStatus.BAD_REQUEST);
//
//        String membId = memberByUdid.getMembId();
//        if(StringCustUtils.isEmpty(membId))
//            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
//
//        //카드정보 조회
//        return response.success(contractEscrowService.getCards(membId), "success", HttpStatus.OK);
//    }
//
//    /**
//     * 거래 에스크로 신규 등록
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/new")
//    public ResponseEntity<?> newContract(@RequestBody HashMap<String, Object> body) throws Exception{
//        ContractEscrow escrow;
//        try {
//            //거래 에스크로 등록
//            escrow = contractEscrowService.newContract(body);
//            if(escrow == null)
//                return response.fail("fail", HttpStatus.BAD_REQUEST);
//
//            //카드 조회
//            return response.success(getContractEscrow(escrow, escrow.getEscrMKey()),"success", HttpStatus.OK);
//
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 계약정보 수정
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/update/{escrMKey}")
//    public ResponseEntity<?> update(@PathVariable Long escrMKey,
//                                    @RequestBody HashMap<String, Object> body) throws Exception{
//
//        if(escrMKey == null)
//            return response.fail("잘못된 요청입니다." , HttpStatus.BAD_REQUEST);
//
//        try {
//            ContractEscrow update = contractEscrowService.update(escrMKey, body);
//
//            if(update == null)
//                return response.fail("fail", HttpStatus.BAD_REQUEST);
//
////            ContractEscrow contractEscrow = getContractEscrow(update, escrMKey);
////            if(contractEscrow.getContractEscrowDetails().size() != 0){
////                //거래 에스크로 상세
////                contractEscrowDetailService.updateEscrowDetails(escrMKey,
////                        contractEscrow.getContractEscrowDetails(),
////                        (List<HashMap<String, Object>>) body.get("detailDto"));
////            }
//
//            return response.success(getContractEscrow(update, escrMKey), "success", HttpStatus.OK);
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 지급계좌정보 설정
//     * @param body
//     * @return
//     */
//    @PostMapping("/acct-no")
//    public ResponseEntity<?> setPmntAcctNo(@RequestBody HashMap<String, String> body) throws Exception{
//        try {
//            if (contractEscrowService.setPmntAcctNo(body)) {
//                return response.success(contractEscrowPartyService.getParty(Long.valueOf(body.get("escrMKey"))),
//                        "success", HttpStatus.OK);
//            } else {
//                return response.fail("fail", HttpStatus.BAD_REQUEST);
//            }
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 에스크로 내역상세
//     * @param escrMKey
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/report/{escrMKey}")
//    public ResponseEntity<?> report(@PathVariable Long escrMKey) throws Exception{
//
//        if(escrMKey == null)
//            return response.fail("잘못된 요청입니다." , HttpStatus.BAD_REQUEST);
//
//        return response.success(contractEscrowService.getContractEscrowReport(escrMKey),
//                "success",HttpStatus.OK);
//    }
//
//    /**
//     * 최종지급승인
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/approval")
//    public ResponseEntity<?> approval(@RequestBody HashMap<String, String> body) {
//
//        log.error("===============   approval      ===============");
//        log.error(body.toString());
//        log.error("===============   approval      ===============");
//        try {
//
//            for(String key: body.keySet()) {
//                log.error("=====>> ESCR approval body  key [" + key + "]   value ["  + body.get(key) + "]");
//            }
//
//
//            HashMap<String, String> paramMap = new HashMap<>();
//            paramMap.put("escr_m_key", body.get("escrMKey"));
//
//            paidService.approval(paramMap);
//
//            return response.success("지급요청완료", "success", HttpStatus.OK);
//
//        } catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 확정일자 저장
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/fixed")
//    public ResponseEntity<?> fixed(@RequestBody HashMap<String, Object> body) throws Exception{
//
//        try {
//            return contractEscrowService.fixed(body)
//                    ? response.success("확정일자 저장", "success", HttpStatus.OK)
//                    : response.fail("fail", HttpStatus.BAD_REQUEST);
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 계약정보상세 조회
//     * @param escrMKey
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/{escrMKey}")
//    public ResponseEntity<?> details(@PathVariable Long escrMKey) throws Exception{
//
//        if(escrMKey == null)
//            return response.fail("잘못된 요청입니다." , HttpStatus.BAD_REQUEST);
//
//        ContractEscrowInfoDto dto = contractEscrowService.getDetails(escrMKey);
//
//        if(dto == null)
//            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
//
//        //수수료결제내역조회
//        HashMap<String, String> feeAmtInfo = feeRomDetailService.getFeeDetail(escrMKey);
//        if(feeAmtInfo != null){
//            dto.setCrdDscTxt(feeAmtInfo.get("crdDscTxt"));
//            dto.setFeeStmtDscTxt(feeAmtInfo.get("feeStmtDscTxt"));
//            dto.setFeeVrAcctNo(feeAmtInfo.get("feeVrAcctNo"));
//            dto.setFeeRomBnkCdTxt(feeAmtInfo.get("feeRomBnkCdTxt"));
//            dto.setFeeRomAcctNo(feeAmtInfo.get("feeRomAcctNo"));
//            dto.setFeeRommnNm(feeAmtInfo.get("feeRommnNm"));
//            dto.setFeeAmt(Long.valueOf(feeAmtInfo.get("stmtAmt")));
//            dto.setFeeDt(feeAmtInfo.get("stmtDt"));
//        }
//        return response.success(dto,"success", HttpStatus.OK);
//    }
//
//    /**
//     * 에스크로 해지 정보 조회
//     * @param escrMKey
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/cancel-info")
//    public ResponseEntity<?> getCancel(@RequestBody HashMap<String, Object> body) {
//
//        try {
//            return response.success(contractEscrowService.getCancelInfo(body), "success", HttpStatus.OK);
//        } catch (Exception Ex){
//            Ex.printStackTrace();
//            return response.fail(Ex.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 에스크로 해지
//     * @return
//     */
//    @PostMapping("/cancel")
//    public ResponseEntity<?> cancel(@RequestBody HashMap<String, String> body){
//
//        try {
//            //에스크로 해지 정보 저장
//            HashMap<String, Object> paramMap = new HashMap<String, Object>();
//            paramMap.put("ESCR_M_KEY"  , body.get("escrMKey"  ));
//            paramMap.put("escr_m_key"  , body.get("escrMKey"  ));
//            paramMap.put("PMNT_BNK_CD" , body.get("pmntBnkCd" ));
//            paramMap.put("PMNT_ACCT_NO", body.get("pmntAcctNo"));
//
//
//            HashMap<String, Object> saveMap = contractEscrowService.cancel(paramMap);
//
//            String res_cd = (String)saveMap.get("RES_CD");
//
//            return response.success(saveMap, "success", HttpStatus.OK);
//
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 수수료 입금전 해약
//     * @param escrMKey
//     * @return
//     */
////    @GetMapping("/delete/{escrMKey}")
////    public ResponseEntity<?> delete(@PathVariable Long escrMKey){
//    @PostMapping("/delete")
//    public ResponseEntity<?> delete(@RequestBody HashMap<String, String> body){
//
//        try{
//            Long escrMKey = Long.parseLong(body.get("escrMKey").toString());
//            return response.success(contractEscrowService.delete(escrMKey),"success", HttpStatus.OK);
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 이해관계자 인원수 저장
//     * @param body
//     * @return
//     */
//    @PostMapping("/set-party-num")
//    public ResponseEntity<?> setPartyNums(@RequestBody HashMap<String, String> body) {
//        try {
//            contractEscrowService.setPartyNums(body);
//            return response.success("success","success", HttpStatus.OK);
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    private ContractEscrow getContractEscrow(ContractEscrow update, Long escrMKey){
//        update.setContractEscrowParties(contractEscrowPartyService.getParties(escrMKey));
//        update.setContractEscrowDetails(contractEscrowDetailService.getDetails(escrMKey));
//        return update;
//    }
//
//    /**
//     * 거래등록완료
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/regFn")
//    public ResponseEntity<?> regFn(@RequestBody HashMap<String, Object> body) throws Exception{
//
//        HashMap<String, Object> resMap = new HashMap<>();
//
//        try {
//            //거래 에스크로 등록
//            resMap.put("RES_CD" , "0000"   );
//            resMap.put("RES_MSG", "SUCCESS");
//
//            body.put("escr_m_key", body.get("ESCR_M_KEY"));
//
//            int dbCnt = accountMapper.updateEscrPmntAcctInfo(body);
//                dbCnt = ifTgInfoMapper.insertBackupEscrM(body);
//
//            //카드 조회
//            return response.success(resMap,"success", HttpStatus.OK);
//
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
