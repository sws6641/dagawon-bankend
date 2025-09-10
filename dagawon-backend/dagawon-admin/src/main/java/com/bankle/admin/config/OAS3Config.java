package com.bankle.admin.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class OAS3Config {
    @Bean
    public OpenAPI customizeOpenAPI(ServletContext servletContext) {

        final String securitySchemeName = "bearer DaGaWonAdmin";
        Info info = new Info()
                .version("v1.0.0")
                .title("DaGaWon-Admin-Server Project")
                .description("이름: 홍길동 , ID : abc123  , 비밀번호 : 123456 , 권한 : 일반관리자(A) \n 토큰 : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc0FkbWluIjoiWSIsImxvZ25JZCI6ImFiYzEyMyIsImV4cCI6MTg3NTI2NjYwNX0.XPiQPwEGwA0GISKKojBV7kZc-mKn5Q46EcSZiKNRMeE2IOjQRyfxj6Yg_dmA76a8qkBOmfWAS17mQ1O-GxG2iA");

        Server server = new Server().url(servletContext.getContextPath());

        log.debug("########################################################################");
        log.debug("### Anbu-Admin-Server OpenAPI30Configuration Configuration");
        log.debug("########################################################################");

        return new OpenAPI()
                .info(info)
                .servers(List.of(server))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                );
    }

}
