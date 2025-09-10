package com.bankle.common.asis.component.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("sysmnginfo")
@Validated
@Getter @Setter
public class SysMngProperties {
    private String mngName;
    private String mngHpNo;
    
    
    public String[] getListMngName() {
        return  getMngName().split("\\^");        
    }
    
    public String[] getListMngHpNo() {
        return  getMngHpNo().split("\\^");        
    }    
}
