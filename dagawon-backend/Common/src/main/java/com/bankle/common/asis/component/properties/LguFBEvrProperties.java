package com.bankle.common.asis.component.properties;

import com.bankle.common.asis.component.properties.LguFBDBProperties;
import com.bankle.common.asis.component.properties.LguFBEscrProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Validated
@RequiredArgsConstructor
@Slf4j
public class LguFBEvrProperties {
    
    private final LguFBDBProperties lguFBDBProp;
    private final LguFBEscrProperties lguFBEscrProp;
    
    private String SND_ID;
    private String RCV_ID;
    private String CUST_ID;
    private String SRVC_DSC;
    private String SRV_IP;
    private int    SRV_PORT;
    private String ACCT_NO;
    private String ACCT_PW;
    private String ANBU_ACCT_NAME = "(주)코코스원";
    
    public String getAnbuAcctName() {
        return ANBU_ACCT_NAME;
    }
    
    public String getSndId(String svrGbn) {
        
        if      ("DW".equals(svrGbn)) { SND_ID   = lguFBDBProp  .getSndId (); } 
        else if ("DV".equals(svrGbn)) { SND_ID   = lguFBDBProp  .getSndVid(); }
        else if ("EW".equals(svrGbn)) { SND_ID   = lguFBEscrProp.getSndId (); } 
        else if ("EV".equals(svrGbn)) { SND_ID   = lguFBEscrProp.getSndVid(); }
        
        return SND_ID;
    }
    
    
    public String getRcvId(String svrGbn) {
        
        if      ("DW".equals(svrGbn)) { RCV_ID   = lguFBDBProp  .getRcvId (); } 
        else if ("DV".equals(svrGbn)) { RCV_ID   = lguFBDBProp  .getRcvVid(); }
        else if ("EW".equals(svrGbn)) { RCV_ID   = lguFBEscrProp.getRcvId (); } 
        else if ("EV".equals(svrGbn)) { RCV_ID   = lguFBEscrProp.getRcvVid(); }
        
        return RCV_ID;
    }    

    public String getCustId(String svrGbn) {
        
        if      ("DW".equals(svrGbn)) { CUST_ID   = lguFBDBProp  .getWonCustId();} 
        else if ("DV".equals(svrGbn)) { CUST_ID   = lguFBDBProp  .getVrCustId ();}
        
        else if ("EW".equals(svrGbn)) { CUST_ID   = lguFBEscrProp.getWonCustId();}
        else if ("EV".equals(svrGbn)) { CUST_ID   = lguFBEscrProp.getVrCustId ();}
        
        // @ConfigurationProperties 사용시 숫자로 인식후 String 변환 하는 오류 때문에 .. 
        // 임의로 "ID" 문자를 붙여서 application.yml 에 등록함.
        CUST_ID = CUST_ID.replace("ID", ""); 
        log.debug("====>>> LguFBEvrProperties.getCustId CUST_ID [" + CUST_ID + "]");
        
        return CUST_ID;
    }    
    
    public String getSrvcDsc(String svrGbn) {
        
        if      ("DW".equals(svrGbn)) { SRVC_DSC   = "DY2"; } 
        else if ("DV".equals(svrGbn)) { SRVC_DSC   = "VAS"; }
        else if ("EW".equals(svrGbn)) { SRVC_DSC   = "DY2"; } 
        else if ("EV".equals(svrGbn)) { SRVC_DSC   = "VAS"; }
        
        return SRVC_DSC;
    }    

    public String getSrvIp(String svrGbn) {
        
        if      ("DW".equals(svrGbn)) { SRV_IP   = lguFBDBProp  .getSvrIp(); } 
        else if ("DV".equals(svrGbn)) { SRV_IP   = lguFBDBProp  .getSvrIp(); }
        else if ("EW".equals(svrGbn)) { SRV_IP   = lguFBEscrProp.getSvrIp(); } 
        else if ("EV".equals(svrGbn)) { SRV_IP   = lguFBEscrProp.getSvrIp(); }
        
        return SRV_IP;
    }
    
    public int getSrvPort(String svrGbn) {
        
        if      ("DW".equals(svrGbn)) { SRV_PORT   = Integer.parseInt(lguFBDBProp  .getSvrWonPort()); } 
        else if ("DV".equals(svrGbn)) { SRV_PORT   = Integer.parseInt(lguFBDBProp  .getSvrVrPort ()); }
        else if ("EW".equals(svrGbn)) { SRV_PORT   = Integer.parseInt(lguFBEscrProp.getSvrWonPort()); } 
        else if ("EV".equals(svrGbn)) { SRV_PORT   = Integer.parseInt(lguFBEscrProp.getSvrVrPort ()); }
        
        return SRV_PORT;
    }    
    
    public String getAcctNo(String svrGbn) {
        
        if      ("DW".equals(svrGbn)) { ACCT_NO   = lguFBDBProp  .getAcctNo(); } 
        else if ("DV".equals(svrGbn)) { ACCT_NO   = lguFBDBProp  .getAcctNo(); }
        else if ("EW".equals(svrGbn)) { ACCT_NO   = lguFBEscrProp.getAcctNo(); } 
        else if ("EV".equals(svrGbn)) { ACCT_NO   = lguFBEscrProp.getAcctNo(); }
        
        return ACCT_NO;        
    }
    
    public String getAcctPW(String svrGbn) {
        
        if      ("DW".equals(svrGbn)) { ACCT_PW   = lguFBDBProp  .getAcctPw(); } 
        else if ("DV".equals(svrGbn)) { ACCT_PW   = lguFBDBProp  .getAcctPw(); }
        else if ("EW".equals(svrGbn)) { ACCT_PW   = lguFBEscrProp.getAcctPw(); } 
        else if ("EV".equals(svrGbn)) { ACCT_PW   = lguFBEscrProp.getAcctPw(); }

        // @ConfigurationProperties 사용시 숫자로 인식후 String 변환 하는 오류 때문에 .. 
        // 임의로 "ID" 문자를 붙여서 application.yml 에 등록함.
        ACCT_PW = ACCT_PW.replace("PW", "");  
        log.debug("====>>> LguFBEvrProperties.getAcctPW ACCT_PW [" + ACCT_PW + "]");        
        
        return ACCT_PW;        
    }    
}
