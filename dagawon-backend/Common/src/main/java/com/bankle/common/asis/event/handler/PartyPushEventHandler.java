//package kr.co.anbu.event.handler;
//
//import kr.co.anbu.component.FCMSendPushService;
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.entity.ContractEscrowParty;
//import kr.co.anbu.domain.entity.MessageTemplate;
//import kr.co.anbu.domain.service.ContractEscrowService;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.MessageTemplateService;
//import kr.co.anbu.event.ContractEscrowPartyPushEvent;
//import kr.co.anbu.utils.CommonUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.text.MessageFormat;
//import java.util.HashMap;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class PartyPushEventHandler {
//
//    private final FCMSendPushService fcmSendPushService;
//    private final ContractEscrowService contractEscrowService;
//    private final MemberService memberService;
//
//    private final MessageTemplateService messageTemplateService;
//
//    /**
//     * 이해관계자등록 푸쉬 전송
//     * @param event
//     * @throws Exception
//     */
//    @EventListener
//    public void sendConsentEventPush(ContractEscrowPartyPushEvent event) throws Exception {
//
//        ContractEscrowParty party = event.getParty();
//        String prdtDsc = event.getPrdtDsc();
//        //계약정보 조회
//        ContractEscrow escrow = contractEscrowService.getContractEscrow(party.getEscrMKey());
//        //메시지템플릿 조회
//        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(event.getId());
//        String pushMsg = messageTemplate.getPushMsg();
//        if(StringCustUtils.isEmpty(pushMsg))
//            throw new RuntimeException("메시지 템플릿이 없습니다.");
//
//        //푸시전송 데이터 세팅
//        HashMap<String, String> params = new HashMap<>();
//        params.put("escrMKey", String.valueOf(escrow.getEscrMKey()));
//        params.put("title", messageTemplate.getTrnsMmt());
//        params.put("notiDsc","");       //공지사항 구분코드 1: 공지사항, 2: FAQ
//
//        String[] args = {CommonUtils.getPrtyDscValue(party.getPrtyDsc(), prdtDsc),party.getPrtyNm()};
//        params.put("body", MessageFormat.format(pushMsg, args));
//
//        //디바이스정보 세팅
//        params = memberService.getDevicdInfo(escrow.getMembId(), params);
//
//        fcmSendPushService.sendPush(params);
//    }
//}
