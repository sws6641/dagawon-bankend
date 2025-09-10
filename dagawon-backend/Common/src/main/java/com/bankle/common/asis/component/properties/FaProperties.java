package com.bankle.common.asis.component.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("fa")
@Validated
@Getter @Setter
public class FaProperties {
    private String anbuIp;
    private String url;
    private String ip;
    private String dpfUrl;
    private String vendorCode;
    private String accNo;
    private String bankCode;
    private String pubKey;
    private String apiKey;
}
