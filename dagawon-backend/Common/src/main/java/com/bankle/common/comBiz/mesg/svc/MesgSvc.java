package com.bankle.common.comBiz.mesg.svc;

import com.bankle.common.comBiz.mesg.vo.FcmPayload;
import com.bankle.common.comBiz.mesg.vo.MesgSvo;
import com.bankle.common.config.CommonConfig;
import com.bankle.common.dto.TbEscrRltnsListDto;
import com.bankle.common.dto.TbMesgSendHistDto;
import com.bankle.common.entity.TbMesgSendHist;
import com.bankle.common.enums.Sequence;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.mapper.*;
import com.bankle.common.repo.*;
import com.bankle.common.spec.TbMesgSendHistSpec;
import com.bankle.common.userAuth.UserAuthSvc;
import com.bankle.common.util.BizUtil;
import com.bankle.common.util.CustomeModelMapper;
import com.bankle.common.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import com.popbill.api.KakaoService;
import com.popbill.api.PopbillException;
import com.popbill.api.kakao.KakaoButton;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MesgSvc {

    private final static String SEND_TYPE_A = "A";

    private final BizUtil bizUtil;

    private final CustomeModelMapper customeModelMapper;

    private final TbMesgSendHistRepository tbMesgSendHistRepo;
    private final TbMesgTpltRepository tbMesgTpltRepo;
    private final TbEscrMasterRepository tbEscrMasterRepo;
    private final TbEscrRltnsListRepository tbEscrRltnsListRepo;
    private final TbCustMasterRepository tbCustMasterRepo;
    private final TbCustAlramYnRepository tbCustAlramYnRepo;

    private final KakaoService kakaoSvc;
    private final FirebaseMessaging messaging;

    ObjectMapper objectMapper = new ObjectMapper();

    String fcmId1 = "fbJrsZprwU7-uYNpgyE5k_:APA91bEkmm7DrY1WwFZBABZFMii3miQdwQezXJX7VRpzLQZiCH9VmzxCuolMK38ZWi-qdGv7tzsxrWtZWhJ-e05eBAzyAeHiciTphjhXrdYxRNeSKtKDXXHZNie874aVtIsk8EFzTfS9";
    String fcmId2 = "cbPOgFAqQQivVhfkf44Cyn:APA91bENPANvb68OIHKiL9gMgy3G1CpvOfEUF3yZ0HuIlMMOeoM2Ns5_vh8bXBy4OiomRLZ6vM7yhsf18k5Wjd11wid_RjMkaGqqfzKO8QZGMBCAcdVr8Zl1dXr4NAGmUpBWJMWGhBgy";


    /**
     * MesgSvc.class
     * 메세지 전송 이력 조회
     *
     * @author sh.lee
     * @date 2024-05-14
     */
    public List<MesgSvo.MesgSearchOut> mesgHistList(MesgSvo.MesgSearchIn searchIn) {
        try {
            Specification<TbMesgSendHist> spec = Specification.where(TbMesgSendHistSpec.equalAddreNm(searchIn.getAddreNm()));
            var histList = tbMesgSendHistRepo.findAll(spec);
            if (histList.stream().count() > 0) {
                var histDtoList = histList.stream().map(TbMesgSendHistMapper.INSTANCE::toDto).toList();

                return histDtoList.stream().map(e -> customeModelMapper.mapping(e, MesgSvo.MesgSearchOut.class)).toList();
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    /**
     * MesgSvc.class
     * 단건 메세지 템플릿 조회
     *
     * @author sh.lee
     * @date 2024-05-14
     */
    public MesgSvo.MesgTpltOut findMesgTplt(MesgSvo.MesgTpltIn mesgTpltIn) {
        try {
            var tpltModel = tbMesgTpltRepo.findByTpltSeq(mesgTpltIn.getTpltSeq())
                    .orElseThrow(() -> new DefaultException("요청하신 템플릿이 존재하지 않습니다."));

            var tpltDto = TbMesgTpltMapper.INSTANCE.toDto(tpltModel);

            return customeModelMapper.mapping(tpltDto, MesgSvo.MesgTpltOut.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    /**
     * MesgSvc.class
     * KAKAOTALK SEND SERVICE
     *
     * @author sh.lee
     * @date 2024-05-14
     */
    public void kakaoSend(List<String> membNo, String tpltSeq, Object[] msgPattern, String connUrl) throws Exception {
        String receiptNum = "";

        var tpltMesg = customeModelMapper.mapping(TbMesgTpltMapper.INSTANCE.toDto(
                tbMesgTpltRepo.findByTpltSeq(tpltSeq)
                        .orElseThrow(() -> new DefaultException("템플릿이 존재하지 않습니다."))
        ), MesgSvo.MesgTpltOut.class);

        var userInfo = TbCustMasterMapper.INSTANCE.toDto(
                tbCustMasterRepo.findByMembNo(membNo.get(0))
                        .orElseThrow(() -> new DefaultException("회원정보가 존재하지 않습니다."))
        );

        String tpltCd = tpltMesg.getTpltCd();
        String senderNum = tpltMesg.getGidTelno();
        String content = MessageFormat.format(tpltMesg.getTpltCnts(), msgPattern);
        String altContent = MessageFormat.format(tpltMesg.getAltTpltCnts(), msgPattern);
        String receiverNum = userInfo.getCphnNo();
        String receiverNm = userInfo.getMembNm();
        String sendDt = "";
        String requestNum = "";
        String btnNm = tpltMesg.getBtnNm();
        KakaoButton[] btns = null;
        if (StringUtils.hasText(btnNm)) {
            btns = new KakaoButton[1];

            KakaoButton button = new KakaoButton();
            button.setN(btnNm);
            button.setT("WL"); // webLink
            button.setU1(connUrl);
            button.setU2(connUrl); // 버튼링크2

            altContent = altContent + "\n\n" + connUrl;
        }

        try {
            receiptNum = kakaoSvc.sendATS(CommonConfig.CORP_NUM, tpltCd, senderNum, content, altContent, SEND_TYPE_A, receiverNum, receiverNm, sendDt, CommonConfig.USER_ID, requestNum, btns);

            if (StringUtils.hasText(receiptNum)) {
                var tbMesgSendHistDto = TbMesgSendHistDto.builder()
                        .seq(Long.parseLong(bizUtil.getSeq(Sequence.MESSAGE)))
                        .acptNo(receiptNum)
                        .escrNo("")
                        .tpltSeq(tpltSeq)
                        .tpltGbCd(String.valueOf(tpltSeq.charAt(0)))
                        .msgTitle(tpltMesg.getTpltTtl())
                        .msgTransCnts(content)
                        .altMsgTransCnts(altContent)
                        .sndNo(senderNum)
                        .sndDtm(LocalDateTime.parse(sendDt))
                        .rcvNo(receiverNum)
                        .addreMembNo(membNo.get(0))
                        .addreNm(receiverNm)
                        .rsltCd("200")
                        .build();

                tbMesgSendHistRepo.save(TbMesgSendHistMapper.INSTANCE.toEntity(tbMesgSendHistDto));

            }
        } catch (PopbillException popbillE) {
            popbillE.printStackTrace();
            var tbMesgSendHistDto = TbMesgSendHistDto.builder()
                    .seq(Long.parseLong(bizUtil.getSeq(Sequence.MESSAGE)))
                    .acptNo(receiptNum)
                    .escrNo("")
                    .tpltSeq(tpltSeq)
                    .tpltGbCd(String.valueOf(tpltSeq.charAt(0)))
                    .msgTitle(tpltMesg.getTpltTtl())
                    .msgTransCnts(content)
                    .altMsgTransCnts(altContent)
                    .sndNo(senderNum)
                    .sndDtm(LocalDateTime.parse(sendDt))
                    .rcvNo(receiverNum)
                    .addreMembNo(membNo.get(0))
                    .addreNm(receiverNm)
                    .rsltCd("999")
                    .build();

            tbMesgSendHistRepo.save(TbMesgSendHistMapper.INSTANCE.toEntity(tbMesgSendHistDto));
            throw new DefaultException("팝빌 알림톡 발송중 오류가 발생하였습니다.");
        }
    }

    /**
     * MesgSvc.class
     * SMS SEND SERVICE
     *
     * @author sh.lee
     * @date 2024-05-14
     */
    public String smsSend(String escrNo) {
        String tpltSeq = "S01001"; // TpltSeq
        String corpNum = "5378702438"; // 팝빌 회원 사업자번호(하이픈'-' 제외 10자리)
        String sender = ""; // 발신번호
        String receiverName = ""; // 수신자명
        String content = ""; // 메시지 내용(한글, 한자, 특수문자 2byte / 영문, 숫자, 공백 1byte)
        Date reserveDT = null; // 전송 예약 일시(형식: yyyyMMddHHmmss) 미입력시 즉시 전송
        Boolean adsYN = false; // 광고 메시지 전송 여부: true / false 중 택 1 true : 광고 , false : 일반
        String UserID = ""; // 팝빌 회원 아이디
        String requestNum = ""; // 전송 요청번호 / 파트너가 접수 단위를 식별하기 위해 부여하는 관리번호 영문 대소문자, 숫자, 특수문자('-','_')만 이용 가능

        // 원장이 존재하는지 우선 확인을 진행한다.
        var escrMaster = TbEscrMasterMapper.INSTANCE.toDto(
                tbEscrMasterRepo.findByEscrNo(escrNo)
                        .orElseThrow(() -> new DefaultException("존재하지 않는 에스크로 원장입니다."))
        );

        // 원장이 존재한다면 해당하는 관계자 정보를 가져온다.
        var escrRltnsList = tbEscrRltnsListRepo.findByEscrNo(escrNo);
        if (escrRltnsList.stream().count() < 1) {
            throw new DefaultException("관계자 정보가 존재하지 않습니다. 다시 확인 바랍니다.");
        }
        var escrRltnsListDto = escrRltnsList.stream().map(TbEscrRltnsListMapper.INSTANCE::toDto).toList();

        String buyerNm = "";
        for (TbEscrRltnsListDto tbEscrRltnsListDto : escrRltnsListDto) {
            if ("1".equals(tbEscrRltnsListDto.getRltnsGbCd())) {
                buyerNm = tbEscrRltnsListDto.getRltnsNm();
            }
        }

        for (TbEscrRltnsListDto dto : escrRltnsListDto) {
            String receiver = ""; // 수신번호
        }
        // 현재기준으로는 템플릿만 정의해서 던져주면된다.

        var tpltMesg = TbMesgTpltMapper.INSTANCE.toDto(
                tbMesgTpltRepo.findByTpltSeq(tpltSeq)
                        .orElseThrow(() -> new DefaultException("템플릿이 존재하지 않습니다."))
        );

        var svo = customeModelMapper.mapping(tpltMesg, MesgSvo.MesgTpltOut.class);

        // 가져온 템플릿에 필요한 key맵핑을 진행 한 후 동의 작성을 들어갈수있도록 유도를 진행하는 메세지를 완성시킨다.
        Object[] msgPattern = {
                escrMaster.getPrdtGbCd() // 상품구분코드를 상품구분명으로 변경하여야한다.
                , escrMaster.getTrgtAddr() + escrMaster.getTrgtAddrDtl() // 주소
                , buyerNm // 매수인명
        };
        // 매도인명을 찾아온다.

        // 유니크 키를 생성해서 해당 관계자의 매도인에게 매핑해놓고, 관계자 내역에 내용을 저장해놓는다.

        return MessageFormat.format(svo.getTpltCnts(), msgPattern);
    }

    /**
     * MesgSvc.class
     * PUSH SEND SERVICE
     *
     * @author sh.lee
     * @date 2024-05-14
     */
    public boolean pushSend(@Valid MesgSvo.MesgPushIn pushIn) throws FirebaseMessagingException {
        try {
            // payload 설정
            FcmPayload payload = FcmPayload.builder()
                    .seq(getRndSeq())
                    .clickUri("demo")
                    .loanNo("12345678901234")
                    .desc("테스용 푸쉬")
                    .build();

            Message msq = Message.builder()
                    //.setToken(pushIn.getFcmId())
                    .setToken(fcmId2)
                    .setAndroidConfig(AndroidConfig.builder().build())
                    .setApnsConfig(
                            ApnsConfig.builder()
                                    .setAps(Aps.builder()
                                            .setCategory("push_click")
                                            .build())
                                    .build()
                    )
                    .setNotification(
                            Notification.builder()
                                    .setTitle(StringUtil.replaceEnter(pushIn.getTpltTtl()))
                                    .setBody(StringUtil.replaceEnter(pushIn.getTpltCnts()))
                                    .build())
                    .putAllData(objectMapper.convertValue(payload, Map.class))
                    .putData("type", "application/json")
                    .putData("Authorization","")
                    .build();
            String result = messaging.getInstance().send(msq);
            log.debug("전송결과 : " + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;    // 오류 발생시 오류 메시지 반환. 추후 수정 필요.
        }
    }

    /**
     * MesgSvc.class
     * PUSH SEND ASYNC SERVICE
     *
     * @author sh.lee
     * @date 2024-05-16
     */
    public boolean sendAllByToken(@Valid List<MesgSvo.MesgPushIn> pushInList) {

        List<Message> list = new ArrayList<>();

        try {

            // 푸쉬 내부 data 객체
            FcmPayload payload = FcmPayload.builder()
                    .seq(getRndSeq())
                    .clickUri("/aaaaa/vvvvv/111")
                    .loanNo("12345678901234")
                    .desc("테스용 푸쉬")
                    .build();

            pushInList.forEach(item -> {
                list.add(
                        Message.builder()
                                .setToken(item.getFcmId())
                                .setNotification(
                                        Notification.builder()
                                                .setTitle(StringUtil.replaceEnter(item.getTpltTtl()))
                                                .setBody(StringUtil.replaceEnter(item.getTpltCnts()))
                                                .build())
                                .putAllData(objectMapper.convertValue(payload, Map.class))
                                .build()
                );
            });
            BatchResponse response = messaging.sendEach(list);
            log.debug("전송결과 : " + response);
            log.debug("전송결과 : " + response.getSuccessCount());
            log.debug("전송결과 : " + response.getFailureCount());
            log.debug("전송결과 : " + response.getResponses());
        } catch (Exception e) {
            e.printStackTrace();
            return false;    // 오류 발생시 오류 메시지 반환. 추후 수정 필요.
        }
        return true;
    }

    public Boolean sendPush(){
        try{

            var cust = TbCustMasterMapper.INSTANCE.toDto(
                    tbCustMasterRepo.findByMembNo(UserAuthSvc.getMembNo())
                            .orElseThrow(() -> new DefaultException("존재하지 않는 회원정보입니다!"))
            );

            log.debug("push send start======>");
            MesgSvo.MesgPushIn push = MesgSvo.MesgPushIn
                    .builder()
                    .fcmId(cust.getFcmId())
                    .tpltTtl("TEST TITLE")
                    .tpltCnts("TEST CONTENTS")
                    .build();

            return this.pushSend(push);
        }catch(Exception e){
        e.printStackTrace();
        throw new DefaultException(e.getMessage());
        }
    }


    private String getRndSeq() {
        Random rn = new Random();
        return String.valueOf(rn.nextInt(10) + 1);
    }

    /**
     * @Package com.bankle.common.comBiz.mesg.svc
     * @Class   MesgSvc.class
     * @method  alramYn
     * @Author  sh.lee
     * @date    2024-06-13
     * @version 1.0.0
     */
    public Boolean alramYn(String membNo, String alramGbCd) {
        try {
            var alramYn = TbCustAlramYnMapper.INSTANCE.toDto(
                    tbCustAlramYnRepo.findByMembNoAndAlramGbCd(membNo, alramGbCd)
                            .orElseThrow(() -> new DefaultException("존재하지 않는 회원입니다. 다시 확인 바랍니다."))
            );
            return "Y".equals(alramYn.getAlramYn());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

}
