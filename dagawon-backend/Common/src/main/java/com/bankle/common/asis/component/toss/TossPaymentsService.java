package com.bankle.common.asis.component.toss;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.service.ContractEscrowService;
import com.bankle.common.asis.domain.service.FeeRomDetailService;
import com.bankle.common.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class TossPaymentsService {

//    @Value("${toss.secretKey}")
    private String secretKey;

//    @Value("${toss.customerKey}")
    private String customerKey;

    private final FeeRomDetailService feeRomDetailService;
    private final ContractEscrowService contractEscrowService;

    /**
     * 결제요청
     * @param params
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Map<String,String> reqApproval(String escrMKey,
                                          HashMap<String, String> params) throws IOException, InterruptedException{

        String encodedSecretKey = Base64.getEncoder().encodeToString((secretKey +":").getBytes());
        log.info("encodedSecretKey ======"+encodedSecretKey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/"+params.get("paymentKey")))
                .header("Authorization", "Basic "+encodedSecretKey)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "{\"amount\":"+params.get("amount")+"" +
                        ",\"orderId\":\""+params.get("orderId")+"\"}"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        log.info(response.body());

        Map<String,String> fromJson = new Gson().fromJson(
                response.body(), new TypeToken<HashMap<String, String>>() {
                }.getType());

        Map<String,String> fromData = new Gson().fromJson(
                fromJson.get("data"), new TypeToken<HashMap<String, String>>() {
                }.getType());

        if(StringUtil.equals(fromData.get("status"),"DONE")){

            HashMap feeParams = new HashMap<String,String>();
            feeParams.put("escrMKey", escrMKey);
            feeParams.put("feeStmtDsc", "6");
            feeParams.put("stmtAmt", params.get("amount"));
            saveResult(feeParams);
        }

        return fromData;
    }

    /**
     * 결제내역 및 메인 테이블 업데이트
     * @param params
     */
    @Transactional
    public void saveResult(HashMap<String,String> params){
        //수수료내역 저장
        feeRomDetailService.paid(params);

        //거래 에스크로 기본 업데이트
        ContractEscrow contract = contractEscrowService.getContractEscrow(params.get("orderId"));
        contract.setFeePayYn("Y");
        contract.setFeeAmt(Long.valueOf(params.get("amount")));
        contract.setEscrDtlPgc("03");       //수수료납입완료
        contractEscrowService.save(contract);
    }
}
