package com.bankle.common.asis.component.directSend;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectSendTemplateService {

    private HttpURLConnection conn;

//    @Value("${direct.send.username}")
    private String username;

//    @Value("${direct.send.key}")
    private String key;

    private final DirectSendCmnService directSendCmnService;
    private final ObjectMapper objectMapper;

    public void initialize() throws Exception {
        String url = "https://directsend.co.kr/index.php/api_kakao/template/get/list";
        conn = directSendCmnService.getConnection(url);
    }

    public String getUserTemplateNo(String templateType) throws Exception {
        Map<String, Object> result = getTemplate(templateType);
        List<HashMap<String, String>> data = (List<HashMap<String, String>>) result.get("data");

        if (!"1".equals(String.valueOf(result.get("result"))))
            throw new RuntimeException(String.valueOf(result.get("message")));

        HashMap<String, String> datum = data.stream().filter(d -> templateType.equals(d.get("user_template_type")))
                .findFirst().orElse(null);

        if (datum != null)
            throw new RuntimeException("템플릿이 없습니다.");

        return String.valueOf(result.get("user_template_no"));
    }

    private Map<String, Object> getTemplate(String templateType) throws Exception {
        initialize();

        String urlParameters = setParams(templateType);
        directSendCmnService.send(conn, urlParameters);

        /*
         * response 성공 json 데이터 양식
         * {result:1, message:"success", data:{~~}}
         *   data:total_count 예약 목록 전체 갯수
         *   data:list[~~]  예약 목록 정보
         *      템플릿 번호	user_template_no
         *      템플릿 타입	user_template_type
         *      템플릿 제목	user_template_subject
         *      템플릿 내용	user_template_content
         *      @검색용아이디	sms_auth_kakao_plus_id
         *      등록일	user_template_date
         *      템플릿 코드	template_code
         *      템플릿 히스토리	template_comments
         *      템플릿 버튼	template_buttons
         *      템플릿 심사상태	template_insp_code
         *      템플릿 상태	template_status
         *      템플릿 내용 글자수	template_length
         *      프로필키	profile_key
         *      템플릿 이미지	template_images
         *      템플릿 미리보기	template_preview
         *      플러스친구 이름	kakao_plus_name
         *      휴대폰 번호	kakao_phone
         *      템플릿 광고 여부	template_ad
         *      템필릿 이미지 링크	template_img_link
         */

        /*
         * response 실패 json 데이터 양식
         * {result:300-1, message:에러 내용}
         */

		/*
			status code
			1 : 성공
			300-2 : 파라미터 오류
			300-9 : 템플릿 목록 조회 오류
		 */

        HashMap resultMap = new HashMap();
        String response = directSendCmnService.getResponse(conn);

        return objectMapper.readValue(response, Map.class);
    }

    private String setParams(String templateType) {

        return "{\"username\":\"" + username + "\",\"key\":\"" + key + "\", \"template_type\":\"" + templateType + "\"}";
    }
}
