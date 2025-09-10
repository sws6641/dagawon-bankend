//package kr.co.anbu.event.handler;
//
//import kr.co.anbu.component.directSend.DirectSendSmsService;
//import kr.co.anbu.component.FCMSendPushService;
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.entity.MessageTemplate;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.MessageTemplateService;
//import kr.co.anbu.event.BasicPaymentPushEvent;
//import kr.co.anbu.utils.DateCustUtils;
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
//public class BasicPaymentPushEventHandler {
//
//    private final MemberService memberService;
//    private final DirectSendSmsService directSendSmsService;
//    private final FCMSendPushService fcmSendPushService;
//
//    private final MessageTemplateService messageTemplateService;
//
//    @EventListener
//    public void sendSms(BasicPaymentPushEvent event) throws Exception {
//        ContractEscrow contractEscrow = event.getContractEscrow();
//        String beforeDay = event.getBeforeDay();
//
//        String prdtDsc = contractEscrow.getPrdtDsc();
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("title", "");
//        params.put("message", getSmsMsg(beforeDay, prdtDsc));
//        params.put("receiver",memberService.getReceiver(contractEscrow));
//
//        directSendSmsService.sendSms(params);
//
//    }
//
//    @EventListener
//    public void sendPush(BasicPaymentPushEvent event) throws Exception{
//        ContractEscrow contractEscrow = event.getContractEscrow();
//        String beforeDay = event.getBeforeDay();
//
//        String prdtDsc = contractEscrow.getPrdtDsc();
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("escrMKey", String.valueOf(contractEscrow.getEscrMKey()));
//        params.put("notiDsc","");
//        params.put("title", "");
//        params.put("body", getPushMsg(prdtDsc, beforeDay));
//
//        params = memberService.getDevicdInfo(contractEscrow.getMembId(), params);
//
//        fcmSendPushService.sendPush(params);
//    }
//
//    private String getPushMsg(String prdtDsc, String beforeDay){
//        Integer days = Integer.valueOf(beforeDay);
//        String pushPattern;
//        String[] args;
//        if(days < 0){   // 지급예정일 지남
//            pushPattern = getPattern(26000,"PUSH");
//            args = new String[]{getDepositTxt(prdtDsc), beforeDay};
//
//        }else{
//            if(StringCustUtils.equals(beforeDay, "0")) {
//                pushPattern = getPattern(24001,"PUSH");
//                args = new String[]{getDepositTxt(prdtDsc)};
//
//                return MessageFormat.format(pushPattern, args);
//            }else{
//                pushPattern = getPattern(24000,"PUSH");
//                args = new String[]{getDepositTxt(prdtDsc), beforeDay,
//                        DateCustUtils.getThisDate("yyyy년 MM월 dd일")};
//            }
//        }
//        return MessageFormat.format(pushPattern, args);
//    }
//
//    private String getPattern(Integer id, String kind) {
//        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(id);
//        if(StringCustUtils.equals(kind, "PUSH")){
//            return messageTemplate.getPushMsg();
//        }else{
//            return messageTemplate.getKakaoMsg();
//        }
//    }
//
//    private String getSmsMsg(String beforeDay, String prdtDsc){
//        if(StringCustUtils.equals(beforeDay, "0")) {
//            String smsPattern = getPattern(24001, "SMS");
//            String[] args = {getDepositTxt(prdtDsc),DateCustUtils.getThisDate("yyyy년 MM월 dd일")};
//            return MessageFormat.format(smsPattern, args);
//        }else{
//            throw new RuntimeException("메시지가 없습니다.");
//        }
//    }
//
//
//    private String getTitle(String depositTxt, String beforeDay){
//
//        Integer days = Integer.valueOf(beforeDay);
//        String pattern;
//        String[] args;
//
//        if(days < 0){
//            pattern = "\"{0}\" 지급이 \"{1}\"일 지났어요";
//            args = new String[]{depositTxt, String.valueOf(days)};
//        }else{
//            if(StringCustUtils.equals(beforeDay, "0")){
//                pattern = "\"{0}\"을 지급해주세요";
//                args = new String[]{depositTxt};
//            }else{
//                pattern = "\"{0}\" 지급 \"{1}\"일 전이에요";
//                args = new String[]{depositTxt, beforeDay};
//            }
//        }
//
//        return MessageFormat.format(pattern, args);
//    }
//
//    private String getMessage(String depositTxt, String beforeDay, String pmntPlnDt) {
//
//        Integer days = Integer.valueOf(beforeDay);
//        String pattern;
//        String[] args;
//
//        if(days < 0){
//            pattern = "지정된 \"{0}\" 지급일이 \"{1}\"일 지났어요. 계약정보가 바뀐 경우 APP에서 수정해주세요.";
//            args = new String[]{depositTxt, String.valueOf(days)};
//        }else{
//            if(StringCustUtils.equals(beforeDay, "0")){
//                pattern = "메시지를 눌러 지급승인을 진행해주세요.";
//                args = new String[]{};
//            }else{
//                pattern = "\"{0}\"은 \"{1}\" 지급예정일 입니다. 계약정보가 바뀐 경우 APP에서 미리 수정해주세요.";
//                args = new String[]{DateCustUtils.getDateWithPattern(pmntPlnDt, "yyyy년 MM월 dd일"), depositTxt};
//            }
//        }
//
//        return MessageFormat.format(pattern, args);
//    }
//
//    /**
//     * 상품구분, 에스크로진행상세코드에 따른 대금코드 조회
//     * @param prdtDsc
//     * @return
//     */
//    private static String getDepositTxt(String prdtDsc){
//        if(StringCustUtils.equals(prdtDsc, "1")){
//            return "매매대금";
//        }else{
//            return "보증금";
//        }
//    }
//}
