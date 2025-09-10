/*
package kr.co.anbu.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.anbu.domain.entity.ContractEscrow;
import kr.co.anbu.domain.service.ContractEscrowPaidService;
import kr.co.anbu.domain.service.ContractEscrowService;
import kr.co.anbu.domain.service.FARcvService;
import kr.co.anbu.domain.service.extnLk.LguFirmBankingService;
import kr.co.anbu.infra.ContractEscrowInfoDto;
import kr.co.anbu.infra.Response;
import kr.co.anbu.utils.StringCustUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/anbu")
public class AnbuTestController {

//    private final BatchTestMapper bactTestMapper;
    private final LguFirmBankingService     LFBService;
    private final ContractEscrowPaidService EscrService;
    private final FARcvService              faRcvService;
    private final ContractEscrowService     contractEscrowService;
    private final Response response;

    @PostMapping("/test")
    public @ResponseBody ResponseEntity<?> anbuTest(@RequestBody HashMap<String, Object> reqMap){

        HashMap<String, Object> resMap = new HashMap<>();
        String                  jobGbn = StringCustUtils.mapToString(reqMap, "jobGbn");
        
        resMap.put("RES_CD" , "9999");
        resMap.put("RES_MSG", "jobGbn [" + jobGbn + "] 을 확인하시기 바랍니다.");
        
        if      ("1".equals(jobGbn)) { resMap = sendBnkOpenTg  (reqMap); }
        else if ("2".equals(jobGbn)) { resMap = procEscrRom    (reqMap); }
        else if ("3".equals(jobGbn)) { resMap = procEscrPmnt   (reqMap); }
        else if ("4".equals(jobGbn)) { resMap = getAcctBlncAmt (); }
        else if ("5".equals(jobGbn)) { resMap = rcvRschRslt    (reqMap); }
        
        resMap.put("jobGbn", jobGbn);
        
        return response.success(resMap,"success", HttpStatus.OK);
    }
    
    // 개시전문
    public HashMap<String, Object> sendBnkOpenTg(HashMap<String, Object> paramMap) {
        
        HashMap<String, Object> resMap = new HashMap<>();
        
        resMap.put("RES_CD" , "0000");
        resMap.put("RES_MSG", ""    );
        
        String[] listServer = StringCustUtils.mapToString(paramMap, "SELECT_SERVER").split(",");
        String   res_cd     = "";
        String   res_msg    = "";
        String   tg_cd      = "";
        
        if (listServer.length > 0) {
            
            for(String serverNm : listServer) {
                
                tg_cd   = ("V".equals(serverNm.substring(1,2))) ? "VA01" : "WA01";                
                resMap  = LFBService.sendTrOpen(serverNm, tg_cd);                
                res_cd  = StringCustUtils.mapToString(resMap, "RES_CD" );

                if (!"0000".equals(res_cd)) {
                    res_msg = res_msg + "[" + serverNm + "] " + StringCustUtils.mapToString(resMap, "RES_MSG") + "\n";
                }                
            }
        }
        
        if (!"".equals(res_msg)) {
            resMap.put("RES_CD" , "9001" );
            resMap.put("RES_MSG", res_msg);
        } else {
            resMap.put("RES_MSG", "SUCCESS");
        }
        return resMap;
    }
    
    // 에스크로 입금
    public HashMap<String, Object> procEscrRom(HashMap<String, Object> paramMap) {
        
        HashMap<String, Object> resMap = new HashMap<>();
        
        paramMap.put("SVR_GBN" , "EW");
        paramMap.put("escrMKey", StringCustUtils.mapToString(paramMap, "ESCR_M_KEY" ));
        paramMap.put("BNK_CD"  , StringCustUtils.mapToString(paramMap, "ROM_BNK_CD" ));
        paramMap.put("ACCT_NO" , StringCustUtils.mapToString(paramMap, "ROM_ACCT_NO"));
        paramMap.put("NAME"    , StringCustUtils.mapToString(paramMap, "ROM_NM"     ));
        paramMap.put("AMT"     , StringCustUtils.mapToString(paramMap, "ROM_AMT"    ));
        
        resMap = LFBService.directTrn(paramMap); 

        return resMap;
    } 

    // 에스크로 출금
    public HashMap<String, Object> procEscrPmnt(HashMap<String, Object> paramMap) {
        
        HashMap<String, Object> resMap         = new HashMap<>();
        HashMap<String, String> changeParamMap = new HashMap<>();
        
        changeParamMap.put("escr_m_key", StringCustUtils.mapToString(paramMap, "ESCR_M_KEY"));

        try {
            resMap = EscrService.approval(changeParamMap);
        } catch (Exception Ex) {
            resMap.put("RES_CD" , "9001" );
            resMap.put("RES_MSG", Ex.getMessage());           
        }

        return resMap;
    }  
    
    // 에스크로 모계좌 잔액 조회
    public HashMap<String, Object> getAcctBlncAmt() {
        
        HashMap<String, Object> resMap         = new HashMap<>();
    
        resMap  = LFBService.sendSrchBlnc("EW");
        
        String acct_amt =  StringCustUtils.toDecimalFormat(StringCustUtils.mapToStringL(resMap, "RSLT_DATA"));

        resMap.put("ACCT_AMT", acct_amt);
        return resMap;
    }  
 
    public HashMap<String, Object> rcvRschRslt(HashMap<String, Object> paramMap) {
        
        HashMap<String, Object> resMap = new HashMap<>();
       
        resMap.put("RES_CD" , "0000");
        resMap.put("RES_MSG", ""    );
        
        String escr_m_key = StringCustUtils.mapToString(paramMap, "ESCR_M_KEY").trim();
        
        try {
            
            ContractEscrow contractEscrow = contractEscrowService.getContractEscrow(Long.parseLong(escr_m_key));
            
            if (!"2".equals(contractEscrow.getPrdtTpc())) {
                escr_m_key = StringCustUtils.lpad(escr_m_key, 20, "0");            
        
                paramMap.put("VMngCode"      , escr_m_key);   // 안부 에스크로 관리번호
                paramMap.put("RR"            , "1");   // 계약 의뢰 승낙 코드            
                paramMap.put("RRCode"        , "");   // 계약해지사유코드
                paramMap.put("DeniedRemark"  , "");   // 계약해지사유내용            
                paramMap.put("Premium"       , contractEscrow.getIsrnPrmm());   // 보험료
                paramMap.put("ReportUrl"     , "https://apitest.fatitle.co.kr:8443/anbu/v1/report/II9952245A00001");   // 확인서 URL
                paramMap.put("ReportContents", "");   // 등기부열람내용
                paramMap.put("ContentsDate"  , "");   // 등기부열람일시
                paramMap.put("ReportRemark"  , "");   // 비고 사항        
                
                faRcvService.updateRschRslt(paramMap);
            } else {
                resMap.put("RES_CD" , "8001" );
                resMap.put("RES_MSG", "에스크로 보험형이 아닙니다.[" + escr_m_key + "]");                
            }
        } catch (Exception  Ex) {
            resMap.put("RES_CD" , "9001" );
            resMap.put("RES_MSG", "계약내용 조회 오류 [" + escr_m_key + "]\n" + Ex.getMessage());           
        }
        
        return resMap;
    }      
    
           
}*/
