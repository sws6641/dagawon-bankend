//package kr.co.anbu.controller.connection;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import kr.co.anbu.component.FaEncryptDecrypt;
//import kr.co.anbu.component.FaRestfulManager;
//import kr.co.anbu.component.properties.FaProperties;
//import kr.co.anbu.domain.service.FARcvService;
//import kr.co.anbu.domain.service.extnLk.ExtnLkService;
//import kr.co.anbu.infra.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class FARcvController {
//
//    private final FaProperties faProp;
//    private final FARcvService faRcvService;
//    HashMap<String, Object>    validOKJsonMap;
//
//    private final ExtnLkService  extnLkService;
//    private final Response       response;
//
//    /*===========================================================================================*/
//    // faRschRslt : 조사 결과
//    /*===========================================================================================*/
//    @RequestMapping("/report")
//    public ResponseEntity<String> faRschRslt(
//                                                @RequestHeader (value="IP"            ) String strIp
//                                              , @RequestHeader (value="HMAC"          ) String strHmac
//                                              , @RequestHeader (value="X-FA-Timestamp") String strTimestamp
//                                              , @RequestHeader (value="X-FA-VISA-ID"  ) String strPubKey
//                                              , @RequestHeader (value="X-FA-VISA"     ) String strApiKey
//                                              , @RequestBody                      final String encodedJsonData
//                                            ) {
//
//        log.info("====================================================================================================");
//        log.info("FA 조사 결과 Start !!!");
//
//        FaRestfulManager faRestfulManager = new FaRestfulManager(faProp);
//        JSONObject       jsonObj          = checkValidation("1", strIp, strHmac, strTimestamp, strApiKey, strPubKey, encodedJsonData);
//
//        /*=======================================================================================*/
//        /* 송신 전문 저장                                                                        */
//        /*=======================================================================================*/
//        String escr_m_key = "";
//
//        if (validOKJsonMap != null) {
//            escr_m_key = (String)validOKJsonMap.get("VMngCode");
//        }
//
////        HashMap<String, Object> paramMap = new HashMap<String, Object>();
////
////        paramMap.put("tg_cd"     , "FA22"    );  // FA 연계 - 조사서 수신 전문
////        paramMap.put("escr_m_key", escr_m_key);  // FA 연계 - 조사서 수신 전문
////        long  tg_log_m_key   =  extnLkService.saveTg(paramMap, validOKJsonMap);
//
//        if ("success".equals(jsonObj.get("result"))) {
//
//            faRcvService.updateRschRslt(validOKJsonMap);
//        }
//
//        log.debug("=====>> responseData [" + jsonObj.toJSONString() + "]");
//
//        String strBody    = faRestfulManager.makeBody(jsonObj.toJSONString());
//
//        log.debug("=====>> responseBody [" + strBody                + "]");
//
////      paramMap.put("tg_cd"           , "FA21"                  );  // FA 연계 - 조사서 수신 전문
////      paramMap.put("tg_log_m_key"    , tg_log_m_key            );
////      paramMap.put("snd_rcv_rslt"    , jsonObj.get("result"   ));
////      paramMap.put("snd_rcv_rslt_msg", jsonObj.get("errordesc"));
////      extnLkService.saveTg(paramMap, resMap);
//
//        log.info("FA 조사 결과 End !!!");
//        log.info("====================================================================================================");
//
//        if ("success".equals(jsonObj.get("result"))) {
//            return new ResponseEntity<String>(strBody, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<String>(strBody, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /*===========================================================================================*/
//    // faIsrnScrtPrt : 보험증권발행
//    /*===========================================================================================*/
//    @RequestMapping("/certification")
//    public ResponseEntity<String> faIsrnScrtPrt (
//                                                    @RequestHeader (value="IP"            ) String strIp
//                                                  , @RequestHeader (value="HMAC"          ) String strHmac
//                                                  , @RequestHeader (value="X-FA-Timestamp") String strTimestamp
//                                                  , @RequestHeader (value="X-FA-VISA-ID"  ) String strPubKey
//                                                  , @RequestHeader (value="X-FA-VISA"     ) String strApiKey
//                                                  , @RequestBody                      final String encodedJsonData
//                                                ) {
//
//        log.info("====================================================================================================");
//        log.info("FA 보험증권발행 Start !!!");
//
//        FaRestfulManager   faRestfulManager = new FaRestfulManager(faProp);
//        JSONObject jsonObj = checkValidation("2", strIp, strHmac, strTimestamp, strApiKey, strPubKey, encodedJsonData);
//
//        if ("success".equals(jsonObj.get("result"))) {
//
//            faRcvService.updateIsrnScrtPrt(validOKJsonMap);
//        }
//
//        log.debug("=====>> responseData [" + jsonObj.toJSONString() + "]");
//
//        String strBody = faRestfulManager.makeBody(jsonObj.toJSONString());
//
//        log.debug("=====>> responseBody [" + strBody + "]");
//
//        log.info("FA 보험증권발행 End !!!");
//        log.info("====================================================================================================");
//
//        if ("success".equals(jsonObj.get("result"))) {
//            return new ResponseEntity<String>(strBody, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<String>(strBody, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//	public JSONObject checkValidation(String jobGbn, String strIp, String strHmac, String strTimestamp, String strApiKey, String strPubKey, String encodedJsonData) {
//
//        String strTtl = "";
//        if      ("1".equals(jobGbn)) { strTtl = "조사 결과"; }
//        else if ("2".equals(jobGbn)) { strTtl = "중권 발행"; }
//
//
//        JSONObject jsonObj = new JSONObject();
//
//        FaEncryptDecrypt faEncryptDecrypt = new FaEncryptDecrypt();
//        String           decodedApiKey    = faEncryptDecrypt.Decrypt(strPubKey, strApiKey);
//        FaRestfulManager faRestfulManager = new FaRestfulManager(faProp);
//
//
//        jsonObj = faRestfulManager.checkValidation(strIp, strHmac, strTimestamp, strPubKey, decodedApiKey, encodedJsonData);
//
//
//        if(encodedJsonData == null) {
//
//            jsonObj.clear();
//            jsonObj.put("result", "error");
//            jsonObj.put("errordesc", "수신된 " + strTtl + " 데이터가 없습니다.");
//
//        } else {
//
//            if (jsonObj.get("result").equals("success")) {
//
//                String decodedJsonData = faEncryptDecrypt.Decrypt(faProp.getApiKey(), encodedJsonData);
//
//                log.info("================================================================================================");
//                log.info("=====>> FARcvController Decrypt Json Data\n" + decodedJsonData);
//                log.info("================================================================================================");
//
//                try {
//
//                    validOKJsonMap = parseJsonData(decodedJsonData);
//
//                    //수신받은 "VMngCode"가 계약 테이블에 있는지 확인한다.
//                    HashMap<String, Object> paramMap = new HashMap<String, Object>();
//                    HashMap<String, Object> rtnMap   = new HashMap<String, Object>();
//
//                    paramMap.put("escr_m_key"  , (String)validOKJsonMap.get("VMngCode" ));
//                    paramMap.put("isrn_scrt_no", (String)validOKJsonMap.get("PolicyNum"));
//                    paramMap.put("jobGbn"      , jobGbn                                 );
//
//
//                    log.info("=====>> checkValidation   escr_m_key [" + (String)paramMap.get("escr_m_key") +"]   isrn_scrt_no [" + (String)paramMap.get("isrn_scrt_no") + "]");
//
//                    rtnMap = faRcvService.checkReqEscr(paramMap);
//
//                    log.info("=====>> checkValidation   result [" + (String)rtnMap.get("result") +"]   errordesc [" + (String)rtnMap.get("errordesc") + "]");
//
//                    int chkValue = Integer.parseInt((String)rtnMap.get("result"));
//
//                    jsonObj.clear();
//                    if (0 == chkValue) {
//                        jsonObj.put("result"   , "success");
//                        jsonObj.put("errordesc", "");
//                    } else {
//                        jsonObj.put("result"   , "error");
//                        jsonObj.put("errordesc", rtnMap.get("errordesc"));
//                    }
//
//                } catch (Exception Ex) {
//                    jsonObj.clear();
//                    jsonObj.put("result"   , "error");
//                    jsonObj.put("errordesc", "JSON Data Parsing Error !!!");
//                    Ex.printStackTrace();
//                }
//            }
//        }
//
//        return jsonObj;
//	}
//
//    @SuppressWarnings("unchecked")
//    private HashMap<String, Object> parseJsonData(String jsonData) throws Exception {
//
//        Object     object     = null;
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObj    = new JSONObject();
//
//log.info("=====>> jsonData [" + jsonData + "]");
//
//        object  = jsonParser.parse(jsonData);
//
//log.info("=====>> parseJsonData Debug1");
//
//        jsonObj = (JSONObject)object;
//
//log.info("=====>> parseJsonData Debug2");
//
//        HashMap<String, Object> replaceJSonMap = new ObjectMapper().readValue(jsonObj.toJSONString(), HashMap.class);
//log.info("=====>> parseJsonData Debug3");
//        return replaceJSonMap;
//    }
//}
//
