package com.bankle.common.asis.component.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sms")
@Getter @Setter
public class SmsProperties {
    private String secretKey;
    private boolean isTest;
    private String testCorpNum;
    private String testUserId;
}
