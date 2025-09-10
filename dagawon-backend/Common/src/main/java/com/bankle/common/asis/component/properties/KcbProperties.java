package com.bankle.common.asis.component.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("kcb")
@Component
@Getter @Setter
public class KcbProperties {
    
    private String kcb_cp_cd;
    private String kcb_site_name;
    private String kcb_site_url;
    private String kcb_target;
    private String kcb_license;
}
