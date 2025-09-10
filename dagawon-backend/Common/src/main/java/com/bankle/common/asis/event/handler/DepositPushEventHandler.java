//package kr.co.anbu.event.handler;
//
//import kr.co.anbu.component.FCMSendPushService;
//import kr.co.anbu.component.directSend.DirectSendSmsService;
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.entity.MessageTemplate;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.MessageTemplateService;
//import kr.co.anbu.event.DepositPushEvent;
//import kr.co.anbu.utils.DateCustUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.MessageFormat;
//import java.util.HashMap;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class  DepositPushEventHandler {
//
//    private final DirectSendSmsService directSendSmsService;
//    private final FCMSendPushService fcmSendPushService;
//    private final MessageTemplateService messageTemplateService;
//    private final MemberService memberService;
//
//    @EventListener
//    @Transactional
//    public void sendRequestDepositSms(DepositPushEvent event) throws Exception {
//
//        ContractEscrow escrow = event.getEscrow();
//        String beforeDay = event.getBeforeDay();
//
//        String prdtTpc = escrow.getPrdtTpc();
//        String escrDtlPgc = escrow.getEscrDtlPgc();
//
//        String depositTxt = getDepositTxt(prdtTpc, escrDtlPgc);
//        if(StringCustUtils.isEmpty(depositTxt)){
//            throw new RuntimeException("잘못된 요청입니다.");
//        }
//
//        String message = getSmsMsg(prdtTpc, escrDtlPgc, beforeDay);
//
//        if(message != null){
//            HashMap<String, String> params = new HashMap<>();
//            params.put("title", "");
//            params.put("message", message);
//            params.put("receiver",memberService.getReceiver(escrow));
//
//            directSendSmsService.sendSms(params);
//        }
//    }
//
//    @EventListener
//    @Transactional
//    public void sendRequestDepositPush(DepositPushEvent event) throws Exception{
//        ContractEscrow escrow = event.getEscrow();
//        String beforeDay = event.getBeforeDay();
//
//        String prdtTpc = escrow.getPrdtTpc();
//        String escrDtlPgc = escrow.getEscrDtlPgc();
//
//        String depositTxt = getDepositTxt(prdtTpc, escrDtlPgc);
//        if(StringCustUtils.isEmpty(depositTxt)){
//            throw new RuntimeException("잘못된 요청입니다.");
//        }
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("escrMKey", String.valueOf(escrow.getEscrMKey()));
//        params.put("title", "");
//        params.put("notiDsc","");
//        params.put("body", getPushMsg(prdtTpc, escrDtlPgc, beforeDay));
//
//        params = memberService.getDevicdInfo(escrow.getMembId(), params);
//
//        fcmSendPushService.sendPush(params);
//    }
//
//    private String getPattern(Integer id, String kind){
//        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(id);
//        if(StringCustUtils.equals(kind,"PUSH"))
//            return messageTemplate.getPushMsg();
//        else
//            return messageTemplate.getKakaoMsg();
//    }
//
//    private String getSmsMsg(String prdtTpc, String escrDtlPgc, String beforeDay){
//        if(StringCustUtils.equals(beforeDay,"0")){
//            String smsPattern = getPattern(21001, "SMS");
//            String[] args = {getDepositTxt(prdtTpc, escrDtlPgc)};
//            return MessageFormat.format(smsPattern, args);
//        }else{
//            log.info("메시지가 없습니다.");
//            return null;
//        }
//    }
//
//    private String getPushMsg(String prdtTpc, String escrDtlPgc, String beforeDay){
//        int i = Integer.parseInt(beforeDay);
//        String depositTxt = getDepositTxt(prdtTpc, escrDtlPgc);
//
//        int id;
//        if(StringCustUtils.equals(beforeDay,"0")){
//            id = 21001;
//        }else {
//            if (i > 0) {
//                id = 21000;
//            } else {
//                id = 23000;
//            }
//        }
//
//        String pushPattern = getPattern(id, "PUSH");
//        String[] args = {depositTxt, String.valueOf(Math.abs(Integer.parseInt(beforeDay))), DateCustUtils.getThisDate("yyyy년 MM월 dd일")};
//
//        return MessageFormat.format(pushPattern, args);
//    }
//
//    /**
//     * 상품구분, 에스크로진행상세코드에 따른 대금코드 조회
//     * @param prdtTpc
//     * @param escrDtlPgc
//     * @return
//     */
//    private String getDepositTxt(String prdtTpc, String escrDtlPgc){
//        //입금요청
//        if(StringCustUtils.equals(escrDtlPgc,prdtTpc+"1")){
//            return "계약금";
//        }else if(StringCustUtils.equals(escrDtlPgc, ((Integer.parseInt(prdtTpc)+1)+"1"))){
//            return "중도금";
//        }else if(StringCustUtils.equals(escrDtlPgc, ((Integer.parseInt(prdtTpc)+2)+"1"))){
//            return "잔금";
//        }else{
//            return "";
//        }
//    }
//}
