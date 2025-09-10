package com.bankle.common.asis.domain.service.extnLk;

import com.bankle.common.asis.component.FaEncryptDecrypt;
import com.bankle.common.asis.component.FaRestfulManager;
import com.bankle.common.asis.component.properties.FaProperties;
import com.bankle.common.asis.domain.mapper.IfFaCmnMapper;
import com.bankle.common.asis.domain.mapper.IfFaSndMapper;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.util.GsonUtil;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExtnLkFAService {

    private final FaProperties faProp;
    private final IfFaCmnMapper ifFaCmnMapper;
    private final IfFaSndMapper ifFaSndMapper;
    private final IfTgInfoMapper ifTgInfoMapper;
    private final ExtnLkService extnLkService;
    private final LguFirmBankingService lfbService;

    private HttpMethod faHttpMethod;  // FA 연결 Method
    private String faUrl;    // FA 연결 URL
    private String tg_item_cd;
    private String tg_item_nm;

    /*===========================================================================================*/
    /* Func Name : callFA [ FA  전문 송수신 ]                                                    */
    /* Param     : tg_cd      : 전문코드                                                         */
    /*             escr_m_key : 에크스로 기본 키                                                 */
    /* Return    : map        : 결과코드  ( 0000 : 정상, 그외 오류 )                             */
    /*===========================================================================================*/
    public HashMap<String, Object> callFA(HashMap<String, Object> paramMap) {

        HashMap<String, Object> rsltMap = new HashMap<String, Object>();
        rsltMap.put("rslt_cd", "0000");
        rsltMap.put("rslt_msg", "정상");

        /*=======================================================================================*/
        /* Parameter 및 변수 설정                                                                */
        /*=======================================================================================*/
        paramMap.put("vendor_code", faProp.getVendorCode());  // FA Vendor Code

        String tg_cd = StringUtil.nvl((String) paramMap.get("tg_cd"));
        long escr_m_key = (long) paramMap.get("escr_m_key");
        String chk_msg = "";

        if ("".equals(tg_cd) || escr_m_key == 0) {

            chk_msg = "\ntg_cd [" + tg_cd + "]     escr_m_key [" + escr_m_key + "]\n전문코드 또는 에스크로기본키 값이 Null 입니다.";

            rsltMap.put("rslt_cd", "0001");
            rsltMap.put("rslt_msg", chk_msg);
            return rsltMap;
        }

        /*=======================================================================================*/
        /* 에스크로 Validation                                                                   */
        /*=======================================================================================*/
        HashMap<String, Object> selectMap = ifFaCmnMapper.selectValidEscrSscpt(paramMap);

        if (selectMap == null) {
            chk_msg = "callFA [ errorCode : 0002 ]\n에스크로 정보 / 회원 정보를 확인하시기 바랍니다.";
            log.info("=====>> " + chk_msg);
            rsltMap.put("rslt_cd", "0001");
            rsltMap.put("rslt_msg", chk_msg);
            return rsltMap;
        } else {
            chk_msg = (String) selectMap.get("CHK_MSG");

            log.info("===>> chk_msg : " + chk_msg);

            if (!"OK".equals(chk_msg)) {

                log.info("=====>> callFA [ errorCode : 0002 ]\n" + chk_msg);
                rsltMap.put("rslt_cd", "0001");
                rsltMap.put("rslt_msg", chk_msg);
                return rsltMap;
            }
        }

        /*=======================================================================================*/
        /* FA 송신전문 생성                                                                      */
        /*=======================================================================================*/
        try {

            HashMap<String, Object> reqMap = new HashMap<String, Object>();

            /*===================================================================================*/
            /* FA 송신전문 데이터 조회                                                           */
            /*===================================================================================*/
            if ("FA01".equals(tg_cd)) {
                reqMap = ifFaSndMapper.selectEscrSscpt(paramMap);      // FA 연계 - 청약의뢰 송신 전문

                if (reqMap == null) {
                    chk_msg = "에스크로 관계자 정보가 없습니다.";
                    log.info("=====>> callFA [ errorCode : 9981 ]" + chk_msg);
                    rsltMap.put("rslt_cd", "9981");
                    rsltMap.put("rslt_msg", chk_msg);
                    return rsltMap;
                }
            } else if ("FA11".equals(tg_cd)) {
                reqMap = ifFaSndMapper.selectEscrSscpt(paramMap);
            }   // FA 연계 - 청약의뢰 송신 전문
            else if ("FA41".equals(tg_cd)) {
                reqMap.putAll(paramMap);
            }   // FA 연계 - Daily 조사결과요청 송신 전문
            else if ("FA51".equals(tg_cd)) {
                reqMap = ifFaSndMapper.selectEscrRomPmnt(paramMap);
            }   // FA 연계 - 에스크로 입출내역 송신 전문
            else if ("FA71".equals(tg_cd)) { /* setHttpConnInfo 함수에서 URL 증권번호 Setting */ }   // FA 연계 - 증권확인 송신 전문 [조사서  ] (파일스트림  Detail 테이블 없음)
            else if ("FA81".equals(tg_cd)) { /* setHttpConnInfo 함수에서 URL 증권번호 Setting */ }   // FA 연계 - 증권확인 송신 전문 [보험증권] (파일스트림  Detail 테이블 없음)
            else if ("FA91".equals(tg_cd)) {                                                         // FA 연계 - 청약취소 송신 전문                                                     
                reqMap.put("VendorCode", (String) paramMap.get("vendor_code"));
                reqMap.put("VMngCode", (String) paramMap.get("escr_m_key"));
                reqMap.put("CancelReason", (String) paramMap.get("cntrt_cncl_rsn_cd"));
                reqMap.put("Remark", (String) paramMap.get("cntrt_cncl_rsn_cnts"));
            } else if ("FAA1".equals(tg_cd)) { /* setHttpConnInfo 함수에서 URL 증권번호 Setting */ }   // FA 연계 - Alive Check
            else {

                chk_msg = "전문코드 (tg_cd : \" + tg_cd + \") 를 확인 하시기 바랍니다.";
                log.info("=====>> callFA [ errorCode : 1009 ]" + chk_msg);
                rsltMap.put("rslt_cd", "1009");
                rsltMap.put("rslt_msg", chk_msg);
                return rsltMap;
            }

            /*===================================================================================*/
            /* FA 전문 HttpMethod / URL Setting                                                  */
            /*===================================================================================*/
            int connInfo = setHttpConnInfo(paramMap);

            /*===================================================================================*/
            /* FA 전문 전 JSON ARRAY 처리                                                        */
            /*===================================================================================*/
            JSONObject sendJsonObj = chngJsonArray(paramMap, reqMap);

            /*===================================================================================*/
            /* FA 전문 송신 / 수신                                                               */
            /*===================================================================================*/
            log.info("METHOD [ " + faHttpMethod.toString() + " ]    FA 호출 URL : " + faUrl);
            Map<String, Object> resMap = sendFa(faUrl, faHttpMethod, paramMap, reqMap, sendJsonObj);

            String result = (String) resMap.get("result");
            String errordesc = (String) resMap.get("errordesc");
            String res_cd = "";
            int dbCnt = 0;

            /*===================================================================================*/
            /* FA 전문 송수신 오류                                                               */
            /*===================================================================================*/
            if ("success".equals(result)) {

                if ("FA01".equals(tg_cd)) {

                    /*===================================================================================*/
                    // FA 보험 정보 TET_ESCR_M UPDATE
                    /*===================================================================================*/
                    paramMap.put("isrn_scrt_no", (String) resMap.get("pnum"));  // 보험증권번호
                    paramMap.put("isrn_prmm", (String) resMap.get("premium"));  // 보험료
                    paramMap.put("isrn_rom_sqn", (String) resMap.get("depositNum"));  // 보험입금일련번호

                    dbCnt = ifFaSndMapper.updateEscrSscpt(paramMap);  // 전송전 Validation 으로 체크되어 ( dbCnt = 0 ) 체크하지 않음.
                    dbCnt = ifTgInfoMapper.insertBackupEscrM(paramMap);  // TET_ESCR_M 변경내역 TET_ESCR_H 에 복제

                    /*===================================================================================*/
                    // 보험료 입금처리 ( 펌뱅킹 )
                    /*===================================================================================*/

                    rsltMap = lfbService.sendIsrnPrmmTrn(escr_m_key);

                    res_cd = (String) rsltMap.get("RES_CD");

                    log.info("=====>> 보험료이체 faSscptAskReg escr_m_key [ " + escr_m_key + "]   rslt_cd [" + (String) rsltMap.get("RES_CD") + "]   rslt_msg [" + (String) rsltMap.get("RES_MSG") + "]");
                }
            } else {
                chk_msg = "[" + tg_cd + "] : [1001] " + errordesc;
                log.info("===>> " + chk_msg);
                rsltMap.put("rslt_cd", "1001");
                rsltMap.put("rslt_msg", chk_msg);
                return rsltMap;
            }

        } catch (NullPointerException NullEx) {
            chk_msg = "[" + tg_cd + "] : [8001]  [" + tg_item_cd + " " + tg_item_nm + "] 처리중 NullPointerException 오류가 발생했습니다.";
            log.info("===>> " + chk_msg);
            rsltMap.put("rslt_cd", "8001");
            rsltMap.put("rslt_msg", chk_msg);
        } catch (Exception Ex) {
            chk_msg = "[" + tg_cd + "] : [9001] " + Ex.getMessage();
            log.info("===>> " + chk_msg);
            rsltMap.put("rslt_cd", "9001");
            rsltMap.put("rslt_msg", chk_msg);
            Ex.printStackTrace();
        }

        return rsltMap;
    }

    /*===========================================================================================*/
    /* FA 전문 송수신                                                                            */
    /*===========================================================================================*/
    public Map<String, Object> sendFa(String url, HttpMethod httpMethod, HashMap<String, Object> paramMap, HashMap<String, Object> reqMap, JSONObject sendJsonObj) {

        log.debug("====================================================================================");
        log.debug("=====>> FA  Send Data \n" + sendJsonObj.toString());
        log.debug("====================================================================================");

        FaRestfulManager faRestfulManager = new FaRestfulManager(faProp);
        HttpHeaders httpHeader = faRestfulManager.makeHeader();
        String httpBody = faRestfulManager.makeBody(sendJsonObj.toJSONString());
        HttpEntity<String> entity = new HttpEntity<String>(httpBody, httpHeader);

        long tg_log_m_key = 0;
        /*=======================================================================================*/
        /* 송신 전문 저장                                                                        */
        /*=======================================================================================*/
        paramMap.put("tg_cd", (String) paramMap.get("tg_cd_s"));

        tg_log_m_key = extnLkService.saveTg(paramMap, reqMap);

        /*=======================================================================================*/
        /* Http Request                                                                          */
        /*=======================================================================================*/
        LinkedHashMap<String, Object> resMap = new LinkedHashMap<String, Object>();

        try {
            RestTemplate restTemplate = faRestfulManager.restTemplate();
            ResponseEntity<String> resEntity = restTemplate.exchange(url, httpMethod, entity, String.class);

            if (resEntity != null) {

                JSONObject resJson = decryptResponseBody(resEntity);

                resMap = GsonUtil.toLMap(resJson);

                log.debug("====================================================================================");
                log.debug("=====>> FA  Receive Data \n" + resJson.toString());
                log.debug("====================================================================================");
                StringUtil.printMapInfo(resMap);
                log.debug("====================================================================================");

                resMap.put("HttpStatus", resEntity.getStatusCode());
            } else {
                resMap.put("result", "error");
                resMap.put("errordesc", "수신 정보가 Null 입니다.");
            }
        } catch (Exception Ex) {
            resMap.put("result", "error");
            resMap.put("errordesc", "Exception : " + Ex.getMessage());
            Ex.printStackTrace();
        }

        /*=======================================================================================*/
        /* 수신 전문 저장                                                                        */
        /*=======================================================================================*/
        paramMap.put("tg_log_m_key", tg_log_m_key);
        paramMap.put("tg_cd", (String) paramMap.get("tg_cd_r"));
        paramMap.put("res_cd", (String) resMap.get("result"));
        paramMap.put("res_msg", (String) resMap.get("errordesc"));
        tg_log_m_key = extnLkService.saveTg(paramMap, resMap);

        return resMap;
    }

    /*===========================================================================================*/
    /* FA 측에서 전달되는 Response Body 에 쌍따옴표가 들어와 제거하여 JSON 리턴                  */
    /*===========================================================================================*/
    private JSONObject decryptResponseBody(ResponseEntity<String> resEntity) throws Exception {
        if (resEntity.getBody() != null) {
            FaEncryptDecrypt faEncryptDecrypt = new FaEncryptDecrypt();

            //FA측에서 주는 Body데이터에 쌍따옴표가 들어와 제거
            String tempStr = resEntity.getBody().replace("\"", "");
            String decodedJsonData = faEncryptDecrypt.Decrypt(faProp.getApiKey(), tempStr);

            if (!decodedJsonData.equals("Failed")) {

                Object object = null;
                JSONParser jsonParser = new JSONParser();
                JSONObject resJson = new JSONObject();

                object = jsonParser.parse(decodedJsonData);
                resJson = (JSONObject) object;

                System.out.println(resJson.toJSONString());
                return resJson;
            }
        }

        return null;
    }

    public int setHttpConnInfo(HashMap<String, Object> paramMap) {

        HashMap<String, Object> selectMap = ifTgInfoMapper.selectTgMEscr(paramMap);

        String escr_m_key = selectMap.get("ESCR_M_KEY").toString();
        String isrn_scrt_no = (String) selectMap.get("ISRN_SCRT_NO");
        String strMethod = (String) selectMap.get("TG_TRNS_MTD");
        String strUrl = (String) selectMap.get("TG_TRNS_URL");

        if ("POST".equals(strMethod)) {
            faHttpMethod = HttpMethod.POST;
        } else if ("PUT".equals(strMethod)) {
            faHttpMethod = HttpMethod.PUT;
        } else {
            faHttpMethod = HttpMethod.GET;
        }

        if (!"".equals(StringUtil.nvl(isrn_scrt_no))) {
            strUrl = strUrl.replaceAll("\\{isrn_scrt_no\\}", isrn_scrt_no);
        } else {
            strUrl = strUrl.replaceAll("\\{isrn_scrt_no\\}", "");
        }

        strUrl = strUrl.replaceAll("\\{escr_m_key\\}", escr_m_key);

        faUrl = faProp.getUrl() + strUrl;

        return 1;
    }

    @SuppressWarnings("unchecked")
    public JSONObject chngJsonArray(HashMap<String, Object> paramMap, HashMap<String, Object> reqMap) throws NullPointerException {

        log.debug("=====>> chngJsonArray tg_cd [" + paramMap.get("tg_cd").toString() + "]");

        ArrayList<HashMap<String, Object>> listTg = ifTgInfoMapper.selectTgD(paramMap);

        JSONArray jsonArr = new JSONArray();
        JSONObject sendJsonObj = new JSONObject();

        String json_arr_yn = "";   //  Json Array Change
        String tg_value = "";   //  Json Array Change

        for (HashMap<String, Object> map : listTg) {

            tg_item_cd = map.get("TG_ITM_CD").toString();
            tg_item_nm = map.get("TG_ITM_NM").toString();
            json_arr_yn = map.get("JSON_ARR_YN").toString();

            if ("Y".equals(json_arr_yn)) {

                jsonArr.clear();

                log.debug("=====>> tg_item_cd [" + tg_item_cd + "]");

                tg_value = (StringUtil.nvl(reqMap.get(tg_item_cd).toString())).trim();

                log.debug("=====>> tg_value [" + tg_value + "]");

                String[] arrSplit = tg_value.split(",");

                for (int i = 0; i < arrSplit.length; i++) {
                    jsonArr.add(arrSplit[i].toString());
                }

                sendJsonObj.put(tg_item_cd, jsonArr.toJSONString());

            } else {
                sendJsonObj.put(tg_item_cd, reqMap.get(tg_item_cd));
            }
        }

        return sendJsonObj;
    }
}