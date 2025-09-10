package com.bankle.common.asis.component.infoTech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.bankle.common.asis.component.properties.InfoTechProperties;
import com.bankle.common.asis.domain.mapper.RgstrMapper;
import com.bankle.common.asis.utils.EscrSendMsgUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InfoTechService {

    private final InfoTechProperties infoTech;
    private final RgstrMapper rgstrMapper;
    private final EscrSendMsgUtils escrSendMsg;
    
    /*=====================================================================================*/
    // srchInfoTech 등기사건조회 (부동산 고유번호)
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("ESCR_M_KEY"  , "566"           );   // 에스크로 키
    // map.put("RGSTR_UNQ_NO", "11612003004416");   // 등기(부동산) 고유번호
    // map.put("FEE_NM"      , "이지혜"        );   // 매도인명
    // map.put("FEE_PAY_DT"  , "20220801"      );   // 수수료 납부 일자     
    /*=====================================================================================*/
    public void srchInfoTech(HashMap<String, Object> paramMap) throws IOException {
        
        HttpsURLConnection httpsUrl          = null;
        BufferedReader     bufferedReader    = null;
        InputStream        inputStream       = null;
        InputStreamReader  inputStreamReader = null;
        String             reqBody           = "";
        try {
            ignoreSsl();
            
            httpsUrl = setConnection();
            reqBody  = setReqBody(paramMap);
            httpsUrl.getOutputStream().write(reqBody.getBytes("UTF-8"));
            
            int responseCode = httpsUrl.getResponseCode();
            
            if (responseCode != 200) {
                throw new RuntimeException("KOS 내 InfoTech 관리자에게 문의해주시기 바랍니다.");
            }
        
            inputStream       = httpsUrl.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader    = new BufferedReader(inputStreamReader);
            
            String resData = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((resData = bufferedReader.readLine()) != null) {
                    stringBuilder.append(resData);
            }
            
            resData = stringBuilder.toString();
            
            parseResData(paramMap, resData);
            
        } catch (Exception Ex) {            
            Ex.printStackTrace();
            
        } finally {
            if (bufferedReader    != null) { try { bufferedReader   .close(); } catch (IOException e) { e.printStackTrace(); } }        
            if (inputStreamReader != null) { try { inputStreamReader.close(); } catch (IOException e) { e.printStackTrace(); } }
            if (inputStream       != null) { try { inputStream      .close(); } catch (IOException e) { e.printStackTrace(); } }        
        }        
    }
    
    private HttpsURLConnection setConnection () throws Exception {
        
        HttpsURLConnection httpsUrl          = null;
        
        log.debug("=====>> url    [" + infoTech.getUrl() + "]");
        
        httpsUrl = (HttpsURLConnection) (new URL(infoTech.getUrl()).openConnection());
        httpsUrl.setRequestMethod("POST");
        httpsUrl.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
        httpsUrl.addRequestProperty("api-cloud-key", infoTech.getApiKey());  // 전달 받은 키 정보 설정
        
        httpsUrl.setConnectTimeout(3000);
        httpsUrl.setDoOutput(true);
        return httpsUrl;
    }
    
    private String setReqBody(HashMap<String, Object> paramMap) {
        
        HashMap<String, String> reqMap = new HashMap<String, String>();
        
        reqMap.put("orgCd"     , "iros"                              );  // 기관코드    
        reqMap.put("svcCd"     , "C0003"                             );  // 서비스코드 [ 고유번호로 등기신청사건 조회 ]
        reqMap.put("userId"    , infoTech.getUserId1()               );  // 로그인 아이디 
        reqMap.put("userPw"    , infoTech.getUserPw1()               );  // 로그인 비밀번호
        reqMap.put("rstNo"     , (String)paramMap.get("RGSTR_UNQ_NO"));  // 부동산고유번호 
        reqMap.put("ownerName" , (String)paramMap.get("PRTY_NM"     ));  // 등기소유자이름
        
        JSONObject jsonObj  = new  JSONObject(reqMap);    
        return jsonObj.toJSONString();
    }

    private void parseResData(HashMap<String, Object> paramMap, String resData) {
        
        log.debug("\n=====>> InfoTech Res Data [" + resData + "]");        
        Object     object         = null;
        JSONParser jsonParser     = new JSONParser();
        JSONObject resJson        = new JSONObject();
        JSONObject resJsonOut     = null;
        JSONObject resJsonOutC003 = null;
        JSONObject rowJson        = null;
        
        String escr_m_key = (String)paramMap.get("ESCR_M_KEY");
        long   fee_pay_dt = Long.parseLong((String)paramMap.get("FEE_PAY_DT"));
        long   acpt_dt    = 0;
        
        try {
            object         = jsonParser.parse(resData);
            resJson        = (JSONObject)object;
            resJsonOut     = (JSONObject)resJson.get("out");
            resJsonOutC003 = (JSONObject)resJsonOut.get("outC0003");
            
            String errYn  = (String)resJsonOutC003.get("errYn" );
            String errMsg = (String)resJsonOutC003.get("errMsg");
            
            if ("N".equals(errYn)) {
            
                //HashMap<String, Object> resMap = JsonCustUtils.toMap(resJson);
                
                JSONArray jsonArray = (JSONArray) resJsonOutC003.get("list");
    
                if (jsonArray != null) {
                    
                    int listCnt = jsonArray.size();
                    
                    ArrayList<HashMap<String, Object>> listSave = new ArrayList<HashMap<String, Object>>();
                              
                    for (int i=0; i<listCnt; i++) {
                        
                        rowJson = (JSONObject)jsonArray.get(i);
                        
                        HashMap<String, Object>  rowMap   = new HashMap<String, Object>();
                        
                        acpt_dt = Long.parseLong((String)rowJson.get("접수일자"   ));

                        // 수수료납입일자 이후에 발생한 사건내역만 저장한다.
                        log.debug("=====>> acpt_dt [" + acpt_dt + "]     fee_pay_dt [" + fee_pay_dt + "]");
                        
                        if (acpt_dt >= fee_pay_dt) {
                            rowMap.put("ACPT_DT"   , (String)rowJson.get("접수일자"   ));
                            rowMap.put("ACPT_NO"   , (String)rowJson.get("접수번호"   ));
                            rowMap.put("JURS_REGOF", (String)rowJson.get("관할등기소" ));
                            rowMap.put("REGOF_DPRT", (String)rowJson.get("계"         ));
                            rowMap.put("ALN_ADDR"  , (String)rowJson.get("소재지번"   ));
                            rowMap.put("RGSTR_OBJ" , (String)rowJson.get("등기목적"   ));
                            rowMap.put("PROC_STT"  , (String)rowJson.get("처리상태"   ));
                            rowMap.put("ESCR_M_KEY", escr_m_key                        );
                            
                            listSave.add(rowMap);
                        }
                    }
                    if (listSave.size() > 0) { saveRgstrIcdnt(escr_m_key, listSave); }
                }
            } else {
                log.error("=====>> 등기사건조회오류 escr_m_key [" + (String)paramMap.get("ESCR_M_KEY") + "]\nerrMsg : " + errMsg);
            }
        } catch(Exception Ex) {
           Ex.printStackTrace(); 
        }
    }

    private void saveRgstrIcdnt(String escr_m_key, ArrayList<HashMap<String, Object>> listSave) {
        
        // 내역 테이블 삭제
        // 내역 테이블 입력
        // 내역테이블과 이력(최종) 비교
        // 변경내역 발생시 내역테이블 update
        // 내역테이블 변경내역 테이블로 이관
        
        rgstrMapper.deleteRgstrIcdntI(escr_m_key);

        listSave.forEach(map->{
            rgstrMapper.insertRgstrIcdntI(map);
        });
        
        ArrayList<HashMap<String, Object>> list = rgstrMapper.selectRgstrIcdntChgYn(escr_m_key);
        
        if (list.size() > 0) {
            
            list.forEach(map -> {
                rgstrMapper.updateRgstrIcdnt(map);
            });
            
            /*==============================================================*/
            // 알림톡/PUSH 전송
            String   addr      = rgstrMapper.getAddress(escr_m_key);
            Object[] msgPatten = {addr};                
            escrSendMsg.sendMsg(escr_m_key, 38001, msgPatten, null);
            /*==============================================================*/            
        }
        
        rgstrMapper.insertRgstrIcdntH(escr_m_key);
    }
    
    private void ignoreSsl() throws Exception{
        HostnameVerifier hv = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                        return true;
                }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
    private void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
    static class miTM implements TrustManager,X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }
    
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }
    
        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    
        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }
}
