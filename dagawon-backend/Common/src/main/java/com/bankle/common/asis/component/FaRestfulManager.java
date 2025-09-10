package com.bankle.common.asis.component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.bankle.common.asis.component.properties.FaProperties;
import com.bankle.common.util.StringUtil;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RequiredArgsConstructor
public class FaRestfulManager {
    
    private FaProperties faProp;

    public FaRestfulManager(FaProperties prop) {
        this.faProp = prop;
    }
    
    public HttpHeaders makeHeader() {
        
        FaEncryptDecrypt faEncryptDecrypt = new FaEncryptDecrypt();

        //헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        //서버 IP 추가
        headers.add("IP", faProp.getAnbuIp());
        log.debug("=====>> IP [" + faProp.getAnbuIp() + "]");
        
        //HMAC 서명
        String timeSpan = Long.toString(System.currentTimeMillis());
        
        String planText = faProp.getPubKey() + timeSpan + faProp.getAnbuIp();
        
        String hash     = faEncryptDecrypt.makeHMAC(faProp.getPubKey(), planText);
        
        if(!hash.equals("Failed")) {
            headers.add("HMAC", hash);
            log.debug("=====>> HMAC [" + hash + "]");
        }

        //TimeStamp 현재 시간
        headers.add("X-FA-Timestamp", timeSpan);
        log.debug("=====>> X-FA-Timestamp [" + timeSpan + "]" );
        
        //Public key 추가
        headers.add("X-FA-VISA-ID", faProp.getPubKey());
        log.debug("=====>> X-FA-VISA-ID [ " + faProp.getPubKey() + "]");
        
        //API Key 생성        
        String enCryptedApiKey = faEncryptDecrypt.encrypt(faProp.getPubKey(), faProp.getApiKey());
        //log.debug("==FaRestfulManager==enCryptedApiKey  : " + enCryptedApiKey);
        
        if(!enCryptedApiKey.equals("Failed")) {
            headers.add("X-FA-VISA", enCryptedApiKey);
            log.debug("=====>> X-FA-VISA [" + enCryptedApiKey + "]");
            
        }

        return headers;
    }

    public String makeBody(String jsonData) {
        
        FaEncryptDecrypt faEncryptDecrypt  = new FaEncryptDecrypt();
        String           enCryptedJsonData = faEncryptDecrypt.encrypt(faProp.getApiKey(), jsonData);
        //log.debug("==FaRestfulManager==enCryptedJsonData  : " + enCryptedJsonData);

        return enCryptedJsonData;
    }

    public JSONObject checkValidation(String Ip, String hmac, String timeStamp, String pubKey, String decodedApiKey, String encodedJsonData) {

        //log.debug("==FaRestfulManager==Ip : " + Ip);
        
        JSONObject jsonObj = new JSONObject();
                  
        if (Ip == null || !Ip.equals(faProp.getIp())) { //IP 변수로 지정하자
             jsonObj.put("result"   , "error");
             jsonObj.put("errordesc", "허가되지 않은 IP입니다.");
             
             log.debug("==FaRestfulManager==toString : " + jsonObj.toJSONString());
             return jsonObj;
        }

        if (System.currentTimeMillis() - Long.parseLong(timeStamp) >= 600000) {
//           log.debug("==System.currentTimeMillis==시간은 : " + System.currentTimeMillis() );
//           log.debug("==Long.parseLong==시간은 : " + Long.parseLong(timeStamp) );
//           log.debug("==FaRestfulManager==시간은 : " + (System.currentTimeMillis() - Long.parseLong(timeStamp)));
           jsonObj.put("result","error");
           jsonObj.put("errordesc","요청한 시간이 10분 초과 하였습니다.");
           log.debug("==FaRestfulManager==toString : " + jsonObj.toJSONString());
           return jsonObj;
        }

        FaCompHmac faCompHmac = FaCompHmac.getInstance();

        if(faCompHmac.getHmac() != null && faCompHmac.getHmac().equals(hmac)) {
               jsonObj.put("result","error");
               jsonObj.put("errordesc","동일한 HMAC가 요청되었습니다.");
               log.debug("==FaRestfulManager==toString : " + jsonObj.toJSONString());
               faCompHmac.setHmac(hmac);
               return jsonObj;
        }

        faCompHmac.setHmac(hmac);

        if(!decodedApiKey.equals(faProp.getApiKey())) {
             jsonObj.put("result","error");
             jsonObj.put("errordesc","허가되지 않은 API KEY입니다.");
             log.debug("==FaRestfulManager==toString : " + jsonObj.toJSONString());
             return jsonObj;
        }

        if (encodedJsonData == null) {
            jsonObj.put("result","error");
            jsonObj.put("errordesc","요청 데이터가 NUll입니다.");
            return jsonObj;
        }


        jsonObj.put("result","success");
        jsonObj.put("errordesc", "");
        return jsonObj;
    }

    /*
     * Desc : HTTPS를 검증하지 않기 위한 메소드
     */
    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        /*
         * Ignore untrusted certificates
         */
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    @Override
                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                    @Override
                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sslContext = SSLContext.getInstance("SSL");

        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                        new HttpComponentsClientHttpRequestFactory();

//        requestFactory.setHttpClient(httpClient);
        //requestFactory.setConnectTimeout(7000); // set short connect timeout
        //requestFactory.setReadTimeout(7000); // set slightly longer read timeout

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
     }


    /*
     * Desc : HTTPS를 검증하지 않기 위한 메소드
     */
    @Bean
    public CloseableHttpClient closeableHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        /*
         * Ignore untrusted certificates
         */
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    @Override
                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                    @Override
                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sslContext = SSLContext.getInstance("SSL");

        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        return closeableHttpClient;
     }
    
    /**
    * 계약상세내용 - 계약내용요약*
    * @param contno
    * @return
    * @throws Exception
    */
   public String getContractDetail(String strContDtl) throws Exception{

      Map<String, Object> map = new HashMap<String, Object>();
      String strTotRemark = "";
      String strReMark1 = null;
      String strReMark2 = null;
      String strReMark3 = null;
      String dvsnCd = null;

      try {
         String[] splitRemark = strContDtl.split(",");
         String[] splitRemark1 = splitRemark[0].split(":");
         String[] splitRemark2 = splitRemark[1].split(":");
         String[] splitRemark3 = splitRemark[2].split(":");
         if (splitRemark1 != null && splitRemark1.length != 0) {
            map.put("remark1_1", splitRemark1[0]);
            map.put("remark1_2", StringUtil.getAmountComma(splitRemark1[1]));
            map.put("remark1_3", splitRemark1[2]);
            if(map.get("remark1_3").equals("1")) {
                dvsnCd = "승계, ";
            } else {
                dvsnCd = "말소, ";
            }

            strTotRemark = "(근)저당권 " + map.get("remark1_1").toString() + "건 " + "채권최고액 합계 " + map.get("remark1_2").toString() + "원  " + dvsnCd;
         }
         if (splitRemark2 != null && splitRemark2.length != 0) {
            map.put("remark2_1", splitRemark2[0]);
            map.put("remark2_2", StringUtil.getAmountComma(splitRemark2[1]));
            map.put("remark2_3", splitRemark2[2]);

            if(map.get("remark2_3").equals("1")) {
                dvsnCd = "승계, ";
            } else {
                dvsnCd = "말소, ";
            }

            strTotRemark = strTotRemark + "전세권(임차권) " + map.get("remark2_1").toString() + "건 " + "전세권(보증금) 합계 " + map.get("remark2_2").toString() + "원  " + dvsnCd;
         }
         if (splitRemark3 != null && splitRemark3.length != 0) {
            map.put("remark3_1", splitRemark3[0]);
            map.put("remark3_2", StringUtil.getAmountComma(splitRemark3[1]));
            map.put("remark3_3", splitRemark3[2]);

            if(map.get("remark3_3").equals("1")) {
                dvsnCd = "승계 ";
            } else {
                dvsnCd = "말소 ";
            }

            strTotRemark = strTotRemark + "가압류(경매,압류) " + map.get("remark3_1").toString() + "건 " + "청구금액 합계 " + map.get("remark3_2").toString() + "원  " + dvsnCd;
         }
      } catch(Exception e) {
         e.printStackTrace();
      }

      return strTotRemark;
    }
}
