package com.bankle.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonConfig {

    /**
     * Common 및 모든 프로젝트 Configuration 파일 사용방법입니다.
     * <p>
     * 해당 클래스의 기능은 application-{server}.yml 파일 내의 설정된 내용의 value를 가져오는 기능을 하고 있습니다.
     * 해당하는 값을 불러와서 사용하는 방법은 아래와 동일하다.
     * static으로 사용을 할 것 이기 때문에 경로를 언더스코어 & 대문자로 작성을 합니다.
     * 어노테이션 @Value로 선언하여 yml내에 지정한 경로로 작성을 해줍니다. 이떄는 ":"(콜론) 을 .으로 변경하여 붙여서 작성을 해줍니다.
     * 선언된 어노테이션 아래에 setter를 작성을 해줍니다.
     * 해당하는 property의 값을 쓸 곳에 가서 CommonConfig.SAMPLE 로 불러서 사용하시면 됩니다.
     * <p>
     * <ex>
     * <p>
     * application-common-dev.yml >>>>>>>>>>>> 파일 위치입니다.
     * <p>
     * private static String CUSTOM_PROPERTY_SAMPLE;
     *
     * @Value("${custom.property.sample}") public void setCUSTOM_PROPERTY_SAMPLE(String sample) {
     * CUSTOM_PROPERTY_SAMPLE = sample;
     * }
     * </ex>
     */
    public static String COOCON_URL;
    public static String COOCON_KEY;
    public static String CORP_NUM;
    public static String USER_ID;
    public static String ANBU_ADMIN_URL;
    public static String ANBU_APP_URL;
    public static String ANBU_BATCH_URL;

    public static String KAKAO_CLIENT_CODE;

    public static String KAKAO_LINK_ID;
    public static String KAKAO_SECRET_KEY;
    public static String ENV;

    @Value("${spring.config.activate.on-profile}")
    public void setENV(String env) { ENV = env; }
    @Value("${kakaocert.linkId}")
    public void setKAKAO_LINK_ID(String kakaoLinkId) {KAKAO_LINK_ID = kakaoLinkId;}
    @Value("${kakaocert.secretKey}")
    public void setKAKAO_SECRET_KEY(String kakaoSecretKey) {KAKAO_SECRET_KEY = kakaoSecretKey;}
    @Value("${kakaocert.clientCode}")
    public void setKAKAO_CLIENT_CODE(String clientCode){KAKAO_CLIENT_CODE = clientCode;}
    @Value("${anbu.url.admin}")
    public void setANBU_ADMIN_URL(String anbuAdminUrl){ANBU_ADMIN_URL = anbuAdminUrl;}
    @Value("${anbu.url.app}")
    public void setANBU_APP_URL(String anbuAppUrl){ANBU_APP_URL = anbuAppUrl;}
    @Value("${anbu.url.batch}")
    public void setANBU_BATCH_URL(String anbuBatchUrl){ANBU_BATCH_URL = anbuBatchUrl;}
    @Value("${coocon.url}")
    public void setCOOCON_URL(String cooconUrl) { COOCON_URL = cooconUrl; }
    @Value("${coocon.key}")
    public void setCOOCON_KEY(String cooconKey) { COOCON_KEY = cooconKey; }
    @Value("${message.kakao.corp_num}")
    public void setCORP_NUM(String corpNum) { CORP_NUM = corpNum; }
    @Value("${message.kakao.user_id}")
    public void setUSER_ID(String userId) { USER_ID = userId; }
}
