//package kr.co.anbu.controller;
//
//import java.util.HashMap;
//
//import org.json.simple.JSONObject;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import kr.co.anbu.component.FaRestfulManager;
//import kr.co.anbu.component.infoTech.InfoTechService;
//import kr.co.anbu.component.properties.FaProperties;
//import kr.co.anbu.domain.mapper.BatchTestMapper;
//import kr.co.anbu.domain.service.ContractEscrowPaidService;
//import kr.co.anbu.domain.service.extnLk.ExtnLkFAService;
//import kr.co.anbu.domain.service.extnLk.LguFirmBankingService;
//import kr.co.anbu.infra.Response;
//import kr.co.anbu.utils.FileCustUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/test")
//public class BatchTestController {
//
//    private final LguFirmBankingService LFBService;
//    private final FaProperties faProp;
//    private final Response response;
//    private final InfoTechService infoTech;
//    private final ExtnLkFAService extnLkFaService;
//    private final BatchTestMapper bactTestMapper;
//    private final ContractEscrowPaidService escrService;
//
//    @PostMapping("/Batch")
//    public @ResponseBody ResponseEntity<?> pushSend(@RequestBody(required=false) HashMap<String,Object> reqMap){
//
//        HashMap<String, Object> resMap = new HashMap<String, Object>();
//        try {
//            log.debug("=====>> testBach Call !!!!");
//            String batchNo  = (String)reqMap.get("batchNo" );
//            String escrMKey = (String)reqMap.get("escrMKey");
//            String chrgDsc  = (String)reqMap.get("chrgDsc" );
//
//
//            log.debug("=====>> batchNo [" + batchNo + "]   escrMKey [" + escrMKey + "]   chrgDsc [" + chrgDsc + "]");
//
//            if      ("1".equals(batchNo)) { resMap  = LFBService.sendTrOpen("DW", "WA01"); }
//            else if ("2".equals(batchNo)) { resMap  = LFBService.sendTrOpen("DV", "VA01"); }
//            else if ("3".equals(batchNo)) { resMap  = LFBService.sendTrOpen("EW", "WA01"); }
//            else if ("4".equals(batchNo)) { resMap  = LFBService.sendTrOpen("EV", "VA01"); }
//            else if ("5".equals(batchNo)) {
//                log.debug("==========================================================================");
//                log.debug("에스크로 잔액조회");
//                log.debug("==========================================================================");
//                resMap  = LFBService.sendSrchBlnc("EW");
//            }
//            else if ("6".equals(batchNo)) {
//                HashMap<String, String> paramMap = new HashMap<String, String>();
//                paramMap.put("escr_m_key",escrMKey);
//                resMap = escrService.approval(paramMap);
//                //resMap  = LFBService.sendEscrTrn(Long.parseLong(escrMKey), chrgDsc);
//            }
//            else if ("7".equals(batchNo)) { makeSendMsg(escrMKey);
//                resMap.put("RES_CD" , "0000");
//                resMap.put("RES_MSG", "WAS 로그를 보세요.");
//            }
//            else if ("8".equals(batchNo)) { resMap  = LFBService.directTrn(reqMap); }
//            else if ("9".equals(batchNo)) {
//                String filePathRoot = "/home/anbu2/";
//                String filePath     = filePathRoot + "20220630001946.png";
//                String fileToString = FileCustUtils.getFileToStringBase64(filePath);
//
//                log.debug("==========================================================================");
//                log.debug("File To String [" + filePath + "]");
//                log.debug("==========================================================================");
//                log.debug(fileToString);
//                log.debug("==========================================================================");
//                log.debug("String To File");
//                log.debug("==========================================================================");
//                filePath = filePathRoot + "20220630001946_test.png";
//                FileCustUtils.makeStringToFile(filePath, fileToString);
//                log.debug("==========================================================================");
//            }
//            else if ("10".equals(batchNo)) {
//
//                HashMap<String, Object> map = new HashMap<String, Object>();
//                map.put("ESCR_M_KEY"  , "566"           );
//                map.put("RGSTR_UNQ_NO", "27422017001021");
//                map.put("FEE_PAY_DT"  , "20220728"      );
//                map.put("PRTY_NM"     , "김혜실"        );
//                infoTech.srchInfoTech(map);
//
//            }
//            else if ("11".equals(batchNo)) {
//
//                log.debug("=====>> FA Alive Check !!! ");
//                HashMap<String, Object> paramMap = new HashMap<String, Object>();
//                HashMap<String, Object> rsltMap  = new HashMap<String, Object>();
//                /*===================================================================================*/
//                // 청약의뢰 전송 정보 셋팅
//                /*===================================================================================*/
//                paramMap.put("tg_cd"     , "FAA1" );
//                paramMap.put("tg_cd_s"   , "FAA1" );
//                paramMap.put("tg_cd_r"   , "FAA2" );
//                paramMap.put("escr_m_key", Long.parseLong(escrMKey));
//
//                String escr_m_key = reqMap.get("escrMKey").toString();
//                paramMap.put("escr_m_key", Long.parseLong(escr_m_key));
//
//                /*===================================================================================*/
//                // 청약의뢰등록 정보 전송
//                /*===================================================================================*/
//                rsltMap   = extnLkFaService.callFA(paramMap);
//                resMap.put("RES_CD" , (String)rsltMap.get("rslt_cd" ));
//                resMap.put("RES_MSG", (String)rsltMap.get("rslt_msg"));
//            }
//
//
//        } catch (Exception Ex) {
//            Ex.printStackTrace();
//            resMap.put("RES_CD" , "0");
//            resMap.put("RES_MSG", Ex.getMessage());
//        }
//        return response.success(resMap,"success", HttpStatus.OK);
//    }
//
//    public void makeSendMsg(String escr_m_key) {
//        JSONObject jsonObj = new JSONObject();
//
//
//        HashMap<String, Object> rowMap = bactTestMapper.selectEscr(escr_m_key);
//
//        //============================================================//
//        // 입금통지전문 송신 메세지 생성
//        //============================================================//
////        jsonObj.put("TransferAmount", "100000"              );
////        jsonObj.put("VMngCode"      , "00000000000000000674");
////        jsonObj.put("EscrowDate"    , "2022-10-27 16:09:11" );
////        jsonObj.put("VendorCode"    , "9995"                );
////        jsonObj.put("EOE"           , "Y"                   );
////        jsonObj.put("EscrowType"    , "01"                  );
////        jsonObj.put("Remark"        , ""                    );
//
//        //============================================================//
//        // 청약 조사결과 송신 메세지 생성
//        //============================================================//
//        jsonObj.put("VendorCode"     , "9995");
//        jsonObj.put("VMngCode"       , StringCustUtils.mapToString(rowMap, "ESCR_M_KEY"));
//        jsonObj.put("PolicyNum"      , StringCustUtils.mapToString(rowMap, "ISRN_SCRT_NO"));
//        jsonObj.put("RR"             , "1"   );
//        jsonObj.put("RRCode"         , ""    );
//        jsonObj.put("DeniedRemark"   , ""    );
//        jsonObj.put("Premium"        , StringCustUtils.mapToString(rowMap, "ISRN_PRMM"));
//        jsonObj.put("ReportUrl"      , "https://apitest.fatitle.co.kr:8443/anbu/v1/report/" + StringCustUtils.mapToString(rowMap, "ISRN_SCRT_NO"));
//        jsonObj.put("ReportContents" , ""    );
//        jsonObj.put("ContentsDate"   , StringCustUtils.mapToString(rowMap, "SRCH_NOW"));
//        jsonObj.put("ReportRemark"   , "* 등기내역과 관련한 특약조건에 따라 계약이 이행되어야 합니다.  * 전입조사된 세대가 누구인지 확인하고 계약을 진행하기 바랍니다.");
//        //============================================================//
//
//        String strJsonObj =  jsonObj.toJSONString();
//        log.debug("jsonObj [" + strJsonObj + "]");
//
//        FaRestfulManager   faRestfulManager = new FaRestfulManager(faProp);
//        HttpHeaders        httpHeader       = faRestfulManager.makeHeader();
//        String             httpBody         = faRestfulManager.makeBody(strJsonObj);
//
//        log.debug("httpBody : [" + httpBody + "]");
//    }
//}