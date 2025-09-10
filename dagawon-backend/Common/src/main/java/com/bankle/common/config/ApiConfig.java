package com.bankle.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {

    public static String KCB_CP_CD;
    public static String KCB_SITE_NAME;
    public static String KCB_SITE_URL;
    public static String KCB_POP_URL;
    public static String KCB_TARGET;
    public static String KCB_LICENSE;

    public static String INFOTECH_APIKEY;
    public static String INFOTECH_URL;
    public static String INFOTECH_IP;
    public static String INFOTECH_PAYNO;
    public static String INFOTECH_PAYPW;
    public static String INFOTECH_USERID1;
    public static String INFOTECH_USERID2;
    public static String INFOTECH_USERID3;
    public static String INFOTECH_USERID4;
    public static String INFOTECH_USERID5;
    public static String INFOTECH_USERID6;
    public static String INFOTECH_USERID7;
    public static String INFOTECH_USERID8;
    public static String INFOTECH_USERID9;
    public static String INFOTECH_USERID10;
    public static String INFOTECH_USERPW1;
    public static String INFOTECH_USERPW2;
    public static String INFOTECH_USERPW3;
    public static String INFOTECH_USERPW4;
    public static String INFOTECH_USERPW5;
    public static String INFOTECH_USERPW6;
    public static String INFOTECH_USERPW7;
    public static String INFOTECH_USERPW8;
    public static String INFOTECH_USERPW9;
    public static String INFOTECH_USERPW10;
    public static String TIFF_SECRET_KEY;


    @Value("${kcb.kcb_cp_cd}")
    public void setKCB_CP_CD(String kcbCpCd) {
        KCB_CP_CD = kcbCpCd;
    }

    @Value("${kcb.kcb_site_name}")
    public void setKCB_SITE_NAME(String kcbSiteName) {
        KCB_SITE_NAME = kcbSiteName;
    }

    @Value("${kcb.kcb_site_url}")
    public void setKCB_SITE_URL(String kcbSiteUrl) {
        KCB_SITE_URL = kcbSiteUrl;
    }

    @Value("${kcb.kcb_target}")
    public void setKCB_TARGET(String kcbTarget) {
        KCB_TARGET = kcbTarget;
    }

    @Value("${kcb.kcb_license}")
    public void setKCB_LICENSE(String kcbLicense) {
        KCB_LICENSE = kcbLicense;
    }

    @Value("${kcb.kcb_pop_url}")
    public void setKCB_POP_URL(String kcbPopUrl) {
        KCB_POP_URL = kcbPopUrl;
    }

    @Value("${infotech.apikey}")
    public void setINFOTECH_APIKEY(String infotechApikey) {
        INFOTECH_APIKEY = infotechApikey;
    }

    @Value("${infotech.url}")
    public void setINFOTECH_URL(String infotechUrl) {
        INFOTECH_URL = infotechUrl;
    }

    @Value("${infotech.ip}")
    public void setINFOTECH_IP(String infotechIp) {
        INFOTECH_IP = infotechIp;
    }

    @Value("${infotech.payNo}")
    public void setINFOTECH_PAYNO(String infotechPayNo) {
        INFOTECH_PAYNO = infotechPayNo;
    }

    @Value("${infotech.payPw}")
    public void setINFOTECH_PAYPW(String infotechPayPw) {
        INFOTECH_PAYPW = infotechPayPw;
    }

    @Value("${infotech.userId1}")
    public void setINFOTECH_USERID1(String infotechUserId1) {
        INFOTECH_USERID1 = infotechUserId1;
    }

    @Value("${infotech.userId2}")
    public void setINFOTECH_USERID2(String infotechUserId2) {
        INFOTECH_USERID2 = infotechUserId2;
    }

    @Value("${infotech.userId3}")
    public void setINFOTECH_USERID3(String infotechUserId3) {
        INFOTECH_USERID3 = infotechUserId3;
    }

    @Value("${infotech.userId4}")
    public void setINFOTECH_USERID4(String infotechUserId4) {
        INFOTECH_USERID4 = infotechUserId4;
    }

    @Value("${infotech.userId5}")
    public void setINFOTECH_USERID5(String infotechUserId5) {
        INFOTECH_USERID5 = infotechUserId5;
    }

    @Value("${infotech.userId6}")
    public void setINFOTECH_USERID6(String infotechUserId6) {
        INFOTECH_USERID6 = infotechUserId6;
    }

    @Value("${infotech.userId7}")
    public void setINFOTECH_USERID7(String infotechUserId7) {
        INFOTECH_USERID7 = infotechUserId7;
    }

    @Value("${infotech.userId8}")
    public void setINFOTECH_USERID8(String infotechUserId8) {
        INFOTECH_USERID8 = infotechUserId8;
    }

    @Value("${infotech.userId9}")
    public void setINFOTECH_USERID9(String infotechUserId9) {
        INFOTECH_USERID9 = infotechUserId9;
    }

    @Value("${infotech.userId10}")
    public void setINFOTECH_USERID10(String infotechUserId10) {
        INFOTECH_USERID10 = infotechUserId10;
    }

    @Value("${infotech.userPw1}")
    public void setINFOTECH_USERPW1(String infotechUserPw1) {
        INFOTECH_USERPW1 = infotechUserPw1;
    }

    @Value("${infotech.userPw2}")
    public void setINFOTECH_USERPW2(String infotechUserPw2) {
        INFOTECH_USERPW2 = infotechUserPw2;
    }

    @Value("${infotech.userPw3}")
    public void setINFOTECH_USERPW3(String infotechUserPw3) {
        INFOTECH_USERPW3 = infotechUserPw3;
    }

    @Value("${infotech.userPw4}")
    public void setINFOTECH_USERPW4(String infotechUserPw4) {
        INFOTECH_USERPW4 = infotechUserPw4;
    }

    @Value("${infotech.userPw5}")
    public void setINFOTECH_USERPW5(String infotechUserPw5) {
        INFOTECH_USERPW5 = infotechUserPw5;
    }

    @Value("${infotech.userPw6}")
    public void setINFOTECH_USERPW6(String infotechUserPw6) {
        INFOTECH_USERPW6 = infotechUserPw6;
    }

    @Value("${infotech.userPw7}")
    public void setINFOTECH_USERPW7(String infotechUserPw7) {
        INFOTECH_USERPW7 = infotechUserPw7;
    }

    @Value("${infotech.userPw8}")
    public void setINFOTECH_USERPW8(String infotechUserPw8) {
        INFOTECH_USERPW8 = infotechUserPw8;
    }

    @Value("${infotech.userPw9}")
    public void setINFOTECH_USERPW9(String infotechUserPw9) {
        INFOTECH_USERPW9 = infotechUserPw9;
    }

    @Value("${infotech.userPw10}")
    public void setINFOTECH_USERPW10(String infotechUserPw10) {
        INFOTECH_USERPW10 = infotechUserPw10;
    }

    @Value("${tiff.secret.key}")
    public void setTIFF_SECRET_KEY(String tiffSecretKey) {
        TIFF_SECRET_KEY = tiffSecretKey;
    }

}
