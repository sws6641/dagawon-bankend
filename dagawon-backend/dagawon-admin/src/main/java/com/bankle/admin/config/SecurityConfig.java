package com.bankle.admin.config;

import com.bankle.common.config.security.CustomAccessDeniedHandler;
import com.bankle.common.config.security.JwtExceptionFilter;
import com.bankle.common.config.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.application.name}")
    private String appName;

    private final JwtFilter jwtFilter;

    private final AuthenticationEntryPoint authEntryPoint;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final String[] SWAGGER = {
            "/api-docs"                    , "/swagger-ui/**", "/api/api/api-docs" ,   "/api/api/api-docs/swagger-config",
            "/api-docs/swagger-config"     , "/api-docs.yaml", "/api/api-docs" ,
            "/swagger-resources/**"        , "/configuration/security",
            "/webjars/**"                  , "/swagger-ui.html"};
    private final String[] UI = {
            "/login/**"                    , "/auth/**",
            "/css/**"                      , "/img/**",
            "/static/**"                   , "/resources/**",
            "/comm/code/**"                , "/asis/**",
            "/agree/**"                    , "/comm/**",
            "/memb/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("#########    ----------- Auth SecurityFilterChain --------------  ######");
        log.debug("#########    ----------- Application Name : "+ appName + " --------------  ######");
        http
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of(
                            "https://localhost:3000"
                            , "http://localhost:3000"
                            , "https://anbuapp.com/"
                            , "https://admin.anbuapp.com/"
                            , "https://batch.anbuapp.com/"
//                            , "https://appdev.anbu.com/"
//                    , "https://admindev.anbu.com/"
//                    , "https://batchdev.anbu.com/"
                    ));
                    cors.setAllowedMethods(List.of("GET","POST"));
                    cors.setAllowedHeaders(List.of("*"));
                    return cors;
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers(UI).permitAll()
                .requestMatchers(SWAGGER).permitAll()
                .requestMatchers("/**").authenticated()

                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")

                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(authEntryPoint);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter(), JwtFilter.class);

        return http.build();
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter();
    }

}
