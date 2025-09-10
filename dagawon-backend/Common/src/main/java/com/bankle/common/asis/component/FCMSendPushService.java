//package com.bankle.common.asis.component;
//
//import java.util.HashMap;
//
//import com.bankle.common.asis.domain.entity.ContractEscrowNoti;
//import com.bankle.common.asis.domain.repositories.ContractEscrowNotiRepository;
//import com.bankle.common.util.DateUtil;
//import com.bankle.common.util.StringUtil;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class FCMSendPushService {
//
//    @Value("${firebase.push.url}")
//    private String fcmUrl;
//
//    @Value("${firebase.push.apiKey}")
//    private String apiKey;
//
////    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    private final ContractEscrowNotiRepository contractEscrowNotiRepository;
//
//    public String sendPush(HashMap<String, String> map) throws JsonProcessingException {
//        HashMap<String, String> validMap = isValid(map);
//
//        String dvcKnd = validMap.get("dvcKnd");
//
//        JSONObject data = new JSONObject();
//        data.put("title", validMap.get("title"));
//        data.put("body", validMap.get("body"));
//
//        JSONObject json = new JSONObject();
//        json.put("to", validMap.get("fcmId"));
//
//        if("A".equals(dvcKnd ))
//            json.put("data", data);
//        else if("I".equals(dvcKnd))
//            json.put("notification", data);
//
//        //TET_ESCR_NOTI_D 저장
//        saveNotification(map);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(apiKey);
//
//        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
//
//        String postForObject = restTemplate.postForObject(fcmUrl, request, String.class);
//
//        JsonNode root = objectMapper.readTree(postForObject);
//
//        return root.toPrettyString();
//    }
//
//    private void saveNotification(HashMap<String,String> params){
//
//        HashMap<String, String> save    = new HashMap<>();
//        HashMap<String, Object> mapImsi = new HashMap<>();
//        mapImsi.putAll(params);
//
//        String escrMKey = StringUtil.mapToString(mapImsi, "escrMKey");
//
//        // escrMKey is not null 인 경우 만 에스크로 알림 저장
//        if (!"".equals(escrMKey)) {
//
//            save.put("escrMKey"   , params.get("escrMKey"   ));
//            save.put("msgPrttIKey", params.get("msgPrttIKey"));
//            save.put("notiTtl"    , params.get("notiTtl"    ));
//            save.put("notiCnts"   , params.get("notiCnts"   ));
//
//            ContractEscrowNoti build = ContractEscrowNoti.builder()
//                    .notiDtm    (DateUtil.getThisDate("yyyyMMddHHmmss"))
//                    .msgPrttIKey(Integer.parseInt(save.get("msgPrttIKey")))
//                    .notiTtl    (save.get("notiTtl"    ))
//                    .notiCnts   (save.get("notiCnts"   ))
//                    .build();
//
//            if(save.get("escrMKey") != null && StringUtils.hasText(save.get("escrMKey")))
//                build.setEscrMKey(Long.valueOf(save.get("escrMKey")));
//
//            contractEscrowNotiRepository.save(build);
//        }
//    }
//
//    private HashMap<String, String> isValid(HashMap<String, String> map){
//        String env = System.getProperty("spring.profiles.active");
//        log.info("env =========>"+env);
//
//        if (!StringUtils.hasText(map.get("fcmId")))
//            throw new RuntimeException("fcmId cannot be null");
//
//        if(!StringUtils.hasText(map.get("dvcKnd")))
//            throw new RuntimeException("device kind cannot be null");
//
//        return map;
//    }
//}