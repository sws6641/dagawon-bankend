//package kr.co.anbu.controller.connection;
//
//import kcb.org.json.JSONObject;
//import kr.co.anbu.component.properties.KcbProperties;
//import kr.co.anbu.infra.Response;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/kcb")
//public class KcbHpAuthEmbedController {
//
//    private final Response      response;
//    private final KcbProperties kcbprop;
//
//    //=================================================================================================//
//    // Func Name : reqKcbAuthConfirm  (KCB 인증 요청)                                                  //
//    // Parameter : srvc_dsc    (서비스구분코드[ 1 : SMS 방식, 2 : PASS 방식                        ] ) //
//    //             telop_cd    (통신사코드    [ SELECT * FROM  TEC_CD_D WHERE  GRP_CD = 'TELOP_CD' ] ) //
//    //             memb_nm     (성명                                                                 ) //
//    //             hp_no       (전화번호                                                             ) //
//    //             birth_dt    (생년월일 YYMMDD                                                      ) //
//    //             reg_back_no (주민번호뒷자리                                                       ) //
//    //             sms_rsnd_yn (SMS 재전송여부 Default="N"                                           ) //
//    //             tr_sqn      (거래일련번호 [재전송시 필요]                                         ) //
//    //             agree1~4    (서비스관련 정보이용동의여부                                          ) //
//    // RETURN    : rslt_cd     (결과코드 [ B000 ; 정상 ~~~                                     ]     ) //
//    //             rslt_msg    (결과메세지                                                           ) //
//    //             tr_sqn      (거래일련번호                                                         ) //
//    //=================================================================================================//
//	@PostMapping("/reqKcbAuthConfirm")
//    public ResponseEntity<?> reqKcbAuthConfirm(@RequestBody HashMap<String, Object> body, Errors errors) throws Exception {
//
//        log.debug("====================================================================================================");
//        log.debug("KCB 휴대폰 본인인증 [거래요청] Start !!!");
//
//        // KCB 본인확인 UI 입력 값 Setting
//        String srvc_dsc        =                     (String)body.get("srvc_dsc"          );    // 서비스구분코드 [1: SMS방식, 2: PASS방식]
//        String memb_nm         =                     (String)body.get("memb_nm"           );    // 성명
//        String telop_cd        =                     (String)body.get("telop_cd"          );    // 통신사 코드
//        String hp_no           =                     (String)body.get("hp_no"             );    // 휴대폰번호
//        String birth_dt        =                     (String)body.get("birth_dt"          );    // 생년월일 (YYMMDD)
//        String reg_back_no     =                     (String)body.get("reg_back_no"       );    // 주민번호 뒷자리
//        String sms_rsnd_yn     = StringCustUtils.nvl((String)body.get("sms_rsnd_yn" ), "N");    // SMS 재전송 여부
//        String agree1          = StringCustUtils.nvl((String)body.get("agree1"      ), "N");    // 개인정보 수집/이용/취급위탁 동의
//        String agree2          = StringCustUtils.nvl((String)body.get("agree2"      ), "N");    // 고유식별정보처리 동의
//        String agree3          = StringCustUtils.nvl((String)body.get("agree3"      ), "N");    // 본인확인서비스 이용약관
//        String agree4          = StringCustUtils.nvl((String)body.get("agree4"      ), "N");    // 통신사 이용약관 동의
//        String tr_sqn          = StringCustUtils.nvl((String)body.get("tr_sqn"      )     );    // 거래일련번호 (재전송 시 필요)
//        String sex             = ("91357" .indexOf(reg_back_no)>0?"M":"F"); // 성별 (남)
//        String ntv_frnr_dsc    = ("901234".indexOf(reg_back_no)>0?"L":"F"); // 내외국인 구분코드 (내국인)
//               srvc_dsc        = "0" + srvc_dsc;
//
//        /*=======================================================================================*/
//        /* 파라미터 Validation                                                                   */
//        /*=======================================================================================*/
//        String  errMsg  = "";     // 파라미터 Validation 에러 메세지
//                errMsg += StringCustUtils.validParam("3", memb_nm, "성명"      );  // 한글 + 영문
//                errMsg += StringCustUtils.validParam("1", hp_no  , "휴대폰번호");  // 숫자만
//
//        if (!"".equals(errMsg)) { return response.fail(errMsg, "fail", HttpStatus.BAD_REQUEST); }
//        /*=======================================================================================*/
//
//        HashMap<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("srvc_dsc"     , srvc_dsc     );   // 서비스구분코드 [1: SMS방식, 2: PASS방식]
//        paramMap.put("TEL_COM_CD"   , telop_cd     );   // 이름
//        paramMap.put("NAME"         , memb_nm      );   // 통신사코드
//        paramMap.put("TEL_NO"       , hp_no        );   // 휴대폰번호
//        paramMap.put("BIRTHDAY"     , birth_dt     );   // 생년월일
//        paramMap.put("SEX_CD"       , sex          );   // 성별
//        paramMap.put("NTV_FRNR_CD"  , ntv_frnr_dsc );   // 내외국인구분코드
//        paramMap.put("SMS_RESEND_YN", sms_rsnd_yn  );   // SMS재전송여부
//        paramMap.put("AGREE1"       , agree1       );   // 개인정보 수집/이용/취급위탁 동의
//        paramMap.put("AGREE2"       , agree2       );   // 고유식별정보처리 동의
//        paramMap.put("AGREE3"       , agree3       );   // 본인확인서비스 이용약관
//        paramMap.put("AGREE4"       , agree4       );   // 통신사 이용약관 동의
//
//        if (!"".equals(tr_sqn)) {
//            paramMap.put("TX_SEQ_NO"    , tr_sqn       );   // 거래일련번호 (재전송 시 필요)
//        }
//
//        String     reqStr      = setAuthConfirmParam(paramMap);
//
//        log.debug("===>> KCB Confirm Request !!!\n" + reqStr);
//
//        JSONObject resJson     = callKCB(srvc_dsc, reqStr);
//
//        log.debug("===>> KCB Confirm Response !!!\n" + resJson.toString());
//
//        LinkedHashMap<String, Object> resMap = new LinkedHashMap<String, Object>();
//        resMap.put("rslt_cd"  , resJson.getString("RSLT_CD"  ));   // 결과코드 (B000 : 정상)
//
//        /* KCB 에서 RSLT_MSG가 없이 넘어오는 경우가 있고 그때 Exception 발생..  try 처리로 변경  */
//        String rslt_msg = "";
//        try { rslt_msg = resJson.getString("RSLT_MSG" ); } catch (Exception Ex) { rslt_msg = ""; }
//
//        resMap.put("rslt_msg" , rslt_msg                      );   // 결과메세지
//        resMap.put("tr_sqn"   , resJson.getString("TX_SEQ_NO"));   // 거래일련번호
//
//        log.debug("=====>> response Map String\n" + resMap.toString());
//
//        log.debug("KCB 휴대폰 본인인증 [거래요청] End !!!");
//        log.debug("====================================================================================================");
//
//        return response.success(resMap, "success", HttpStatus.OK);
//    }
//
//    /**
//     * 휴대폰 본인인증 결과
//     * @param body
//     * @param errors
//     * @return
//     * @throws Exception
//     */
//    @PostMapping(value="/KcbHpAuthResult")
//    public ResponseEntity<?> KcbHpAuthResult(@RequestBody HashMap<String, Object> body, Errors errors) throws Exception {
//
//        log.debug("====================================================================================================");
//        log.debug("====================================================================================================");
//        log.debug("KCB 휴대폰 본인인증 [결과 수신] Start !!!");
//
//        String     srvc_dsc     =     (String)body.get("srvc_dsc"    );   // 서비스구분코드 [1: SMS방식, 2: PASS방식]
//        String     hp_no        =     (String)body.get("hp_no"       ) ;  // 흎대폰번호
//        String     tr_sqn       =     (String)body.get("tr_sqn"      ) ;  // 거래일련번호
//        String     sms_ctfc_no  = StringCustUtils.nvl((String)body.get("sms_ctfc_no" ));  // SMS 인증번호
//                   srvc_dsc     = "1" + srvc_dsc;
//
//        /*=======================================================================================*/
//        /* 파라미터 Validation                                                                   */
//        /*=======================================================================================*/
//        String  errMsg  = "";     // 파라미터 Validation 에러 메세지
//                errMsg += StringCustUtils.validParam("2", tr_sqn, "거래고유번호");  // 숫자 + 영문
//
//        if (!"".equals(errMsg)) { return response.fail(errMsg, "fail", HttpStatus.BAD_REQUEST); }
//
//        /*=======================================================================================*/
//
//        HashMap<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("TEL_NO"   , hp_no       );
//        paramMap.put("TX_SEQ_NO", tr_sqn      );
//        paramMap.put("OTP_NO"   , sms_ctfc_no );
//
//        String     reqStr       = setAuthResultParam(paramMap);
//
//        log.debug("===>> KCB Confirm Request !!!\n" + reqStr);
//
//        JSONObject resJson      = callKCB(srvc_dsc, reqStr);
//
//        log.debug("===>> KCB Confirm Response !!!\n" + resJson.toString());
//
//        String rslt_cd  = resJson.getString("RSLT_CD" );
//        String rslt_msg = resJson.getString("RSLT_MSG");
//
//
//        LinkedHashMap<String, Object> resMap = new LinkedHashMap<String, Object>();
//        resMap.put("tr_sqn"      , tr_sqn                                                    );
//        resMap.put("rslt_cd"     , rslt_cd );
//        resMap.put("rslt_msg"    , rslt_msg);
//        resMap.put("ci_update"   , StringCustUtils.nvl(resJson.getString("CI_UPDATE"       )));
//
//        if ("B000".equals(rslt_cd)) {
//            resMap.put("memb_nm"     , StringCustUtils.nvl(resJson.getString("RSLT_NAME"       )));
//            resMap.put("hp_no"       , StringCustUtils.nvl(resJson.getString("TEL_NO"          )));
//            resMap.put("telop_cd"    , StringCustUtils.nvl(resJson.getString("TEL_COM_CD"      )));
//            resMap.put("birth_dt"    , StringCustUtils.nvl(resJson.getString("RSLT_BIRTHDAY"   )));
//            resMap.put("sex"         , StringCustUtils.nvl(resJson.getString("RSLT_SEX_CD"     )));
//            resMap.put("ntv_frnr_dsc", StringCustUtils.nvl(resJson.getString("RSLT_NTV_FRNR_CD")));
//            resMap.put("di"          , StringCustUtils.nvl(resJson.getString("DI"              )));
//            resMap.put("ci"          , StringCustUtils.nvl(resJson.getString("CI"              )));
//
//        }
//
//
//
//        log.debug("=====>> response Map String\n" + resMap.toString());
//
//        log.debug("KCB 휴대폰 본인인증 [결과수신] End !!!");
//        log.debug("====================================================================================================");
//
//        return response.success(resMap, "success", HttpStatus.OK);
//    }
//
//    public String getServerIP() {
//
//        String hostAddr = "";
//
//        try {
//            Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
//
//            while (nienum.hasMoreElements()) {
//                NetworkInterface         ni = nienum.nextElement();
//                Enumeration<InetAddress> kk = ni.getInetAddresses();
//
//                while (kk.hasMoreElements()) {
//                    InetAddress inetAddress = kk.nextElement();
//                    if (!inetAddress.isLoopbackAddress() &&
//                        !inetAddress.isLinkLocalAddress() &&
//                        inetAddress.isSiteLocalAddress()) {
//                        hostAddr = inetAddress.getHostAddress().toString();
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//
//        return hostAddr;
//    }
//
//    public String setAuthConfirmParam(HashMap<String, Object> paramMap) {
//
//        String srvc_dsc = (String)paramMap.get("srvc_dsc");
//        String chnl_cd = ( "01".equals(srvc_dsc) ? "0000" : "9999" );
//
//        JSONObject reqJson = new JSONObject(paramMap);
//
//        // KCB 설정 값 Setting
//        reqJson.put("USER_IP"      , getServerIP()             );   // 서버 IP
//        reqJson.put("SITE_URL"     , kcbprop.getKcb_site_url ());   // 사이트 URL
//        reqJson.put("SITE_NAME"    , kcbprop.getKcb_site_name());   // 사이트 명
////        reqJson.put("SITE_NAME"    , "(주)코코스원"           );   // 사이트 명
//        reqJson.put("RQST_CAUS_CD" , "99"                      );   // 인증요청사유코드 (99:기타)
//        reqJson.put("CHNL_CD"      , chnl_cd                   );   // 채널 코드 (고정)
//
//        return reqJson.toString();
//    }
//
//    public String setAuthResultParam(HashMap<String, Object> paramMap) {
//
//        JSONObject reqJson = new JSONObject(paramMap);
//
//        return reqJson.toString();
//    }
//
//    public JSONObject callKCB (String srvc_dsc, String reqStr) throws Exception {
//
//        String svcName       = ""; // 1 : SMS 방식,    2 : PASS 방식
//        if      ("01".equals(srvc_dsc)) { svcName = "IDS_HS_EMBED_SMS_REQ";     }  // 거래요청 [ SMS  방식 ]
//        else if ("02".equals(srvc_dsc)) { svcName = "IDS_HS_EMBED_SIMPLE_REQ";  }  // 거래요청 [ PASS 방식 ]
//        else if ("11".equals(srvc_dsc)) { svcName = "IDS_HS_EMBED_SMS_CIDI";    }  // 결과수신 [ SMS  방식 ]
//        else if ("12".equals(srvc_dsc)) { svcName = "IDS_HS_EMBED_SIMPLE_CIDI"; }  // 결과수신 [ PASS 방식 ]
//
//        kcb.module.v3.OkCert okcert    = new kcb.module.v3.OkCert();
//        String               resultStr = okcert.callOkCert(kcbprop.getKcb_target(), kcbprop.getKcb_cp_cd(), svcName, kcbprop.getKcb_license(),  reqStr);
//
//        return new JSONObject(resultStr);
//    }
//}
//
