package com.bankle.common.asis.component.frimBanking;

import java.io.OutputStream;
import java.net.Socket;

import com.bankle.common.asis.component.properties.LguFBDBProperties;
import com.bankle.common.asis.component.properties.LguFBEscrProperties;
import com.bankle.common.util.StringUtil;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component 
@Slf4j
@RequiredArgsConstructor
public class FirmBankingSocketClient {

    private final LguFBDBProperties lguFBDBProp;
    private final LguFBEscrProperties lguFBEscrProp;
    
    // LGU+ FirmBanking 소켓서버 ip, port
    String        socket_ip   = "";
    int           socket_port = 0;
    
    public String sendData    (String svrGbn, String data) {
        
        log.debug("=================================================================================");
        log.debug("=======테스트 용 로직 START !!!!");
        log.debug("=================================================================================");
        log.debug("개발 환경 상 DW/DV 테스트가 안되는 상황 임으로"                        );
        log.debug("EW/EV 로 들어오는 수신데이터를 DW/DV 로 변경하여 에스크로 입출금 테스트로 진행");
        log.debug("테스트 후 아래 url변경 로직은 삭제 해야 함."                       );
        log.debug("=================================================================================");
        if      ("DW".equals(svrGbn)) { svrGbn = "EW";}
        else if ("DV".equals(svrGbn)) { svrGbn = "EV";}
        log.debug("=================================================================================");
        log.debug("=======테스트 용 로직 END !!!!");                
        log.debug("=================================================================================");              
        
        return sendSocket(svrGbn, data, null, true ); 
    }
    
    public String sendDataByte(String svrGbn, byte[] data) {
        
        log.debug("=================================================================================");
        log.debug("=======테스트 용 로직 START !!!!");
        log.debug("=================================================================================");
        log.debug("개발 환경 상 DW/DV 테스트가 안되는 상황 임으로"                        );
        log.debug("EW/EV 로 들어오는 수신데이터를 DW/DV 로 변경하여 에스크로 입출금 테스트로 진행");
        log.debug("테스트 후 아래 url변경 로직은 삭제 해야 함."                       );
        log.debug("=================================================================================");
        if      ("DW".equals(svrGbn)) { svrGbn = "EW";}
        else if ("DV".equals(svrGbn)) { svrGbn = "EV";}
        log.debug("=================================================================================");
        log.debug("=======테스트 용 로직 END !!!!");                
        log.debug("=================================================================================");
        
        return sendSocket(svrGbn, null, data, false); 
    }
    
    public String sendSocket(String svrGbn, String strData, byte[] byteData, boolean strYN){

        Socket       socketDB = null;
        OutputStream os       = null;
        
        try {

            if (strYN) {
                log.info("서버로 보낼 데이터 (길이:" + strData.length() + ") : \n[" + strData + "]");
            } else { 
                log.info("서버로 보낼 데이터 (길이:" + byteData.length + ") [UTF-8] : \n[" + new String(byteData, "UTF-8") + "]");
                log.info("서버로 보낼 데이터 (길이:" + byteData.length + ") [MS949] : \n[" + new String(byteData, "MS949") + "]");
            }
            
            setServerInfo(svrGbn);            
            
            socketDB = new Socket(socket_ip, socket_port);
                        
            socketDB.setSoTimeout(30000);
            
            log.debug("socket : " + socketDB);
            
            os = socketDB.getOutputStream();
            if (strYN) { os.write(strData.getBytes()); }
            else       { os.write(byteData);           }

            os.flush();
            
        } catch (Exception e) {            
            e.printStackTrace();
            return "fail";
        } finally {
            if (os       != null) try { os      .close(); } catch (Exception Ex) {};
            if (socketDB != null) try { socketDB.close(); } catch (Exception Ex) {};
            log.debug("socketFB Close ===============================================");
        }
        return "success";
    }

    /*===================================================================================*/
    // #  svrGbn
    // DW [ DB WON ]  DV [ DB VR ]  EW [ ESCR WON ]   EV [ ESCR VR ]            
    /*===================================================================================*/    
    public void setServerInfo(String svrGbn) {
        
        String env = StringUtil.nvl(System.getProperty("spring.profiles.active"));
        
        if ("".equals(env) || "local".equals(env)) {
            
            socket_ip   = "183.111.103.68";
            
            if      ("DW".equals(svrGbn)) { socket_port = 30810; }
            else if ("DV".equals(svrGbn)) { socket_port = 30811; }
            else if ("EW".equals(svrGbn)) { socket_port = 30812; }
            else if ("EV".equals(svrGbn)) { socket_port = 30813; }
        } else {        
        
            if ("DW".equals(svrGbn) || "DV".equals(svrGbn) ) {
    
                socket_ip   = lguFBDBProp.getSvrIp();
                        
                if ("DW".equals(svrGbn)) { socket_port = Integer.parseInt(lguFBDBProp.getSvrWonPort()); }
                else                     { socket_port = Integer.parseInt(lguFBDBProp.getSvrVrPort ()); }
    
            } else {
                
                socket_ip   = lguFBEscrProp.getSvrIp();
    
                if ("EW".equals(svrGbn)) { socket_port = Integer.parseInt(lguFBEscrProp.getSvrWonPort()); }
                else                     { socket_port = Integer.parseInt(lguFBEscrProp.getSvrVrPort ()); }
                
            }     
        }
        
        log.debug("setServerInfo : socket_ip [" + socket_ip + "]   socket_port [" + socket_port + "]");
    }
}
