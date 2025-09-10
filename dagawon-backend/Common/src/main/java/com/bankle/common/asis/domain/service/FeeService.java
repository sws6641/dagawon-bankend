package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.component.properties.TossKeyProperties;
import com.bankle.common.asis.domain.mapper.FeeMapper;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.asis.utils.EscrSendMsgUtils;
import com.bankle.common.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;

/**
 * @author 김배성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FeeService {

//    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final TossKeyProperties tossProp;

    private final FeeMapper feeMapper;
    private final IfTgInfoMapper ifTgMapper;
    private final EscrSendMsgUtils escrSendMsg;

    public HashMap<String, Object> cancelCard(HashMap<String, Object> paramMap) {

        String escr_m_key = StringUtil.mapToString(paramMap, "ESCR_M_KEY");
        String paymentKey = StringUtil.mapToString(paramMap, "STMT_KEY");
        long rfnd_amt = StringUtil.mapToStringL(paramMap, "RFND_AMT");

        HashMap<String, Object> reqMap = new HashMap<>();
        reqMap.put("cancelReason", "고객변심");
        reqMap.put("cancelAmount", rfnd_amt);
        reqMap.put("escr_m_key", escr_m_key);

        log.debug("===================================================================================");
        log.debug("카드취소");
        log.debug("===================================================================================");
        log.debug("escr_m_key  [" + escr_m_key + "]  rfnd_amt [" + rfnd_amt + "]");
        log.debug("===================================================================================");

        return callTossPayments("https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel", "2", reqMap);
    }

    public HashMap<String, Object> callTossPayments(String tossUrl, String svcGbn, HashMap<String, Object> paramMap) {

        ResponseEntity<JsonNode> responseEntity;
        JsonNode jsonNode;

        String rslt_proc_yn = "N";
        String escr_m_key = paramMap.get("escr_m_key").toString();
        log.debug("=====>> callTossPayments escr_m_key [" + escr_m_key + "]");

        try {

            log.debug("=====>> callTossPayments Debug 1");
            HttpHeaders headers = new HttpHeaders();
            //headers.setBasicAuth(SECRET_KEY, "")
            headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((tossProp.getSecretKey() + ":").getBytes()));
            headers.setContentType(MediaType.APPLICATION_JSON);

            log.debug("=====>> callTossPayments Debug 2");

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(paramMap), headers);

            log.debug("callTossPayments Response Body\n[" + request.getBody() + "]");


            responseEntity = null;
//            responseEntity = restTemplate.postForEntity(tossUrl, request, JsonNode.class);

            log.debug("=====>> callTossPayments Debug 3");

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                log.debug("callTossPayments respnse OK Start !!!");

                jsonNode = responseEntity.getBody();
                String resBody = jsonNode.toString();

                log.debug("OK jsonNode [" + resBody + "]");
                //getStmtInfo(paramMap);

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(resBody);
                JSONObject jsonObj = (JSONObject) obj;
                JSONObject jsonObjDtl = (JSONObject) jsonObj.get("card");


                paramMap.put("crd_nm", jsonObjDtl.get("company").toString());
                paramMap.put("crd_no", jsonObjDtl.get("number").toString());
                paramMap.put("totalAmount", jsonObj.get("totalAmount").toString());
                paramMap.put("lastTransactionKey", jsonObj.get("lastTransactionKey").toString());
                paramMap.put("paymentKey", jsonObj.get("paymentKey").toString());
                paramMap.put("requestedAt", jsonObj.get("requestedAt").toString());
                paramMap.put("approvedAt", jsonObj.get("approvedAt").toString());
                paramMap.put("receiptUrl", jsonObjDtl.get("receiptUrl").toString());

                paramMap.put("fee_stmt_dsc", "2");   // 카드결제
                paramMap.put("rslt_cd", "0000");
                paramMap.put("rslt_msg", "정상");

                if ("1".equals(svcGbn)) {
                    paramMap.put("stmt_cncl_dsc", "1");  // 카드결제

                    JSONObject jsonObjRcp = (JSONObject) jsonObj.get("receipt");
                    paramMap.put("url", jsonObjRcp.get("url").toString());
                } else {
                    paramMap.put("stmt_cncl_dsc", "2");  // 카드결제취소

                    JSONArray jsonArrayCncl = (JSONArray) jsonObj.get("cancels");
                    JSONObject jsonObjCncl = (JSONObject) jsonArrayCncl.get(0);

                    paramMap.put("cancelReason", jsonObjCncl.get("cancelReason").toString());
                    paramMap.put("cancelAmount", jsonObjCncl.get("cancelAmount").toString());
                    paramMap.put("transactionKey", jsonObjCncl.get("transactionKey").toString());
                }

                rslt_proc_yn = "Y";
            } else {

                log.debug("callTossPayments respnse Error Start !!!");

                jsonNode = responseEntity.getBody();

                log.debug("failNode message [" + jsonNode.get("message").asText() + "]");
                log.debug("failNode code    [" + jsonNode.get("code").asText() + "]");

                paramMap.put("rslt_cd", "9901");
                paramMap.put("rslt_msg", "[" + jsonNode.get("code").asText() + "]\n" + jsonNode.get("message").asText());
            }
        } catch (HttpClientErrorException HEx) {

            log.debug("callTossPayments respnse Exception Start !!!");

            try {

                log.error("=====>>> HttpClientError [" + HEx.getResponseBodyAsString() + "]");

                jsonNode = objectMapper.readTree(HEx.getResponseBodyAsString());

                paramMap.put("rslt_cd", "9902");
                paramMap.put("rslt_msg", "[" + jsonNode.get("code").asText() + "]\n" + jsonNode.get("message").asText());

            } catch (Exception Ex) {
                log.error("====>>> HttpClientErrorException 내부");
                Ex.printStackTrace();
                paramMap.put("rslt_cd", "9990");
                paramMap.put("rslt_msg", Ex.getMessage());
            }
        } catch (Exception Ex) {
            log.error("====>>> TossPayments 통신 내부");
            Ex.printStackTrace();
            paramMap.put("rslt_cd", "9991");
            paramMap.put("rslt_msg", Ex.getMessage());
        }

        paramMap.put("rslt_proc_yn", rslt_proc_yn); // 처리 성공/실패

        int dbCnt = feeMapper.insertFeeStmtH(paramMap);

        // 결제 성공시 수수료결제입금상세(TET_FEE_ROM_D) 등록
        if ("Y".equals(rslt_proc_yn)) {

            if ("1".equals(svcGbn)) {
                paramMap.put("stmt_cncl_dsc", "1");  // 수수료 결제
            } else {
                paramMap.put("stmt_cncl_dsc", "2");  // 수수료 취소 (반환)
            }

            dbCnt = feeMapper.insertFeeRomTossD(paramMap);

            if ("1".equals(svcGbn)) {
                dbCnt = feeMapper.updateEscrFeeRom(paramMap);
                dbCnt = ifTgMapper.insertBackupEscrM(paramMap);

                escrSendMsg.sendMsg(escr_m_key, 32001, null, null);
            }
        }

        return paramMap;
    }
}
