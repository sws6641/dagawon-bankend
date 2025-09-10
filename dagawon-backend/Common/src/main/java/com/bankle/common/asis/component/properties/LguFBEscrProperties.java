package com.bankle.common.asis.component.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
//@Service
@ConfigurationProperties("lgufbescr")
@Validated
@Getter @Setter
public class LguFBEscrProperties {
  
//    @Value("${lgufbescr.sndVid}")
//    private String sndVid;
//    @Value("${lgufbescr.rcvVid}")
//    private String rcvVid;
//    @Value("${lgufbescr.sndId}")
//    private String sndId;
//    @Value("${lgufbescr.rcvId}")
//    private String rcvId;
//    @Value("${lgufbescr.acctNo}")
//    private String acctNo;
//    @Value("${lgufbescr.acctPw}")
//    private String acctPw;
//    @Value("${lgufbescr.svrIp}")
//    private String svrIp;
//    @Value("${lgufbescr.svrWonPort}")
//    private String svrWonPort;   
//    @Value("${lgufbescr.svrVrPort}")
//    private String svrVrPort;  
//    @Value("${lgufbescr.wonCustId}")
//    private String wonCustId;
//    @Value("${lgufbescr.vrCustId}")
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