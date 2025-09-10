package com.bankle.common.asis.component.directSend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectSendKakaoTokService {

    private HttpURLConnection conn;

//    @Value("${direct.send.username}")
    private String username;

//    @Value("${direct.send.key}")
    private String key;

//    @Value("${direct.send.kakao.plusId}")
    private String kakaoPlusId;

//    @Value(("${direct.send.sender}"))
    private String sender;

    private final DirectSendCmnService directSendCmnService;
    private final DirectSendTemplateService directSendTemplateService;


    public void initialize() throws Exception{
        String url = "https://directsend.co.kr/index.php/api_v2/kakao_notice";
        conn = directSendCmnService.getConnection(url);
    }

    public Map<String, String> sendKakaoTok(HashMap<String,String> params) throws Exception {

        initialize();

        //1, user_template_no 조회
        try{
            String userTemplateNo = directSendTemplateService.getUserTemplateNo(params.get("templateType"));
            params.put("userTemplateNo", userTemplateNo);
        }catch (RuntimeException e){
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        params.put("message", "[$NAME]님 알림 SMS 문자 입니다. " + params.get("message") +
                "전화번호 : [$MOBILE] 비고1 : [$NOTE1] 비고2 : [$NOTE2] 비고3 : [$NOTE3] 비고4 : [$NOTE4] 비고5 : [$NOTE5]");

        String urlParameters = setParams(params);
        int resultCode = directSendCmnService.send(conn, urlParameters);
        if(resultCode != 200)
            throw new RuntimeException("directsend 관리자에게 문의해주시기 바랍니다.");

        /*
         * response의 실패
         * {"status":300, "message":"필수 입력 값이 없습니다."}
         * 실패 코드번호, 내용
         *
         * status code 308 실패인 경우 인코딩 실패 문자열 return
         *  {"status":308, "message": "message EUC-KR 인코딩에 실패 하였습니다.\n msg_detail":풰(13)}
         *  실패 코드번호, 내용, 인코딩 실패 문자열(문자열 위치)
         */

        /*
         * response 성공
         * {"status":1}
         * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
         *
         * 잘못된 번호가 포함된 경우
         * {"status":1, "message":"유효하지 않는 번호를 제외하고 발송 완료 하였습니다.\n error mobile : 01000000001aa, 010112"}
         * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.), 내용(잘못된 데이터)
         *
         */

		/* status code
			1   : 정상발송 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
			300 : POST validation 실패
			301 : receiver 유효한 번호가 아님
			302 : api key or user is invalid
			303 : 분당 300건 이상 API 호출을 할 수 없습니다.
			304 : 대체문자 message validation 실패
			305 : 발신 프로필키 유효한 키가 아님
			306 : 잔액부족
			307 : return_url이 없음
			308 : 대체문자 utf-8 인코딩 에러 발생
			309 : 대체문자 message length = 0
			310 : 대체문자 euckr 인코딩 에러 발생
			311 : 대체문자 sender 유효한 번호가 아님
			312 : 대체문자 title validation 실패
			313 : 카카오 내용 validation 실패
			314 : 이미지 갯수 초과
			315 : 이미지 확장자 오류
			316 : 이미지 업로드 실패
			317 : 이미지 용량 300kb 초과
			318 : 예약정보가 유효하지 않습니다.
			319 : 동일 예약시간으로는 200회 이상 API 호출을 할 수 없습니다.
			999 : Internal Error.
		 */

        String response = directSendCmnService.getResponse(conn);

        Map<String,String> fromJson = new Gson().fromJson(
                response, new TypeToken<HashMap<String, String>>() {
                }.getType());

        return fromJson;
    }


    private String setParams(HashMap<String,String> params){
        String userTempateNo = params.get("userTempateNo");

        return  "\"username\":\""+ username +"\"," +
                "\"key\":\""+ key +"\", " +
                "\"type\":\"java\", " +
                "\"kakao_plus_id\":\"" +kakaoPlusId+ "\", " +
                "\"user_template_no\":\"" + userTempateNo + "\", " +
                //대체문자 ------------------------------
                "\"kakao_faild_type\":\"" +params.get("kakao_faild_type")+ "\", " +
                "\"title\":\"\", " +
                "\"message\":\""+params.get("message")+"\", "+
                //대체문자 ------------------------------
                "\"sender\":\""+sender+"\"";
    }
}
