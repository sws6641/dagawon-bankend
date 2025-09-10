//package kr.co.anbu.event.handler;
//
//import kr.co.anbu.component.directSend.DirectSendSmsService;
//import kr.co.anbu.component.FCMSendPushService;
//import kr.co.anbu.domain.entity.Members;
//import kr.co.anbu.domain.entity.MessageTemplate;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.MessageTemplateService;
//import kr.co.anbu.event.DeletedEvent;
//import kr.co.anbu.event.RegisteredEvent;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.text.MessageFormat;
//import java.util.HashMap;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class MemberEventHandler {
//
//    private final DirectSendSmsService directSendSmsService;
//    private final FCMSendPushService fcmSendPushService;
//    private final MessageTemplateService messageTemplateService;
//    private final MemberService memberService;
//
//    /**
//     * 회원등록 푸시 전송
//     * @param event
//     * @throws IOException
//     */
//    @EventListener
//    public void sendRegisteredPush(RegisteredEvent event)  throws IOException {
//
//        Members member = event.getMember();
//
//        MessageTemplate messageTemplate = getMessageTemplate(100);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("title", messageTemplate.getTrnsMmt());
//        params.put("notiDsc","");
//        params.put("body", messageTemplate.getPushMsg());
//
//        params = memberService.getDevicdInfo(member.getMembId(), params);
//
//        fcmSendPushService.sendPush(params);
//    }
//
//    /**
//     * 회원등록 SMS 전송
//     * @param event
//     * @throws Exception
//     */
//    @EventListener
//    public void sendRegisteredSms(RegisteredEvent event) throws Exception {
//        Members member = event.getMember();
//        MessageTemplate messageTemplate = getMessageTemplate(100);
//        String[] args = {member.getMembNm()};
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("title", messageTemplate.getTrnsMmt());
//        params.put("message", MessageFormat.format(messageTemplate.getKakaoMsg(), args));
//        params.put("receiver",getReceiver(member));
//
//        directSendSmsService.sendSms(params);
//    }
//
//    /**
//     * 회원삭제 푸시 전송
//     * @param event
//     * @throws IOException
//     */
//    @EventListener
//    public void sendDeletedPush(DeletedEvent event) throws IOException{
//        Members member = event.getMember();
//
//        MessageTemplate messageTemplate = getMessageTemplate(200);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("title", messageTemplate.getTrnsMmt());
//        params.put("notiDsc","");
//        params.put("body", messageTemplate.getPushMsg());
//
//        params = memberService.getDevicdInfo(member.getMembId(), params);
//
//        fcmSendPushService.sendPush(params);
//    }
//
//    /**
//     * 회원삭제 SMS 전송
//     * @param event
//     * @throws Exception
//     */
//    @EventListener
//    public void sendDeletedSms(DeletedEvent event) throws Exception {
//        Members member = event.getMember();
//        MessageTemplate messageTemplate = getMessageTemplate(200);
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("title", messageTemplate.getTrnsMmt());
//        String[] args = {member.getMembNm()};
//        params.put("message", MessageFormat.format(messageTemplate.getKakaoMsg(), args));
//        params.put("receiver",getReceiver(member));
//
//        directSendSmsService.sendSms(params);
//
//    }
//
//    private String getReceiver(Members member){
//        String receiver = "{\"name\": \""+member.getMembNm()+"\", \"mobile\":\""+member.getHpNo()+"\", \"note1\":\"\", \"note2\":\"\", \"note3\":\"\", \"note4\":\"\", \"note5\":\"\"}";
//        return "["+ receiver +"]";
//    }
//
//    private MessageTemplate getMessageTemplate(Integer id){
//        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(id);
//        if(messageTemplate == null)
//            throw new RuntimeException("잘못된 요청입니다.");
//
//        return messageTemplate;
//    }
//}
