//package com.bankle.common.asis.controller;
//
//import java.text.MessageFormat;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.bankle.common.asis.domain.entity.Members;
//import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
//import com.bankle.common.asis.domain.mapper.MemberMapper;
//import com.bankle.common.asis.domain.service.ContractEscrowService;
//import com.bankle.common.asis.domain.service.MemberService;
//import com.bankle.common.asis.infra.ContractStatusDto;
//import com.bankle.common.asis.infra.Response;
//import com.bankle.common.asis.infra.ResponseLoginDto;
//import com.bankle.common.asis.utils.EscrSendMsgUtils;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/members")
//public class MembersRestController {
//
//    private final Response response;
//
////    private final MembersServiceWithRedis membersServiceWithRedis;
//    private final MemberService memberService;
//    private final ContractEscrowService contractEscrowService;
//
//    private final ApplicationEventPublisher publisher;
//    private final MemberMapper memberMapper;
//    private final EscrSendMsgUtils escrSendMsg;
//    private final IfTgInfoMapper ifTgInfoMapper;
//    /**
//     * 로그인
//     * @param body request Json body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/sign-in")
//    public ResponseEntity<?> signIn(@RequestBody HashMap<String, String> body) throws Exception{
//
//        log.info("signIn ============================="+body.toString());
//
//        String udid = body.get("udid");
//        if(udid == null)
//            return response.fail("잘못된 요청입니다. ", HttpStatus.BAD_REQUEST);
//
//        Members memberByUdid = memberService.getMemberByUdid(udid);
//        if(memberByUdid == null)
//            return response.fail("회원이 아닙니다. 회원가입을 해주세요.", HttpStatus.BAD_REQUEST);
//
//        ResponseLoginDto byCredentials = membersServiceWithRedis.getByCredentials(body.get("udid"),
//                body.get("pwd"));
//
//        if(byCredentials == null){
//            return response.fail("비밀번호를 확인해 주세요.", HttpStatus.FORBIDDEN);
//        }else{
//            byCredentials = (AuthUtils.isValidUser(byCredentials.getMembId()))
//                    ? byCredentials
//                    : membersServiceWithRedis.refreshToken(byCredentials.getMembId());
//
//            return response.success(byCredentials, "success", HttpStatus.OK);
//        }
//    }
//
//    /**
//     * 회원정보조회
//     * @param udid 회원 핸드폰 번호
//     * @return
//     */
//    @GetMapping("/info/{udid}")
//    public ResponseEntity<?> info(@PathVariable String udid,
//                                  HttpServletRequest request){
//
//        log.debug("udid >>> "+udid);
//        if(kr.co.anbu.utils.StringCustUtils.isEmpty(udid))
//            return response.fail("fail", "udid값이 Null 입니다.", HttpStatus.BAD_REQUEST);
//
//        try {
//            //publisher.publishEvent(new TokenEvent(request));
//            Members member = memberService.getMemberByUdid(udid);
//            if(member == null){
//                return response.fail("fail", "사용자를 조회하지 못했습니다.", HttpStatus.BAD_REQUEST);
//            }
//            return response.success(member, "success", HttpStatus.OK);
//        }catch(Exception e){
//            e.printStackTrace();
//            return response.fail("expired", "expired", HttpStatus.OK);
//        }
//    }
//
//    /**
//     * 회원정보조회
//     * @param udid 회원 핸드폰 번호
//     * @return
//     */
//    @PostMapping("/info_ci")
//    public ResponseEntity<?> info_ci(@RequestBody HashMap<String, Object> bodyMap
//                                     , HttpServletRequest request){
//
//        String strCI = (String)bodyMap.get("ci");
//        log.debug("ci >>> "+strCI);
//
//        if(kr.co.anbu.utils.StringCustUtils.isEmpty(strCI))
//            return response.fail("fail", "CI 값이 Null 입니다.", HttpStatus.BAD_REQUEST);
//
//        try {
//            //publisher.publishEvent(new TokenEvent(request));
//
//            //Members member = memberService.getMemberByCi(strCI);
//            Members member = memberMapper.getMemberInfo(bodyMap);
//
//            if (member == null) {
//                member = new Members();
//                member.setCi(strCI);
//                member.setExistEntrYn("N");
//                return response.fail(member, "사용자를 조회하지 못했습니다.", HttpStatus.BAD_REQUEST);
//            } else {
//                member.setExistEntrYn("Y");
//            }
//
//            return response.success(member, "success", HttpStatus.OK);
//        }catch(Exception e){
//            e.printStackTrace();
//            return response.fail("expired", "expired", HttpStatus.OK);
//        }
//    }
//
//    /**
//     * 회원정보수정
//     * @param body request Json body
//     * @param request
//     * @return
//     */
//    @PostMapping("/save")
//    public ResponseEntity<?> save(@RequestBody HashMap<String, String> body,
//                                  HttpServletRequest request){
//
////        publisher.publishEvent(new TokenEvent(request));
//
//        if(body.get("udid") == null)
//            return response.fail("invalid", "fail", HttpStatus.BAD_REQUEST);
//
//        try {
//
//            Members member = memberService.getMemberByUdid(body.get("udid"));
//            member.setUdid(body.get("udid"));
//
//            if(body.get("hpNo") != null)
//                member.setHpNo(body.get("hpNo"));
//
//            if(body.get("membNm") != null)
//                member.setMembNm(body.get("membNm"));
//
//            if(body.get("telopCd") != null)
//                member.setTelopCd(body.get("telopCd"));
//
//            if(body.get("email") != null)
//                member.setEmail(body.get("email"));
//
//            if(body.get("mktNotiYn") != null)
//                member.setMktNotiYn(body.get("mktNotiYn"));
//
//            Members save = memberService.update(member);
//            memberService.addHistory(save);
//            return response.success(save, "success", HttpStatus.OK);
//
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 비밀번호 재설정
//     * @param body request Json body
//     * @return
//     */
//    @PostMapping("/pwd/reset")
//    public ResponseEntity<?> resetPwd(@RequestBody HashMap<String, String> body){
//        if(body.get("udid") == null)
//            return response.fail("udid cannot be null", HttpStatus.BAD_REQUEST);
//
//        try {
//            return response.success(memberService.resetPwd(body), "success", HttpStatus.OK);
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 회원전체조회
//     * @return
//     */
//    @GetMapping
//    public ResponseEntity<?> getAllMembers(){
//
//        Page<Members> members = memberService.getAllMembers(null, null, null);
//        for (Members member : members.getContent()) {
//            log.info(member.toString());
//        }
//
//        return response.success(members, "success", HttpStatus.OK);
//    }
//
//    /**
//     * 회원전체조회
//     * @param start 조회시작번호
//     * @param end   조회마지막번호
//     * @param sorting   sorting할 컬럼명
//     * @return
//     */
//    @GetMapping("/sorting")
//    public ResponseEntity<?> getAllMembersOrderBy(@RequestParam(name = "start", required = false) Integer start,
//                                           @RequestParam(name = "end", required = false) Integer end,
//                                           @RequestParam(name = "sorting", required = false) String sorting,
//                                           HttpServletRequest request){
//
//        publisher.publishEvent(new kr.co.anbu.event.TokenEvent(request));
//
//        Page<Members> members = memberService.getAllMembers(start, end, sorting);
//        for (Members member : members.getContent()) {
//            log.info(member.toString());
//        }
//
//        return response.success(members, "success", HttpStatus.OK);
//    }
//
//    /**
//     * 회원탈퇴
//     * @param udid  회원 핸드폰 번호
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/delete/{udid}")
//    public ResponseEntity<?> delete(@PathVariable("udid") String udid,
//                                    HttpServletRequest request) throws Exception{
//
//        //publisher.publishEvent(new TokenEvent(request));
//
//        boolean result;
//        try{
//
//            Members member = memberService.getMemberByUdid(udid);
//
//            if(member == null)
//                throw new RuntimeException("잘못된 요청 입니다.");
//
//            //진행중인 계약이 있는지 체크
//            List<ContractStatusDto> cards = contractEscrowService.getCards(member.getMembId());
//
//            if ( (cards != null) && (cards.size() > 0) ) {
//                for (ContractStatusDto card:cards) {
//
//                    if (!kr.co.anbu.utils.StringCustUtils.equals(card.getEscrPgc(), "9")) {
//
//                        log.warn("진행중인 계약이 있어서 탈퇴할 수 없습니다. ");
//                        throw new RuntimeException("진행중인 계약이 있어서 탈퇴할 수 없습니다. ");
//                    }
//                }
//            }
//
//            result = memberService.deleteMember(udid);
//
//            /*==============================================================*/
//            // 알림톡/PUSH 전송
//            HashMap<String, Object> paramMap = new HashMap<>();
//
//            paramMap.put("memb_id"       , member.getMembId());
//            paramMap.put("msg_prtt_i_key", "10002"           );
//
//            HashMap<String, Object> selectMap = ifTgInfoMapper.selectSendMsgNoEscr(paramMap);
//
//            String memb_nm    = kr.co.anbu.utils.StringCustUtils.mapToString(selectMap, "MEMB_NM"  );
//            String fcm_id     = kr.co.anbu.utils.StringCustUtils.mapToString(selectMap, "FCM_ID"   );
//            String dvcKnd     = kr.co.anbu.utils.StringCustUtils.mapToString(selectMap, "DVC_KND"  );
//            String hp_no      = kr.co.anbu.utils.StringCustUtils.mapToString(selectMap, "HP_NO"    );
//            String trns_mmt   = kr.co.anbu.utils.StringCustUtils.mapToString(selectMap, "TRNS_MMT" );
//            String kakao_msg  = kr.co.anbu.utils.StringCustUtils.mapToString(selectMap, "KAKAO_MSG");
//            String push_msg   = kr.co.anbu.utils.StringCustUtils.mapToString(selectMap, "PUSH_MSG" );
//            String sendResult = "";
//
//            Object[] msgPatten = {memb_nm};
//            push_msg  = MessageFormat.format(push_msg , msgPatten);
//            kakao_msg = MessageFormat.format(kakao_msg, msgPatten);
//
//            if (!"".equals(push_msg)) {
//
//                sendResult = escrSendMsg.sendPush("", fcm_id, dvcKnd, trns_mmt, push_msg, 10002);
//
//                if ("fail".equals(sendResult)) { log.error("=====>> 회원가입 PUSH 문자 전송 실패"); }
//            }
//
//            // SMS 전송
//            if (!"".equals(kakao_msg)) {
//
//                sendResult = escrSendMsg.sendSMS(memb_nm, hp_no, trns_mmt, kakao_msg);
//
//                if ("fail".equals(sendResult)) { log.error("=====>> 회원가입 KAKAO 문자 전송 실패"); }
//            }
//            /*==============================================================*/
//
//            return result ? response.success(member.getHpNo(), "success", HttpStatus.OK)
//                    : response.fail("fail", HttpStatus.BAD_REQUEST);
//        }catch(Exception e){
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
