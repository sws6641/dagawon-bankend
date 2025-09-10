package com.bankle.common.comBiz.kcb.svc;
import com.bankle.common.comBiz.kcb.vo.KcbCvo;
import com.bankle.common.comBiz.kcb.vo.KcbSvo;
import com.bankle.common.config.ApiConfig;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.util.StringUtil;
import jakarta.validation.Valid;
import kcb.org.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class KcbSvc {

    /**
     * KCB인증 거래요청
     *
     * @param  : 서비스구분코드, 성명, 통신사 코드, 휴대폰번호, 생년월일, 주민번호 뒷자리, SMS 재전송 여부, 개인정보 수집/이용/취급위탁 동의, 고유식별정보처리 동의, 본인확인서비스 이용약관, 통신사 이용약관 동의, 거래일련번호 (재전송 시 필요)
     * @return : 결과코드, 결과메세지, 거래일련번호
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public KcbSvo.KcbTrOutSvo fidKcbTrReq(@Valid KcbSvo.KcbTrInSvo inVo) throws Exception {
        KcbSvo.KcbTrOutSvo OutSvo = null;
        try {
            log.debug("===================================KCB 휴대폰 본인인증 [거래요청] Start !!!========================================");
            String trSqn = StringUtil.nvl(inVo.getTrSqn()); // 거래일련번호는 재전송 시에만 필요한 값이다.
            String sex = ("91357".indexOf(inVo.getRegBackNo()) > 0 ? "M" : "F"); // 성별 (남)
            String ntvFrnrDsc = ("901234".indexOf(inVo.getRegBackNo()) > 0 ? "L" : "F"); // 내외국인 구분코드 (내국인)
            String srvcDsc = "0" + inVo.getSrvcDsc(); // 서비스 코드
            String chnlCd = ("01".equals(srvcDsc) ? "0000" : "9999"); //체널코드
            String serverIP = getServerIP();
            //거래 요청데이터(json)
            String reqStr = "";
            //kcb 거래요청 데이터
            HashMap<String, Object> kcbTrReq = new HashMap<String, Object>();
            kcbTrReq.put("srvc_dsc", srvcDsc);
            kcbTrReq.put("NAME", inVo.getMembNm());
            kcbTrReq.put("TEL_COM_CD", inVo.getTelopCd());
            kcbTrReq.put("TEL_NO", inVo.getHpNo());
            kcbTrReq.put("BIRTHDAY", inVo.getBirthDt());
            kcbTrReq.put("SEX_CD", sex);
            kcbTrReq.put("NTV_FRNR_CD", ntvFrnrDsc);
            kcbTrReq.put("SMS_RESEND_YN", inVo.getSmsRsndYn());
            kcbTrReq.put("AGREE1", inVo.getAgree1());
            kcbTrReq.put("AGREE2", inVo.getAgree2());
            kcbTrReq.put("AGREE3", inVo.getAgree3());
            kcbTrReq.put("AGREE4", inVo.getAgree4());

            if (!"".equals(trSqn)) {
                kcbTrReq.put("TX_SEQ_NO", trSqn);   // 거래일련번호 (재전송 시 필요)
            }
            kcbTrReq.put("USER_IP", serverIP);
            kcbTrReq.put("SITE_URL", ApiConfig.KCB_SITE_URL);
            kcbTrReq.put("SITE_NAME", ApiConfig.KCB_SITE_NAME);
            kcbTrReq.put("RQST_CAUS_CD", "99");
            kcbTrReq.put("CHNL_CD", chnlCd);
            //kcb.org.json
            JSONObject reqJson = new JSONObject(kcbTrReq);
            reqStr = reqJson.toString();
            log.debug("KCB 인증 거래요청 데이터 : " + reqStr);
            //kcb 호출
            JSONObject resJson = callKCB(srvcDsc, reqStr);
            log.debug("KCB 인증 거래응답 데이터 : " + resJson.toString());
            OutSvo = new KcbSvo.KcbTrOutSvo();
            OutSvo.setRsltCd(resJson.getString("RSLT_CD")); //결과코드 B000: 정상
            String rsltMsg = ""; //결과메세지
            try {
                rsltMsg = resJson.getString("RSLT_MSG");
            } catch (Exception Ex) {
                rsltMsg = "";
            }
            OutSvo.setRsltMsg(rsltMsg);
            OutSvo.setTrSqn(resJson.getString("TX_SEQ_NO")); //// 거래일련번호
            log.debug("===================================KCB 휴대폰 본인인증 [거래요청] end !!!========================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OutSvo;
    }

    /**
     * 본인인증결과
     *
     * @param :서비스 구분코드,거래일련번호,인증번호,휴대포번호
     * @return : 결과 코드,결과 메시지,거래 일련번호,CI 갱신 여부,성함,휴대폰번호,통시사코드,생년월일,성별코드,내/외국인 코드,DI,CI
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public KcbSvo.KcbRsltOutSvo fidKcbRsltReq(@Valid KcbSvo.KcbRsltInSvo inVo) throws Exception {
        KcbSvo.KcbRsltOutSvo OutSvo = new KcbSvo.KcbRsltOutSvo();
        try {
            log.debug("===================================KCB 휴대폰 본인인증 [결과 수신] Start !!!========================================");
            String srvcDsc = "1" + inVo.getSrvcDsc(); //서비스 구분코드
            String trSqn = StringUtil.nvl(inVo.getTrSqn()); //거래일련번호
            HashMap<String, Object> kcbRsltReq = new HashMap<>();
            kcbRsltReq.put("TEL_NO", inVo.getHpNo());
            kcbRsltReq.put("TX_SEQ_NO", trSqn);
            kcbRsltReq.put("OTP_NO", inVo.getSmsCtfcNo());
            JSONObject reqJson = new JSONObject(kcbRsltReq);
            String reqStr = reqJson.toString();
            JSONObject resJson = callKCB(srvcDsc, reqStr);
            String ciUpdate = StringUtil.nvl(resJson.getString("CI_UPDATE"));
            String rsltCd = resJson.getString("RSLT_CD"); //B000 : 본인인증성공
            OutSvo.setTrSqn(trSqn);
            OutSvo.setRsltCd(rsltCd);
            OutSvo.setRsltMsg(resJson.getString("RSLT_MSG"));
            OutSvo.setCiUpdate(ciUpdate);
            if ("B000".equals(rsltCd)) {
                OutSvo.setMembNm(StringUtil.nvl(resJson.getString("RSLT_NAME")));
                OutSvo.setHpNo(StringUtil.nvl(resJson.getString("TEL_NO")));
                OutSvo.setTelopCd(StringUtil.nvl(resJson.getString("TEL_COM_CD")));
                OutSvo.setBirthDt(StringUtil.nvl(resJson.getString("RSLT_BIRTHDAY")));
                OutSvo.setSex(StringUtil.nvl(resJson.getString("RSLT_SEX_CD")));
                OutSvo.setNtvFrnrDsc(StringUtil.nvl(resJson.getString("RSLT_NTV_FRNR_CD")));
                OutSvo.setDi(StringUtil.nvl(resJson.getString("DI")));
                OutSvo.setCi(StringUtil.nvl(resJson.getString("CI")));
            }
            log.debug("본인인증 결과 : " + OutSvo.toString());
            log.debug("===================================KCB 휴대폰 본인인증 [결과 수신] End !!!========================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OutSvo;
    }

    public String getServerIP() {
        String hostAddr = "";
        try {
            Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
            while (nienum.hasMoreElements()) {
                NetworkInterface         ni = nienum.nextElement();
                Enumeration<InetAddress> kk = ni.getInetAddresses();

                while (kk.hasMoreElements()) {
                    InetAddress inetAddress = kk.nextElement();
                    if (!inetAddress.isLoopbackAddress() &&
                            !inetAddress.isLinkLocalAddress() &&
                            inetAddress.isSiteLocalAddress()) {
                        hostAddr = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostAddr;
    }

    /**
     * KCB호출 메서드
     *
     * @param  : 서비스구분코드 ,  요청 데이터
     * @return : boolean
     * @throws
     */
    public kcb.org.json.JSONObject callKCB (String srvc_dsc, String reqStr) throws Exception {

        log.debug("===>> KCB Confirm Request !!!\n" + reqStr);

        String svcName       = ""; // 1 : SMS 방식,    2 : PASS 방식

        switch(srvc_dsc) {
            case "00":  // 거래요청 [  POPU 방식 ]
                svcName = "IDS_HS_POPUP_START";
                break;
            case "01":  // 거래요청 [ SMS  방식 ]
                svcName = "IDS_HS_EMBED_SMS_REQ";
                break;
            case "02":  // 거래요청 [ PASS 방식 ]
                svcName = "IDS_HS_EMBED_SIMPLE_REQ";
                break;
            case "10":  // 결과수신 [  POPU 방식 ]
                svcName = "IDS_HS_POPUP_RESULT";
                break;
            case "11":  // 결과수신 [ SMS  방식 ]
                svcName = "IDS_HS_EMBED_SMS_CIDI";
                break;
            case "12":  // 결과수신 [ PASS 방식 ]
                svcName = "IDS_HS_EMBED_SIMPLE_CIDI";
                break;

            default:
                break;
        }

        kcb.module.v3.OkCert okCert = new kcb.module.v3.OkCert();
        String resultStr;
        if (System.getProperty("spring.profiles.active").equals("local")) {
            resultStr = okCert.callOkCert(ApiConfig.KCB_TARGET, ApiConfig.KCB_CP_CD, svcName, "Z:\\temp/V55590000000_IDS_01_PROD_AES_license.dat", reqStr);
        } else {
            resultStr = okCert.callOkCert(ApiConfig.KCB_TARGET, ApiConfig.KCB_CP_CD, svcName, ApiConfig.KCB_LICENSE, reqStr);
        }
        log.debug("===>> KCB Confirm Response !!!\n" + resultStr);
        return new JSONObject(resultStr);
    }

    /**
     *  kcb 팝업서비스 거래요청 서비스
     * * @name     : edtStatCd
     * @return   : boolean
     **/
    @Transactional(rollbackFor = Exception.class)
    public KcbSvo.PopUpKcbTrOutSvo KcbPopUpAuthReq() throws Exception {
        KcbSvo.PopUpKcbTrOutSvo outSvo;
        try {
            log.debug("====================================================================================================");
            log.debug("====================================================================================================");
            log.debug("KCB 휴대폰 본인인증 [거래요청] Start !!!");
            // return 받을 api url
            // String returnUrl = CommonConfig.BASE_URL_API + "/auth/searchpopupkcbres";
            String returnUrl = "https://appwooridev.kosapp.co.kr/view/searchcntr/image";
            String srvcDsc = "00";
            JSONObject reqJson = new JSONObject();
            reqJson.put("RETURN_URL", returnUrl);
            reqJson.put("SITE_NAME", ApiConfig.KCB_SITE_NAME);
            reqJson.put("SITE_URL", ApiConfig.KCB_SITE_URL);
            reqJson.put("RQST_CAUS_CD", srvcDsc);

            String reqStr = reqJson.toString();

            //kcb 호출
            JSONObject resJson = callKCB(srvcDsc, reqStr);

            log.debug("KCB 인증 거래응답 데이터 : " + resJson.toString());

            String mdlTkn = "";
            String chkAuthYN = "N";
            String rsltCd = resJson.getString("RSLT_CD"); //B000일때 성공

            if ("B000".equals(rsltCd) && resJson.has("MDL_TKN")) {
                mdlTkn = resJson.getString("MDL_TKN");
                chkAuthYN = "Y";
            }
            outSvo = KcbSvo.PopUpKcbTrOutSvo.builder().
                    chkAuthYn(chkAuthYN).
                    kcbCpCd(ApiConfig.KCB_CP_CD).
                    kcbPopUrl(ApiConfig.KCB_POP_URL).
                    mdlTkn(mdlTkn).
                    rsltCd(rsltCd). //결과 코드
                            rsltMsg(resJson.getString("RSLT_MSG")). //결과메세지
                            trSqn(resJson.getString("TX_SEQ_NO")). //거래 일련번호
                            build();
            log.debug("KCB 휴대폰 본인인증 [거래요청] End !!!");
        }catch (Exception e){
            e.printStackTrace();
            throw new DefaultException("본인인증 중 오류 발생 :" + e.getMessage());
        }
        return outSvo;
    }

    /**
     *  kcb 팝업 본인인증 결과
     * * @name     : edtStatCd
     * @return   : boolean
     **/
    @Transactional(rollbackFor = Exception.class)
    public KcbSvo.KcbRsltOutSvo KcbPopUpAuthRes(@Valid KcbSvo.KcbPopUpRsltInSvo req) throws Exception {
        KcbSvo.KcbRsltOutSvo outSvo = new  KcbSvo.KcbRsltOutSvo();
        log.debug("====================================================================================================");
        log.debug("====================================================================================================");
        log.debug("KCB 휴대폰 본인인증 [결과 수신] Start !!!");
        try {
            JSONObject reqJson = new JSONObject();
            reqJson.put("MDL_TKN", req.getMdlTkn());
            String reqStr = reqJson.toString();
            //kcb 호출
            JSONObject resJson = callKCB("10", reqStr);
            //결과코드
            String rsltCd = resJson.getString("RSLT_CD"); //B000 : 본인인증성공
            outSvo.setTrSqn(resJson.getString("TX_SEQ_NO")); //거래 일련번호
            outSvo.setRsltCd(rsltCd);
            outSvo.setRsltMsg(resJson.getString("RSLT_MSG"));
            if ("B000".equals(rsltCd)) {
                outSvo.setMembNm(StringUtil.nvl(resJson.getString("RSLT_NAME")));
                outSvo.setHpNo(StringUtil.nvl(resJson.getString("TEL_NO")));
                outSvo.setTelopCd(StringUtil.nvl(resJson.getString("TEL_COM_CD")));
                outSvo.setBirthDt(StringUtil.nvl(resJson.getString("RSLT_BIRTHDAY")));
                outSvo.setSex(StringUtil.nvl(resJson.getString("RSLT_SEX_CD")));
                outSvo.setNtvFrnrDsc(StringUtil.nvl(resJson.getString("RSLT_NTV_FRNR_CD")));
                outSvo.setDi(StringUtil.nvl(resJson.getString("DI")));
                outSvo.setCi(StringUtil.nvl(resJson.getString("CI")));
            }
            log.debug("본인인증 결과 : " + outSvo.toString());
            log.debug("===================================KCB 휴대폰 본인인증 [결과 수신] End !!!========================================");
            log.debug("KCB 휴대폰 본인인증 [결과수신] End !!!");
            log.debug("====================================================================================================");
        } catch (Exception e){
            e.printStackTrace();
            throw new DefaultException("본인인증 결과 수신중 오류발생 ===>" + e.getMessage());
        }
        return outSvo;
    }
}

