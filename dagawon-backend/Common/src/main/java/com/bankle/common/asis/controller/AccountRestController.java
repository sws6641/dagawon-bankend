//package kr.co.anbu.controller;
//
//import java.text.MessageFormat;
//import java.util.HashMap;
//
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
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
//import kr.co.anbu.domain.mapper.ContractEscrowMapper;
//import kr.co.anbu.domain.mapper.IfTgInfoMapper;
//import kr.co.anbu.domain.service.ConsentService;
//import kr.co.anbu.domain.service.ContractEscrowPartyService;
//import kr.co.anbu.domain.service.ContractEscrowService;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.MessageTemplateService;
//import kr.co.anbu.infra.Response;
//import kr.co.anbu.utils.CommonUtils;
//import kr.co.anbu.utils.EscrSendMsgUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/account")
//public class AccountRestController {
//
//    private final MemberService memberService;
//    private final ContractEscrowService contractEscrowService;
//    private final ContractEscrowPartyService contractEscrowPartyService;
//    private final MessageTemplateService messageTemplateService;
//    private final ConsentService consentService;
//    private final ApplicationEventPublisher publisher;
//    private final AccountMapper accountMapper;
//
//    private final Response response;
//
//    private final IfTgInfoMapper       ifTgInfoMapper;
//    private final EscrSendMsgUtils     escrSendMsg;
//    private final ContractEscrowMapper escrMapper;
//    /**
//     * 회원가입
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/new")
//    public ResponseEntity<?> save(@RequestBody HashMap<String, Object> body) throws Exception{
//
//        StringCustUtils.printMapInfo(body);
//
//        try{
//            //1. 회원정보 세팅 -> 저장
//
//            Members member = memberService.setMember(body);
//
//            //2. 약관동의 저장
//            consentService.saveEntrAsnt(member.getMembId(), body);
//
//            //3. 회원이력 테이블 저장
//            memberService.addHistory(member);
//
//            // 기존가입회원이 아닌경우 푸시 또는 SMS 전송 이벤트 발생
//            String existEntrYn = member.getExistEntrYn();
//
//            if ("N".equals(existEntrYn)) {
//
//                HashMap<String, Object> paramMap = new HashMap<>();
//
//                paramMap.put("memb_id"       , member.getMembId());
//                paramMap.put("msg_prtt_i_key", "10001"           );
//
//                HashMap<String, Object> selectMap = ifTgInfoMapper.selectSendMsgNoEscr(paramMap);
//
//                String memb_nm    = StringCustUtils.mapToString(selectMap, "MEMB_NM"  );
//                String fcm_id     = StringCustUtils.mapToString(selectMap, "FCM_ID"   );
//                String dvcKnd     = StringCustUtils.mapToString(selectMap, "DVC_KND"  );
//                String hp_no      = StringCustUtils.mapToString(selectMap, "HP_NO"    );
//                String trns_mmt   = StringCustUtils.mapToString(selectMap, "TRNS_MMT" );
//                String kakao_msg  = StringCustUtils.mapToString(selectMap, "KAKAO_MSG");
//                String push_msg   = StringCustUtils.mapToString(selectMap, "PUSH_MSG" );
//                String sendResult = "";
//
//                Object[] msgPatten = {memb_nm};
//                push_msg  = MessageFormat.format(push_msg , msgPatten);
//                kakao_msg = MessageFormat.format(kakao_msg, msgPatten);
//
//                if (!"".equals(push_msg)) {
//
//                    sendResult = escrSendMsg.sendPush("", fcm_id, dvcKnd, trns_mmt, push_msg, 10001);
//
//                    if ("fail".equals(sendResult)) { log.error("=====>> 회원가입 PUSH 문자 전송 실패"); }
//                }
//
//                // SMS 전송
//                if (!"".equals(kakao_msg)) {
//
//                    sendResult = escrSendMsg.sendSMS(memb_nm, hp_no, trns_mmt, kakao_msg);
//
//                    if ("fail".equals(sendResult)) { log.error("=====>> 회원가입 KAKAO 문자 전송 실패"); }
//                }
//            }
//
//            return response.success(member, "success", HttpStatus.OK);
//        }catch(Exception Ex){
//
//            log.error("===============================================");
//            log.error("=====>> Error Message [" + Ex.getMessage() + "]");
//            log.error("===============================================");
//            Ex.printStackTrace();
//            return response.fail(Ex.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 이해관계자 정보 및 아웃링크 전달
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/out-link")
//    @Transactional
//    public ResponseEntity<?> findMember(@RequestBody HashMap<String, String> body) throws Exception {
//
//        String escrMKey = body.get("escrMKey");
//        String prtyDsc  = body.get("prtyDsc" );
//
//        if(StringCustUtils.isEmpty(escrMKey))
//            return response.fail("잘못된 요청 입니다.", HttpStatus.BAD_REQUEST);
//
//        ContractEscrow escrow = contractEscrowService.getContractEscrow(Long.valueOf(escrMKey));
//        if(escrow == null)
//            return response.fail("잘못된 요청 입니다.", HttpStatus.BAD_REQUEST);
//
//        if(StringCustUtils.equals(escrow.getEscrDtlPgc(), "02")){
//            log.info("이미 동의가 완료되었습니다.");
//            return response.fail("Y","이미 동의가 완료되었습니다.", HttpStatus.BAD_REQUEST);
//        }else{
//            if(StringCustUtils.isEmpty(escrow.getMembId()))
//                return response.fail("잘못된 요청 입니다.", HttpStatus.BAD_REQUEST);
//
//            Members memberByMembId = memberService.getMemberByMembId(escrow.getMembId());
//            if(memberByMembId == null)
//                return response.fail("잘못된 요청 입니다.", HttpStatus.BAD_REQUEST);
//
//            //외부링크 조회 전달
//            HashMap<String, String> data = new HashMap();
//            data.put("escrMKey", String.valueOf(escrow.getEscrMKey()));
//            data.put("membNm", memberByMembId.getMembNm());
//            data.put("alnAddr1", escrow.getAlnAddr1());
//            data.put("alnAddr2", escrow.getAlnAddr2());
//
//            String env = StringCustUtils.nvl(System.getProperty("spring.profiles.active"));
//            if ("".equals(env)) { env = "dev";}
//
//            if ("prod".equals(env) || "dev1".equals(env)) {
//                data.put("outLink", "https://anbuapp.com/?escrMKey="+escrow.getEscrMKey()+"&prtyDsc="+prtyDsc);
//            } else {
//                data.put("outLink", "https://test.anbuapp.com/?escrMKey="+escrow.getEscrMKey()+"&prtyDsc="+prtyDsc);
//            }
//
//            // 동의요청 메시지 생성
//            // 1: 임차인/매수인, 2: 임대인/매도인
//            String smsMsg = (StringCustUtils.equals(prtyDsc, "1"))
//                    ? messageTemplateService.makeMessage(31002, memberByMembId.getMembNm(), body)   // 매수(임차)인
//                    : messageTemplateService.makeMessage(31001, memberByMembId.getMembNm(), body);  // 매도(임대)인
//
//            data.put("smsMsg" , smsMsg);
//
//            return response.success(data, "N", HttpStatus.OK);
//        }
//    }
//
//    /**
//     * 매수자 본인 외 이해관계자 등록
//     * @param escrMKey
//     * @param body
//     * @return
//     */
//    @PostMapping("/consent/{escrMKey}")
//    public ResponseEntity<?> saveConsent(@PathVariable Long escrMKey,
//                                         @RequestBody HashMap<String, String> body){
//        try {
//            return response.success(contractEscrowPartyService.saveOthers(escrMKey, body),
//                    "success", HttpStatus.OK);
//        }catch (Exception e){
//            return  response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 이해관계자 삭제
//     * @param escrPrtyDKey
//     * @return
//     */
//    @GetMapping("/delete/{escrPrtyDKey}")
//    public ResponseEntity<?> delete(@PathVariable Long escrPrtyDKey){
//        try {
//
//
//            HashMap<String, Object> selectMap = accountMapper.chkDelEscrPrty(escrPrtyDKey);
//
//            String escr_m_key   =         selectMap.get("ESCR_M_KEY"  ).toString();
//            String prdt_tpc     = (String)selectMap.get("PRDT_TPC"    );
//            String escr_dtl_pgc = (String)selectMap.get("ESCR_DTL_PGC");
//            String chk_valid    = (String)selectMap.get("CHK_VALID"   );
//
//
//            log.debug(  "=====>> escr_m_key   [" + escr_m_key    + "]"
//                      + "        prdt_tpc     [" + prdt_tpc      + "]"
//                      + "        escr_dtl_pgc [" + escr_dtl_pgc  + "]"
//                      + "        chk_valid    [" + chk_valid     + "]");
//
//            if ("Y".equals(chk_valid)) {
//                contractEscrowPartyService.delete(escrPrtyDKey);
//
//                HashMap<String, Object> paramMap = new HashMap<>();
//                paramMap.put("escr_m_key"     , escr_m_key     );
//                paramMap.put("ESCR_M_KEY"     , escr_m_key     );
//                paramMap.put("escr_prty_d_key", escrPrtyDKey+"");
//
//                int dbCnt = accountMapper.updateEscrDtlPgc(paramMap);
//
//                int chkValue = escrMapper.checkTrAsntFn(paramMap);
//
//                if (chkValue == 1) {
//                    escrSendMsg.sendMsg(escr_m_key, 31005, null, null);
//                }
//
//                return response.success("", "success", HttpStatus.OK);
//            } else {
//
//                String escr_dtl_pgc_nm = CommonUtils.getPrtyDscValue("ESCR_DTL_PGC_" + prdt_tpc, escr_dtl_pgc);
//                return response.fail("에스크로 [" + escr_dtl_pgc_nm + "] 단계에서는 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
//            }
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 가장 최신약관동의내용 조회
//     * @return
//     */
//    @GetMapping("/consent/content")
//    public ResponseEntity<?> getConsentContents() throws Exception {
//        try{
//            return response.success(consentService.getConsentContents(), "success", HttpStatus.OK);
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 가장 최신약관동의내용 조회
//     * @return
//     */
//    @GetMapping("/getPrtyCnt/{escr_m_key}")
//    public ResponseEntity<?> getPrtyCnt(@PathVariable String escr_m_key) throws Exception {
//        try{
//
//            return response.success(accountMapper.selectPrtyCnt(escr_m_key), "success", HttpStatus.OK);
//        }catch (Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
