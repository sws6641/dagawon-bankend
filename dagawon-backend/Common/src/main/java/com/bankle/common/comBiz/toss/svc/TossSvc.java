package com.bankle.common.comBiz.toss.svc;

import com.bankle.common.comBiz.toss.vo.TossSvo;
import com.bankle.common.exception.DefaultException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class TossSvc {

    @Value("${toss.secretKey}")
    private String secretKey;

    @Transactional(rollbackFor = Exception.class)
    public TossSvo.TossTrOutSvo reqPayApproval(@Valid TossSvo.TossTrInSvo inSvo) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("orderId", inSvo.getOrderId());
        obj.put("amount", inSvo.getAmount());
        obj.put("paymentKey", inSvo.getPaymentKey());
        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용안함.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가.
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // POST request
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();
        if (jsonObject.get("paymentKey") == "DONE") {
            return TossSvo.TossTrOutSvo.builder()
                    .paymentKey((String)jsonObject.get("paymentKey"))
                    .method((String)jsonObject.get("method"))
                    .orderId((String)jsonObject.get("orderId"))
                    .amount((BigDecimal) jsonObject.get("amount"))
                    .status((String)jsonObject.get("status"))
                    .build();
        } else {
            String errorCode = (String)jsonObject.get("code");
            String errorMessage = (String)jsonObject.get("message");
            log.error("tosspayments errorCode: {}", errorCode);
            log.error("tosspayments error: {}", errorMessage);
            throw new DefaultException(errorMessage);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public TossSvo.TossTrOutSvo reqPayCancel(@Valid TossSvo.TossTrInSvo inSvo) throws Exception {

        JSONObject obj = new JSONObject();
        //필수값
        obj.put("paymentKey", inSvo.getPaymentKey());
        obj.put("cancelReason", inSvo.getCancelReason());
        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용안함.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가.
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제 취소
        URL url = new URL("https://api.tosspayments.com/v1/payments/" + inSvo.getPaymentKey() + "/cancel");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // POST request
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        if (jsonObject.get("paymentKey") == "DONE") {
            return TossSvo.TossTrOutSvo.builder()
                    .paymentKey((String)jsonObject.get("paymentKey"))
                    .method((String)jsonObject.get("method"))
                    .orderId((String)jsonObject.get("orderId"))
                    .amount((BigDecimal) jsonObject.get("amount"))
                    .status((String)jsonObject.get("status"))
                    .build();
        } else {
            String errorCode = (String)jsonObject.get("code");
            String errorMessage = (String)jsonObject.get("message");
            log.error("tosspayments errorCode: {}", errorCode);
            log.error("tosspayments error: {}", errorMessage);
            throw new DefaultException(errorMessage);
        }
    }
}


