package com.bankle.common.config.security;

import com.bankle.common.commvo.ResData;
import com.bankle.common.util.ErrorSaveUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 😀미인증 허가 주소 들어왔을경우
 * @Package     : com.withuslaw.common.config.security
 * @name        : CustomAuthenticationEntryPoint.java
 * @date        : 2023/09/14 9:02 PM
 * @author      : tigerBK
 * @version     : 1.0.0
**/
@Slf4j
@RequiredArgsConstructor
@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {


    private final ErrorSaveUtil errorSaveUtil;


    final String resultMsg = "미인증 서비스 입니다!!";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException  {
        log.debug("CustomAuthenticationEntryPoint() > commence !");
        response.flushBuffer();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
      //  errorSaveUtil.errorSave( request,  authException);
        mapper.writeValue(responseStream, ResData.builder().code(String.valueOf(HttpStatus.UNAUTHORIZED.value())).msg(resultMsg).build());
        responseStream.flush();
    }
}
