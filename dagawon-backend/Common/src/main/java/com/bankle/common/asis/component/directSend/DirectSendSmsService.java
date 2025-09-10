package com.bankle.common.asis.component.directSend;

import com.bankle.common.asis.domain.entity.TgLogMaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectSendSmsService {

    private HttpURLConnection conn;

//    @Value("${direct.send.username}")
    private String username;

//    @Value("${direct.send.key}")
    private String key;

//    @Value(("${direct.send.sender}"))
    private String sender;

    private final DirectSendCmnService directSendCmnService;
//    private final TgLogMasterService tgLogMasterService;
    private TgLogMaster tgLogMaster;

    /**
     * initialize
     * @throws IOException
     */
    public void initialize() throws Exception {
        String url = "https://directsend.co.kr/index.php/api_v2/sms_change_word";
        conn = directSendCmnService.getConnection(url);

        tgLogMaster = TgLogMaster.builder()
                .tgCd("AN02")
                .build();
    }

    /**
     * 파라미터 세팅
     * @param params
     * @return
     */
    private String setParams(HashMap<String, String> params){

        String urlParameters = "\"title\":\"" + params.get("title") + "\" "
                + ", \"message\":\"" + params.get("message") + "\" "
                + ", \"sender\":\"" + sender + "\" "
                + ", \"username\":\"" + username + "\" "
                + ", \"receiver\":" + params.get("receiver")
                + ", \"key\":\"" + key + "\" "
                + ", \"type\":\"" + "java" + "\" ";

        return "{"+ urlParameters  +"}";		//JSON 데이터
    }

    /**
     * sms 전송
     * @param params
     * @return
     * @throws IOException
     */
    public Map<String,String> sendSms(HashMap<String,String> params) throws Exception {

        initialize();

        String urlParameters = setParams(params);
        tgLogMaster.setLkSndTg(urlParameters);
        tgLogMaster.setLkRcvDtm(LocalDateTime.now());

        int resultCode = directSendCmnService.send(conn, urlParameters);
        if(resultCode != 200)
            throw new RuntimeException("directsend 관리자에게 문의해주시기 바랍니다.");

        /*
         * response의 실패
         * {"status":113, "msg":"UTF-8 인코딩이 아닙니다."}
         * 실패 코드번호, 내용
         *
         * status code 112 실패인 경우 인코딩 실패 문자열 return
         *  {"status":112, "msg": "message EUC-KR 인코딩에 실패 하였습니다.", "msg_detail":풰(13)}
         *  실패 코드번호, 내용, 인코딩 실패 문자열(문자열 위치)
         */

        /*
         * response 성공
         * {"status":0}
         * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
         *
         * 잘못된 번호가 포함된 경우
         * {"status":0, "msg":"유효하지 않는 번호를 제외하고 발송 완료 하였습니다.", "msg_detail":"error mobile : 01000000001aa, 010112"}
         * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.), 내용, 잘못된 데이터
         *
            status code
            0   : 정상발송 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
            100 : POST validation 실패
            101 : sender 유효한 번호가 아님
            102 : recipient 유효한 번호가 아님
            103 : 회원정보가 일치하지 않음
            104 : 받는 사람이 없습니다
            105 : message length = 0, message length >= 2000, title >= 20
            106 : message validation 실패
            107 : 이미지 업로드 실패
            108 : 이미지 갯수 초과
            109 : return_url이 유효하지 않습니다
            110 : 이미지 용량 300kb 초과
            111 : 이미지 확장자 오류
            112 : euckr 인코딩 에러 발생
            114 : 예약정보가 유효하지 않습니다.
            200 : 동일 예약시간으로는 200회 이상 API 호출을 할 수 없습니다.
            201 : 분당 300회 이상 API 호출을 할 수 없습니다.
            205 : 잔액부족
            999 : Internal Error.
            *
		  */
        String response = directSendCmnService.getResponse(conn);

        Map<String,String> fromJson = new Gson().fromJson(
                response, new TypeToken<HashMap<String, String>>() {
                }.getType());

        tgLogMaster.setLkRcvTg(response);
        tgLogMaster.setLkRcvDtm(LocalDateTime.now());

        tgLogMaster.setSndRcvRslt(fromJson.get("status"));
        tgLogMaster.setSndRcvRsltMsg((fromJson.get("msg") == null) ? "" : fromJson.get("msg"));

//        saveTgLog();

        return fromJson;
    }

    /**
     * 송수신 내역 저장
     */
//    private void saveTgLog() {
//        tgLogMasterService.save(tgLogMaster);
//    }

}
