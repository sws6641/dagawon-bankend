//package kr.co.anbu.batch;
//
//import java.util.HashMap;
//
//import org.json.simple.JSONObject;
//import org.springframework.http.HttpHeaders;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import kr.co.anbu.component.FaRestfulManager;
//import kr.co.anbu.domain.service.extnLk.LguFirmBankingService;
//import kr.co.anbu.utils.EscrSendMsgUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class LguFBTgScheduleTask {
//
//    private final LguFirmBankingService LFBService;
//    private final EscrSendMsgUtils      sendMsgService;
//
//    /*===========================================================================================*/
//    // FirmBanking 개시 전문
//    /*===========================================================================================*/
//    @Scheduled(cron = "0 0 5 * * ?")
//    public void allBankOpenTg() {
//
//        String runEnv = StringCustUtils.nvl(System.getProperty("spring.profiles.active"));
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// LguFirmBankingOpenTg  Start !! [" + runEnv + "]                               ");
//        log.debug("/*=============================================================================*/");
//
//        String smsMsg = "";
//
//        try {
//
//            int    i      = 0;
//            String res_cd  = "";
//            String res_msg = "";
//            HashMap<String, Object> resMap = new HashMap<String, Object>();
//
//            /*===================================================================================*/
//            // DW DB 원화거래 개시전문
//            /*===================================================================================*/
//            for (i=0;i<3;i++) {
//
//                resMap  = LFBService.sendTrOpen("DW", "WA01");
//                res_cd  = (String)resMap.get("RES_CD" );
//                res_msg = (String)resMap.get("RES_MSG");
//
//                log.debug("=====>> [DW] DB 원화거래 개시전문 [" + res_cd  + "]   " + res_msg);
//
//                if ("0000".equals(res_cd)) { smsMsg += " DW 정상\n"; break;}
//                smsMsg += " DW 실패\n";
//            }
//
//            /*===================================================================================*/
//            // DV DB 가상계좌 개시전문
//            /*===================================================================================*/
//            for (i=0;i<3;i++) {
//                resMap = LFBService.sendTrOpen("DV", "VA01");
//                res_cd  = (String)resMap.get("RES_CD" );
//                res_msg = (String)resMap.get("RES_MSG");
//
//                log.debug("=====>> [DV] DB 가상계좌 개시전문 [" + res_cd  + "]   " + res_msg);
//
//                if ("0000".equals(res_cd)) { smsMsg += " DV 정상\n"; break; }
//                smsMsg += " DV 실패\n";
//            }
//
//            /*===================================================================================*/
//            // EW Escrow 원화거래 개시전문
//            /*===================================================================================*/
//            for (i=0;i<3;i++) {
//                resMap = LFBService.sendTrOpen("EW", "WA01");
//                res_cd  = (String)resMap.get("RES_CD" );
//                res_msg = (String)resMap.get("RES_MSG");
//
//                log.debug("=====>> [EW] ESCROW 원화거래 개시전문 [" + res_cd  + "]   " + res_msg);
//
//                if ("0000".equals(res_cd)) { smsMsg += " EW 정상\n"; break; }
//                smsMsg += " EW 실패\n";
//            }
//
//            /*===================================================================================*/
//            // EV Escrow 가상계좌 개시전문
//            /*===================================================================================*/
//            for (i=0;i<3;i++) {
//                resMap = LFBService.sendTrOpen("EV", "VA01");
//                res_cd  = (String)resMap.get("RES_CD" );
//                res_msg = (String)resMap.get("RES_MSG");
//
//                log.debug("=====>> [EV] ESCROW 가상계좌 개시전문 [" + res_cd  + "]   " + res_msg);
//
//                if ("0000".equals(res_cd)) { smsMsg += " EV 정상\n"; break; }
//                smsMsg += " EV 실패\n";
//            }
//
//            if ("prod".equals(runEnv)) {
//
//                sendMsgService.sendSysMngSMS("펌뱅킹 개시전문 [" + runEnv + "]", smsMsg);
//            }
//            //
//        } catch (Exception Ex) {
//            Ex.printStackTrace();
//        }
//
//        log.debug("// LguFirmBankingOpenTg  End !!                                                  ");
//        log.debug("/*=============================================================================*/");
//    }
//
//    /*===========================================================================================*/
//    // 거래 명세 결번 요구
//    /*===========================================================================================*/
//    @Scheduled(cron = "0 10 5 * * ?")
//    public void trNmagMsnoReq() {
//
//        /* 에스크로 거래 테스트를 위해 주석 처리...  테스트 후 개발 진행 */
////        int trnCnt = fbTgInfoMapper.selectTrnCount();
////
////        ArrayList<HashMap<String, Object>> list    = fbTgInfoMapper.selectTrNmagMsno(trnCnt);
////        HashMap<String, Object>            dataMap = null;
////
////        int listCnt = list.size();
////        for (int i=0; i<listCnt; i++) {
////            dataMap = list.get(i);
////
////            // LFBS.
////
////        }
//    }
//}
