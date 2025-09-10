package com.bankle.app.config;


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

        final String securitySchemeName = "bearer DaGaWonApp";
        Info info = new Info()
                .version("v1.0.0")
                .title("DaGaWon-App-Server Project")
                .description("이름: 홍길동 , 회원번호 : 202403170004  , PIN번호 : 123456 , \n 토큰 : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkdmNlVW5xTnVtIjoiU3lzdGVtIiwiaXNBZG1pbiI6Ik4iLCJtZW1iTm8iOiIyMDI0MDMxNzAwMDQiLCJhdXRoTWV0aG9kIjoiUElOIiwiZXhwIjoxNzMwNzg1NDIwfQ.RQzLy88q3RTKlllTTJRnDE1nZZm7fxyb7l_gWXl9LLZVH7WmoLSxYyX8PjDyMiYH3lNdKlbQQGv4DxkoF2jOew");

        Server server = new Server().url(servletContext.getContextPath());

        log.debug("########################################################################");
        log.debug("### Anbu-App-Server OpenAPI30Configuration Configuration");
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
