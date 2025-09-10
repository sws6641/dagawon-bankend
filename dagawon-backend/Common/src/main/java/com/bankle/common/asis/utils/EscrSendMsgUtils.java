package com.bankle.common.asis.utils;

import java.text.MessageFormat;
import java.util.HashMap;

import com.bankle.common.asis.component.directSend.DirectSendSmsService;
import com.bankle.common.asis.component.properties.SysMngProperties;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.util.StringUtil;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EscrSendMsgUtils {

//    private final FCMSendPushService pushService;
    private final DirectSendSmsService smsService;
    private final IfTgInfoMapper ifTgInfoMapper;
    private final SysMngProperties sysMngProp;

    public HashMap<String, Object> sendMsg(String escr_m_key, int msg_prtt_i_key, Object[] arrStrPatten, String userHpNo) {

        log.debug("Send Message  ESCR_M_KEY [" + escr_m_key + "]   msg_prtt_i_key [" + msg_prtt_i_key + "]");

        String sendResult = "";

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("escr_m_key"    , escr_m_key    );
        paramMap.put("msg_prtt_i_key", msg_prtt_i_key);


        HashMap<String, Object> selectMap = new HashMap<>();
        String hp_no     =  "";
        String memb_nm   =  "";
        String fcm_id    =  "";
        String dvc_knd   =  "";
        String trns_mmt  =  "";
        String kakao_msg =  "";
        String push_msg  =  "";
        String rslt_cd   = "0000";

        if (escr_m_key == null || "".equals(escr_m_key)) {

        } else {
            selectMap  = ifTgInfoMapper.selectSendMsg(paramMap) ;
            hp_no      = (String)selectMap.get("HP_NO"     );
            hp_no      = (userHpNo == null || "".equals(userHpNo)) ? hp_no: userHpNo;
            memb_nm    = (String)selectMap.get("MEMB_NM"   );
            fcm_id     = (String)selectMap.get("FCM_ID"    );
            dvc_knd    = (String)selectMap.get("DVC_KND"   );
            trns_mmt   = (String)selectMap.get("TRNS_MMT"  );
            kakao_msg  = (String)selectMap.get("KAKAO_MSG" );
            push_msg   = (String)selectMap.get("PUSH_MSG"  );

            if (arrStrPatten != null) {
                trns_mmt  = MessageFormat.format(trns_mmt , arrStrPatten);
                kakao_msg = MessageFormat.format(kakao_msg, arrStrPatten);
                push_msg  = MessageFormat.format(push_msg , arrStrPatten);
            }
        }

        // PUSH 전송
        if (!"".equals(push_msg)) {

            sendResult = sendPush(escr_m_key, fcm_id, dvc_knd, trns_mmt, push_msg, msg_prtt_i_key);

            if ("fail".equals(sendResult)) { rslt_cd = "1001"; }
        }

        // 카카오 알림톡 전송
        if (!"".equals(kakao_msg)) {

            sendResult = sendSMS(memb_nm, hp_no, trns_mmt, kakao_msg);

            if ("fail".equals(sendResult)) { rslt_cd = "2001"; }
        }

        selectMap.put("RSLT_CD" , rslt_cd             );
        selectMap.put("RSLT_MSG", getMessage(rslt_cd) );
        return selectMap;
    }

    public String sendPush(String escr_m_key, String fcm_id, String dvcKnd, String title, String pushMsg, int msg_prtt_i_key) {

        try {

            HashMap<String, String> pushMap = new HashMap<String, String>();

            pushMap.put("escrMKey"   , escr_m_key);
            pushMap.put("fcmId"      , fcm_id    );
            pushMap.put("notiTtl"    , title     );
            pushMap.put("notiCnts"   , pushMsg   );
            pushMap.put("dvcKnd"     , dvcKnd    );
            pushMap.put("msgPrttIKey", msg_prtt_i_key+"");

//            pushService.sendPush(pushMap);

        } catch (Exception Ex) {
            Ex.printStackTrace();
            log.error("=====>> sendPush Exception \n" + Ex.getMessage());
            return "fail";
        }

        return "success";
    }

    public String sendSMS( String name, String mobile , String title, String smsMsg) {

        try {

            log.debug("=====>> name [" + name + "]  mobile [" + mobile + "]  title [" + title + "]  smsMsg [" + smsMsg + "]");

            HashMap<String, String> smsMap = new HashMap<String, String>();

            smsMap.put("name"  , name  );
            smsMap.put("mobile", mobile);
            smsMap.put("note1" , ""    );
            smsMap.put("note2" , ""    );
            smsMap.put("note3" , ""    );
            smsMap.put("note4" , ""    );
            smsMap.put("note5" , ""    );

            JSONObject jsonObj  = new  JSONObject(smsMap);
            String     receiver    = "[" + jsonObj.toJSONString() + "]";

            smsMsg  = "[" + title + "]\n" + smsMsg;

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("title"  , title   );
            params.put("message", smsMsg  );
            params.put("receiver",receiver);

            smsService.sendSms(params);
        } catch (Exception Ex) {
            Ex.printStackTrace();
            log.error("=====>> sendSMS Exception \n" + Ex.getMessage());
            return "fail";
        }

        return "success";
    }

    public String getMessage(String rslt_cd) {

        String rslt_msg = "메세지 전송 오류 [" + rslt_cd + "]";

        if      ("0000".equals(rslt_cd)) { rslt_msg = "정상";             }
        else if ("1001".equals(rslt_cd)) { rslt_msg = "PUSH 전송 오류";   }
        else if ("2001".equals(rslt_cd)) { rslt_msg = "알림톡 전송 오류"; }

        return rslt_msg;
    }

    public void sendSysMngSMS(String title, String smsMsg) {

        String env = StringUtil.nvl(System.getProperty("spring.profiles.active"));
        if ("".equals(env)) { env = "dev";}

        if ("prod".equals(env) || "dev1".equals(env)) {

            String [] listName = sysMngProp.getListMngName();
            String [] listHpNo = sysMngProp.getListMngHpNo();
            int       listCnt  = listName.length;

            for(int i=0; i < listCnt; i++) {
                sendSMS(listName[i], listHpNo[i], title, smsMsg);
            }
        }
    }
}
