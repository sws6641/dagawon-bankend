//package kr.co.anbu.batch;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import kr.co.anbu.domain.service.FASndService;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class FaSndScheduleTask {
//
//    private final FASndService faSndService;
//
//    @Scheduled(cron = "0 0/1 * * * ?")
//    public void faSscptAskReg() {
//
//        if (chkDev()) return;
//
//        try {
//
//            log.debug("/*=============================================================================*/");
//            log.debug("// FaSndScheduleTask  청약의뢰 Start !!                                          ");
//            log.debug("/*=============================================================================*/");
//
//            faSndService.faSscptAskReg();
//
//            log.debug("// FaSndScheduleTask  청약의뢰 End !!                                            ");
//            log.debug("/*=============================================================================*/");
//
//        } catch (Exception Ex) {
//            log.error("/*=============================================================================*/");
//            log.error("// FaSndScheduleTask  청약의뢰  Error"                                               );
//            log.error("/*=============================================================================*/");
//            Ex.printStackTrace();
//        }
//    }
//
//
//    /*===========================================================================================*/
//    // Func : faRomPmntSnd  [ 에스크로 입출금 내역 전송 ]
//    /*===========================================================================================*/
//    @Scheduled(cron = "0/30 * * * * ?")
//    public void faRomPmntSnd() throws Exception {
//
//        if (chkDev()) return;
//
//        try {
//
//            log.debug("/*=============================================================================*/");
//            log.debug("// FaSndScheduleTask  입출금 내역 전송 Start !!                                  ");
//            log.debug("/*=============================================================================*/");
//
//            faSndService.faRomPmntSnd();
//
//            log.debug("// FaSndScheduleTask  입출금 내역 전송 End !!                                         ");
//            log.debug("/*=============================================================================*/");
//
//        } catch (Exception Ex) {
//            log.error("/*=============================================================================*/");
//            log.error("// FaSndScheduleTask  입출금 내역 전송  Error"                                          );
//            log.error("/*=============================================================================*/");
//            Ex.printStackTrace();
//        }
//    }
//
//    public boolean chkDev() {
//
//        String env = System.getProperty("spring.profiles.active");
//        if ("".equals(StringCustUtils.nvl(env))) { env = "dev";}
//
//        return (StringCustUtils.equalsAny(env, "local", "dev")) ? true : false;
//    }
//}
