package com.bankle.common.asis.component.directSend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectSendCmnService {

    public HttpURLConnection getConnection(String url) throws Exception{
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        conn.setRequestProperty("Accept", "application/json");

        System.setProperty("jsse.enableSNIExtension", "false");
        conn.setDoOutput(true);

        return conn;
    }

    public int send(HttpURLConnection conn, String urlParameters) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter (conn.getOutputStream());
        wr.write(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        log.info(String.valueOf(responseCode));

        if(responseCode != 200)
            throw new RuntimeException("directsend 관리자에게 문의해주시기 바랍니다.");

        /*
         * responseCode 가 200 이 아니면 내부에서 문제가 발생한 케이스입니다.
         * directsend 관리자에게 문의해주시기 바랍니다.
         */

        return responseCode;
    }

    public String getResponse(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        log.info(response.toString());
        return response.toString();
    }
}
