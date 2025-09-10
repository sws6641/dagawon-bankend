//package kr.co.anbu.event.handler;
//
//import kr.co.anbu.component.FCMSendPushService;
//import kr.co.anbu.component.directSend.DirectSendSmsService;
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.entity.ContractEscrowParty;
//import kr.co.anbu.domain.entity.MessageTemplate;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.MessageTemplateService;
//import kr.co.anbu.event.ContractEscrowEvent;
//import kr.co.anbu.utils.CommonUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class ContractEventHandler {
//
//    private final MemberService memberService;
//    private final FCMSendPushService fcmSendPushService;
//    private final DirectSendSmsService directSendSmsService;
//    private final MessageTemplateService messageTemplateService;
//
//    /**
//     * 계약진행 이벤트 푸시 전송
//     * @param event
//     * @throws Exception
//     */
//    @EventListener
//    @Transactional
//    public void sendContractEventPush(ContractEscrowEvent event) {
//
//        try {
//            ContractEscrow escrow = event.getEscrow();
//            String escrDtlPgc = escrow.getEscrDtlPgc();     //에스크로 상세진행코드
//
//            HashMap<String, String> params = new HashMap<>();
//            HashMap<String, String> pushMessage = getPushMessage(String.valueOf(getMsgPrttIKey(escrDtlPgc)));
//            params.put("escrMKey", String.valueOf(escrow.getEscrMKey()));
//            params.put("title", pushMessage.get("title"));
//            params.put("notiDsc","");
//            params.put("body", pushMessage.get("body"));
//
//            params = memberService.getDevicdInfo(escrow.getMembId(), params);
//
//            fcmSendPushService.sendPush(params);
//        } catch (Exception Ex) {
//            log.error("====================================================================================");
//            log.error("계약진행 이벤트 푸시 전송 sendContractEventPush");
//            log.error("====================================================================================");
//            Ex.printStackTrace();
//        }
//    }
//
//    /**
//     * 계약진행 이벤트 SMS 전송
//     * @param event
//     * @throws Exception
//     */
//    @EventListener
//    @Transactional
//    public void sendContractEventSms(ContractEscrowEvent event) {
//
//        try {
//            ContractEscrow escrow = event.getEscrow();
//            String escrDtlPgc = escrow.getEscrDtlPgc();     //에스크로 상세진행코드
//
//            HashMap<String, String> params = new HashMap<>();
//            HashMap<String, String> smsMessage = getSmsMessage(escrDtlPgc, escrow);
//            params.put("title", smsMessage.get("title"));
//            params.put("message", smsMessage.get("message"));
//            params.put("receiver",getReceiver(escrDtlPgc, escrow));
//
//            directSendSmsService.sendSms(params);
//        } catch (Exception Ex) {
//            log.error("====================================================================================");
//            log.error("계약진행 이벤트 SMS 전송 sendContractEventSms");
//            log.error("====================================================================================");
//            Ex.printStackTrace();
//        }
//    }
//
//    /**
//     * 푸시 메시지 템플릿 조회
//     * @param escrDtlPgc
//     * @return
//     */
//    private HashMap<String, String> getPushMessage(String escrDtlPgc){
//
//        HashMap<String, String> pushMsg = new HashMap<>();
//        MessageTemplate messageTemplate = getMessageTemplate(escrDtlPgc);
//        pushMsg.put("title", messageTemplate.getTrnsMmt());
//        pushMsg.put("body", messageTemplate.getPushMsg());
//        return pushMsg;
//    }
//
//    /**
//     * SMS 템플릿 조회
//     * @param escrDtlPgc
//     * @param escrow
//     * @return
//     * @throws Exception
//     */
//    private HashMap<String, String> getSmsMessage(String escrDtlPgc, ContractEscrow escrow) throws Exception {
//
//        String message;
//        HashMap<String, String> smsMsg = new HashMap<>();
//
//        int msgPrttIKey = getMsgPrttIKey(escrDtlPgc);
//        MessageTemplate messageTemplate = getMessageTemplate(String.valueOf(msgPrttIKey));
//
//        if(messageTemplate == null)
//            throw new RuntimeException("메시지가 없습니다.");
//
//        message = messageTemplate.getKakaoMsg();
//        if(StringCustUtils.isEmpty(message))
//            throw new RuntimeException("메시지가 없습니다.");
//
//        //에스크로 해지이고, 에스크로 잔고 금액 != 0
//        if(msgPrttIKey == 80000 && !Objects.equals(escrow.getEscrBlncAmt(), 0L)){
//            messageTemplate = messageTemplateService.getMessageTemplate(80001);
//            String kakaoMsg = messageTemplate.getKakaoMsg();
//
//            String[] args = {
//                    CommonUtils.getCmnNm("BNK_CD", escrow.getPmntBnkCd()) + escrow.getPmntAcctNo(),
//                    memberService.getMembNm(escrow.getMembId()),
//                    StringCustUtils.toDecimalFormat(escrow.getEscrBlncAmt())
//            };
//
//            message = MessageFormat.format(kakaoMsg, args);
//        }
//
//        smsMsg.put("title", messageTemplate.getTrnsMmt());
//        smsMsg.put("message", message);
//
//        return smsMsg;
//    }
//
//    /**
//     * 수시자 조회
//     * @param escrDtlPgc
//     * @param escrow
//     * @return
//     * @throws Exception
//     */
//    @Transactional
//    public String getReceiver(String escrDtlPgc, ContractEscrow escrow) throws Exception {
//
//        int msgPrttIKey = getMsgPrttIKey(escrDtlPgc);
//
//        if(msgPrttIKey == 2000){    //매도인, 매수인/ 임대인, 임차인 전체 전송
//            List<ContractEscrowParty> contractEscrowParties = escrow.getContractEscrowParties();
//            if(contractEscrowParties.size() == 0)
//                throw new RuntimeException("잘못된 요청입니다.");
//
//            List<String> list = new ArrayList<>();
//            contractEscrowParties.forEach(p -> {
//                String sb = "{\"name\": \"" + p.getPrtyNm() + "\" ,\"mobile\": \"" + p.getPrtyHpNo() + "\" ," +
//                        " \"note1\": \"\", \"noti2\": \"\", \"noti3\": \"\", \"noti4\": \"\", \"noti5\": \"\"  }";
//                list.add(sb);
//            });
//
//            String join = String.join(",", list);
//
//            return "["+ join +"]";
//
//        }else{
//            return memberService.getReceiver(escrow);
//        }
//    }
//
//    private Integer getMsgPrttIKey(String escrDtlPgc){
//        escrDtlPgc = StringCustUtils.rightPad(escrDtlPgc, 5, "0");
//
//        if(escrDtlPgc.startsWith("0"))
//            escrDtlPgc = StringCustUtils.substringAfter(escrDtlPgc,"0");
//        else
//            escrDtlPgc = escrDtlPgc;
//
//        return Integer.parseInt(escrDtlPgc);
//    }
//
//    private MessageTemplate getMessageTemplate(String escrDtlPgc){
//        return messageTemplateService.getMessageTemplate(Integer.valueOf(escrDtlPgc));
//    }
//}
