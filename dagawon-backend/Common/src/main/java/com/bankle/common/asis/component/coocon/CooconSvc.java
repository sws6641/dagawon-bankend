//package com.bankle.common.asis.component.coocon;
//
//import com.bankle.common.asis.domain.entity.TgLogMaster;
//import com.bankle.common.util.StringUtil;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustStrategy;
//import org.apache.http.impl.client.HttpClients;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.client.RestTemplate;
//
//import javax.net.ssl.*;
//import java.io.ByteArrayOutputStream;
//import java.io.DataInputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class CooconSvc {
////    private final AccountMapper accountMapper;
////    private final IfTgInfoMapper ifTgInfoMapper;
////    private final TgLogMasterService tgLogMasterService;
//    private RestTemplate restTemplate;
//
//    @Value("${coocon.url}")
//    private String cooconUrl;
//
//    @Value("${coocon.key}")
//    private String cooconKey;
//
//    private HttpURLConnection conn;
//    private SSLContext sc;
//    private String env;
//    private TgLogMaster tgLogMaster;
//
//
//    @Bean
//    public RestTemplate restTemplate() { //throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//
//        try {
//            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//                    .loadTrustMaterial(null, acceptingTrustStrategy)
//                    .build();
//
//            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//            var httpClient = HttpClients.custom()
//                    .setSSLSocketFactory(csf)
//                    .build();
//
//            HttpComponentsClientHttpRequestFactory requestFactory =
//                    new HttpComponentsClientHttpRequestFactory();
//
////            requestFactory.setHttpClient(httpClient);
//            restTemplate = new RestTemplate(requestFactory);
//
//        } catch (Exception Ex) {
//            log.error("================================================================================");
//            log.error("restTemplate Exception");
//            log.error("================================================================================");
//            Ex.printStackTrace();
//        }
//        return restTemplate;
//    }
//
//    /**
//     * 실명계좌 조회
//     *
//     * @param body
//     * @param response
//     * @return
//     * @throws IOException
//     * @throws NoSuchAlgorithmException
//     * @throws KeyManagementException
//     * @throws ParseException
//     */
//    public HashMap<String, Object> checkAccount(HashMap<String, Object> body, HttpServletResponse response) {
//
//        HashMap<String, Object> result = new HashMap<>();
//        HashMap<String, Object> jsonData = new HashMap<>();
//        try {
//            initialize(response);
//
//            jsonData = makeJson(body);
//
//            result = requestRealName(body, jsonData);
//
//            tgLogMaster.setEscrMKey(Long.parseLong(body.get("ESCR_M_KEY").toString()));
//
//            saveRequestData();
//
//        } catch (Exception Ex) {
//            log.error("================================================================================");
//            log.error("checkAccount Exception");
//            log.error("================================================================================");
//            result.put("RESULT_CODE", "9999");
//            result.put("RESULT_MESSAGE", "계좌인증 중 장애가 발생했습니다. [" + Ex.toString() + "]");
//            Ex.printStackTrace();
//        }
//        return result;
//    }
//
//
//    private void initialize(HttpServletResponse response) { //throws IOException, NoSuchAlgorithmException, KeyManagementException {
//
//        try {
//            env = System.getProperty("spring.profiles.active");
//            tgLogMaster = TgLogMaster.builder()
//                    .tgCd("AN01")
//                    .build();
//
//            response.setHeader("Cache-Control", "no-store");
//            response.setHeader("Pragma", "no-cache");
//            response.setDateHeader("Expires", 0);
//            response.setCharacterEncoding("UTF-8");
//
//
//            log.debug("=====>> Coocon Url [" + cooconUrl + "]");
//
//            conn = (HttpURLConnection) new URL(cooconUrl).openConnection();
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setUseCaches(false);
//
//            sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//        } catch (Exception Ex) {
//            log.error("================================================================================");
//            log.error("initialize Exception");
//            log.error("================================================================================");
//            Ex.printStackTrace();
//        }
//    }
//
//    TrustManager[] trustAllCerts = new TrustManager[]{
//            new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
//                }
//
//                @Override
//                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
//                }
//
//                @Override
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//            }
//    };
//
//    HostnameVerifier allHostsValid = (hostname, session) -> false;
//
//    /**
//     * 실명조회 전문생성
//     *
//     * @param params
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    private HashMap<String, Object> makeJson(HashMap<String, Object> params) {
//
//        if (params.isEmpty())
//            throw new RuntimeException("잘못된 요청입니다.");
//
//        if (StringUtils.hasText((String) params.get("BANK_CD")))
//            throw new RuntimeException("은행코드 값은 필수 입니다.");
//
//        if (StringUtils.hasText((String) params.get("SEARCH_ACCT_NO")))
//            throw new RuntimeException("계좌번호 값은 필수 입니다.");
//
//        HashMap<String, Object> returnMap = new HashMap<>();
//
//        JSONObject jsonObj = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObjDtl = new JSONObject();
//
//        jsonObjDtl.put("BANK_CD", params.get("BANK_CD"));
//        jsonObjDtl.put("SEARCH_ACCT_NO", ((String) params.get("SEARCH_ACCT_NO")).replaceAll("-", ""));
//        jsonObjDtl.put("ACNM_NO", params.get("ACNM_NO"));
//        jsonObjDtl.put("ICHE_AMT", params.get("ICHE_AMT"));
//        jsonObjDtl.put("TRSC_SEQ_NO", getTrscSeqNo());
//
//        jsonArray.add(jsonObjDtl);
//
//        jsonObj.put("SECR_KEY", cooconKey);
//        jsonObj.put("KEY", "ACCTNM_RCMS_WAPI");
//        jsonObj.put("REQ_DATA", jsonArray);
//
//        returnMap.put("REQ_DATA", jsonObjDtl.toJSONString());
//        returnMap.put("JSONDataVal", jsonObj.toJSONString());
//
//        String JSONDataVal = jsonObj.toJSONString();
//
//        log.info("===== 실명조회 요청 >>" + JSONDataVal);
//
//        tgLogMaster.setLkSndTg(JSONDataVal);
//        tgLogMaster.setLkSndDtm(LocalDateTime.now());
//
//        return returnMap;
//
//    }
//
//    /**
//     * response data
//     *
//     * @param result
//     * @return
//     * @throws IOException
//     */
//    private JSONObject getResData(HashMap<String, Object> result) { // throws IOException, ParseException {
//
//        String postString = "JSONData=" + result.get("JSONDataVal");
//
//        log.debug("===>> 쿠콘 Request Param [" + postString + "]");
//
//        String resData = "";
//        JSONObject jsonObj = null;
//
//        try {
//
//            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
//            os.write(postString);
//            os.close();
//
//            DataInputStream in = new DataInputStream(conn.getInputStream());
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//
//            byte[] buf = new byte[2048];
//
//            while (true) {
//                int n = in.read(buf);
//                if (n == -1) break;
//                bout.write(buf, 0, n);
//            }
//
//            bout.flush();
//
//            byte[] resMsg = bout.toByteArray();
//            String resMessage = new String(resMsg, "EUC-KR");
//
//            conn.disconnect();
//
//            log.debug("===>> 쿠콘 Response Body [" + resMessage + "]");
//
//            resMessage = resMessage.replaceAll("\r\n", "");
//            resMessage = resMessage.replaceAll("\r", "");
//            resMessage = resMessage.replaceAll("\n", "");
//            resData = resMessage.trim();
//
//            tgLogMaster.setLkRcvTg(resData);
//            tgLogMaster.setLkRcvDtm(LocalDateTime.now());
//
//            JSONParser jsonParse = new JSONParser();
//            jsonObj = (JSONObject) jsonParse.parse(resData);
//        } catch (Exception Ex) {
//
//            log.error("================================================================================");
//            log.error("getResData Exception");
//            log.error("Error Message [" + Ex.getMessage() + "]");
//            log.error("================================================================================");
//            Ex.printStackTrace();
//        }
//
//        return jsonObj;
//    }
//
//    /**
//     * 요청전문저장
//     */
//    private void saveRequestData() {
//
////        tgLogMasterService.save(tgLogMaster);
//    }
//
//    /**
//     * 결과메시지
//     *
//     * @param params
//     * @param result
//     * @return
//     * @throws IOException
//     * @throws ParseException
//     */
//    private HashMap<String, Object> requestRealName(HashMap<String, Object> params,
//                                                    HashMap<String, Object> result) { //throws IOException, ParseException {
//
//        HashMap<String, Object> resultMap = new HashMap<>();
//        JSONObject resData = getResData(result);
//
//        String resultCd = StringUtil.lpad((String) resData.get("RSLT_CD"), 4, "0");
//        String resultMsg = (String) resData.get("RSLT_MSG");
//        JSONArray jsonArray = (JSONArray) resData.get("RESP_DATA");
//
//        if (jsonArray == null) {
//
//            resultMap.put("RESULT_CODE", resultCd);
//            resultMap.put("RESULT_MESSAGE", resultMsg);
//            resultMap.put("RESULT_DATA", resData);
//
//            return resultMap;
//        } else {
//
//            JSONObject jsonResData = (JSONObject) jsonArray.get(0);
//
//            if (!isRealName(jsonResData, (String) params.get("MEMB_NM")))
//                throw new RuntimeException("실명계좌가 아닙니다.");
//
//            resultMap.put("ACCT_NM", params.get("MEMB_NM"));
//            resultMap.put("RESULT_CODE", resultCd);
//            resultMap.put("RESULT_MESSAGE", resultMsg);
//            resultMap.put("RESULT_DATA", resData);
//
//            params.put("ESCR_M_KEY", params.get("ESCR_M_KEY"));
//            params.put("escr_m_key", params.get("ESCR_M_KEY"));
//
////            int dbCnt = accountMapper .updateEscrPmntAcctInfo(params);
////                dbCnt = ifTgInfoMapper.insertBackupEscrM(params);
//        }
//
//        tgLogMaster.setSndRcvRslt(resultCd);
//        tgLogMaster.setSndRcvRsltMsg(resultMsg);
//
//        return resultMap;
//    }
//
//    /**
//     * 거래일련번호 조회
//     *
//     * @return
//     */
//    private String getTrscSeqNo() {
//        String trscSeqNo = "7";
//        // TODO:: 임시주석처리
////        String seqNo = String.valueOf(accountMapper.selectTrscSeqNo());
//        String seqNo = String.valueOf(null);
//        return trscSeqNo + StringUtil.lpad(seqNo, 6, "0");
//    }
//
//    /**
//     * 본인인증 실명과 계좌번호의 실명을 비교
//     *
//     * @param jsonResData
//     * @param membNm
//     * @return
//     */
//    private boolean isRealName(JSONObject jsonResData, String membNm) {
//
//        env = System.getProperty("spring.profiles.active");
//        if (!"prod".equals(env)) {
//            env = "dev";
//        }
//
//        String acctNm = ((String) jsonResData.get("ACCT_NM")).trim();
//
//        if (StringUtil.equalsAny(env, "local", "dev")) {
//            log.debug("=====>> 로컬/개발 테스트시 실명이 이상한 값 [" + acctNm + "] 이 넘어와 테스트를 위해 동일 값으로 처리");
//            acctNm = membNm;
//        }
//
//        log.debug("=====>> 실명 비교 acct_nm [" + acctNm + "]   menbNm [" + membNm + "]");
//
//        return membNm.equals(acctNm);
//    }
//}
