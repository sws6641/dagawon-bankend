package com.bankle.common.asis.component.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("toss")
@Validated
@Getter @Setter
public class TossKeyProperties {
    
    private String clientKey;
    private String secretKey;
    private String customerKey;
}
