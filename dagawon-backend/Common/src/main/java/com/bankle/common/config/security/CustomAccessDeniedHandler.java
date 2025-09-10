package com.bankle.common.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 미인증 허가 주소 들어왔을경우
 *
 * @author : tigerBK
 * @version : 1.0.0
 * @Package : com.withuslaw.common.config.security
 * @name : CustomAccessDeniedHandler.java
 * @date : 2023/09/14 9:04 PM
 **/

@Slf4j
@RequiredArgsConstructor
@Component("CustomAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    private String errorPage;

//    private final ErrorSaveUtil errorSaveUtil;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        String deniedUrl = errorPage + "?exception=" + accessDeniedException.getMessage();
        //  errorSaveUtil.errorSave( request,  accessDeniedException);
        response.sendRedirect(deniedUrl);
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
