package com.bankle.common.asis.component.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
//@Service
@ConfigurationProperties("lgufbdb")
@Validated
@Getter @Setter
public class LguFBDBProperties {
    
//    @Value("${lgufbdb.sndVid}")
//    private String sndVid;
//    @Value("${lgufbdb.rcvVid}")
//    private String rcvVid;
//    @Value("${lgufbdb.sndId}")
//    private String sndId;
//    @Value("${lgufbdb.rcvId}")
//    private String rcvId;
//    @Value("${lgufbdb.acctNo}")
//    private String acctNo;
//    @Value("${lgufbdb.acctPw}")
//    private String acctPw;
//    @Value("${lgufbdb.svrIp}")
//    private String svrIp;
//    @Value("${lgufbdb.svrWonPort}")
//    private String svrWonPort;   
//    @Value("${lgufbdb.svrVrPort}")
//    private String svrVrPort;  
//    @Value("${lgufbdb.wonCustId}")
//    private String wonCustId;
//    @Value("${lgufbdb.vrCustId}")
//    private String vrCustId;
    
    private String sndVid;
    private String rcvVid;
    private String sndId;
    private String rcvId;
    private String acctNo;
    private String acctPw;
    private String svrIp;
    private String svrWonPort;   
    private String svrVrPort;  
    private String wonCustId;
    private String vrCustId;    
}
