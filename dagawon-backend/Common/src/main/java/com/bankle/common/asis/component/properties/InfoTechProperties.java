package com.bankle.common.asis.component.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("infotech")
@Validated
@Getter @Setter
public class InfoTechProperties {
    private String apiKey;
    private String url;
    private String userId1;
    private String userId2;
    private String userId3;
    private String userId4;
    private String userId5;
    private String userPw1;
    private String userPw2;
    private String userPw3;
    private String userPw4;
    private String userPw5;
}
