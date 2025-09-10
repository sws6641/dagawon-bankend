//package kr.co.anbu.event.handler;
//
//import kr.co.anbu.component.directSend.DirectSendKakaoTokService;
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.entity.ContractEscrowParty;
//import kr.co.anbu.domain.entity.MessageTemplate;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.MessageTemplateService;
//import kr.co.anbu.event.ContractKakaoEvent;
//import kr.co.anbu.utils.CommonUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class ContractKakaoEventHandler {
//
//    private final DirectSendKakaoTokService directSendKakaoTokService;
//    private final MessageTemplateService messageTemplateService;
//    private final MemberService memberService;
//
//    @EventListener
//    public void kakaoHandler(ContractKakaoEvent event) throws Exception {
//
//        HashMap<String, String> params = new HashMap<>();
//
//        //TODO 1. 템플릿 조회
//        String templateType = "3";
//        params.put("templateType", templateType);
//
//        ContractEscrow escrow = event.getEscrow();
//        String escrDtlPgc = escrow.getEscrDtlPgc();     //에스크로 상세진행코드
//        params.put("title", "");
//        params.put("message", getSmsMessage(escrDtlPgc, escrow));
//        params.put("receiver",getReceiver(escrDtlPgc, escrow));
//        params = memberService.getDevicdInfo(escrow.getMembId(), params);
//
//        directSendKakaoTokService.sendKakaoTok(params);
//
//    }
//
//    /**
//     * 알림톡이 안되는 경우, 대체문자메시지 (80000, 80001)
//     * @param escrDtlPgc
//     * @param escrow
//     * @return
//     * @throws Exception
//     */
//    private String getSmsMessage(String escrDtlPgc, ContractEscrow escrow) throws Exception {
//
//        String message;
//
//        int msgPrttIKey = Integer.parseInt(StringCustUtils.rightPad(escrDtlPgc, 5, "0"));
//        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(msgPrttIKey);
//
//        if(messageTemplate.getKakaoMsg() == null)
//            throw new RuntimeException("메시지가 없습니다.");
//
//        //에스크로 해지 메시지
//        message = messageTemplate.getKakaoMsg();
//
//        //에스크로 해지(반환금이 있는 경우)
//        if(msgPrttIKey == 80000 && !Objects.equals(escrow.getEscrBlncAmt(), 0L)){
//            messageTemplate = messageTemplateService.getMessageTemplate(80001);
//            String kakaoMsg = messageTemplate.getKakaoMsg();
//
//            String[] args = {CommonUtils.getCmnNm("BNK_CD", escrow.getPmntBnkCd()) + escrow.getPmntAcctNo(),
//                    memberService.getMembNm(escrow.getMembId()),
//                    StringCustUtils.toDecimalFormat(escrow.getEscrBlncAmt())};
//
//            message = MessageFormat.format(kakaoMsg, args);
//        }
//
//        return message;
//    }
//
//    @Transactional
//    public String getReceiver(String escrDtlPgc, ContractEscrow escrow) throws Exception {
//
//        int msgPrttIKey = Integer.parseInt(StringCustUtils.rightPad(escrDtlPgc, 5, "0"));
//
//        if(msgPrttIKey == 2000){    //매도인, 매수인/ 임대인, 임차인 전체 전송
//            List<ContractEscrowParty> contractEscrowParties = escrow.getContractEscrowParties();
//            if(contractEscrowParties.size() == 0)
//                throw new RuntimeException("잘못된 요청입니다.");
//
//            List<String>  list = new ArrayList<>();
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
//}
