//package kr.co.anbu.event.handler;
//
//import kr.co.anbu.component.FCMSendPushService;
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.entity.ContractEscrowDetail;
//import kr.co.anbu.domain.entity.MessageTemplate;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.MessageTemplateService;
//import kr.co.anbu.event.InsurePaymentPushEvent;
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
//import java.util.List;
//import java.util.concurrent.atomic.AtomicReference;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class InsurePaymentPushEventHandler {
//
//    private final FCMSendPushService fcmSendPushService;
//    private final MessageTemplateService messageTemplateService;
//    private final MemberService memberService;
//
//    @EventListener
//    @Transactional
//    public void sendPush(InsurePaymentPushEvent event) throws Exception {
//        ContractEscrow escrow = event.getEscrow();
//        List<ContractEscrowDetail> contractEscrowDetails = escrow.getContractEscrowDetails();
//
//        String escrPgc = escrow.getEscrPgc();
//
//        //지급지연된 금액의 입금예정일 조회
//        String romPlnDt = getRomPlnDt(contractEscrowDetails, getChrgDsc(escrPgc));
//
//        //1일 또는 3일 지났는지 확인
//        String delayedDays = DateCustUtils.howLongDelayed(romPlnDt);
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("escrMKey", String.valueOf(escrow.getEscrMKey()));
//        params.put("title", "");
//        params.put("notiDsc","");
//        params.put("body", getPushMsg(escrPgc, delayedDays));
//
//        params = memberService.getDevicdInfo(escrow.getMembId(), params);
//
//        fcmSendPushService.sendPush(params);
//    }
//
//    private String getPushMsg(String escrPgc, String delayedDays){
//        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(26000);
//        String pushMsgPattern = messageTemplate.getPushMsg();
//        String[] args = {getDepositTxt(escrPgc), delayedDays};
//        return MessageFormat.format(pushMsgPattern, args);
//    }
//
//    /**
//     * 상품구분, 에스크로진행상세코드에 따른 대금코드 조회
//     * @param escrPgc
//     * @return
//     */
//    private String getDepositTxt(String escrPgc){
//        if(StringCustUtils.equals(escrPgc,"2")){
//            return "계약금";
//        }else if(StringCustUtils.equals(escrPgc,"3")){
//            return "중도금";
//        }else if(StringCustUtils.equals(escrPgc,"4")){
//            return "잔금";
//        }else{
//            return "";
//        }
//    }
//
//    private String getChrgDsc(String escrPgc){
//        if(StringCustUtils.equals(escrPgc,"2")){
//            return "0";
//        }else if(StringCustUtils.equals(escrPgc,"3")){
//            return "1";
//        }else if(StringCustUtils.equals(escrPgc,"4")){
//            return "9";
//        }else{
//            return "";
//        }
//    }
//
//    /**
//     * 지급지연된 금액의 입금예정일 조회
//     */
//    private String getRomPlnDt(List<ContractEscrowDetail> contractEscrowDetails, String chrgDsc){
//        //지급지연된 금액의 입금예정일 조회
//        AtomicReference<String> romPlnDt = new AtomicReference<>("");
//        contractEscrowDetails.forEach(d ->{
//            if(StringCustUtils.equals(chrgDsc, "1")){
//                //중도금 1,2차
//                if(StringCustUtils.equalsAny(d.getChrgDsc(), "1","2")){
//                    romPlnDt.set(d.getRomPlnDt());
//                }
//            }else if(StringCustUtils.equals(d.getChrgDsc(), chrgDsc)){
//                romPlnDt.set(d.getRomPlnDt());
//            }
//        });
//
//        return romPlnDt.get();
//    }
//
//}
