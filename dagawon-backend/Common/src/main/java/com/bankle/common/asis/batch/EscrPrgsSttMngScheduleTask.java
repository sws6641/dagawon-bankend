//package kr.co.anbu.batch;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import javax.transaction.Transactional;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import kr.co.anbu.component.infoTech.InfoTechService;
//import kr.co.anbu.domain.mapper.EscrMngMapper;
//import kr.co.anbu.domain.mapper.RgstrMapper;
//import kr.co.anbu.utils.EscrSendMsgUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class EscrPrgsSttMngScheduleTask {
//
//    private final EscrSendMsgUtils escrSendMsg;
//    private final EscrMngMapper    escrMngMapper;
//
//    private final RgstrMapper      rgstrMapper;
//    private final InfoTechService  infoTechService;
//
//    private int dbCnt;
//    private int romBfDay;
//
//    /*===========================================================================================*/
//    // setEscrRomReq 에스크로 입금요청 Update
//    /*===========================================================================================*/
//    @Scheduled(cron = "10 0/1 * * * ?")
//    public void setEscrRomReq() {
//
//        if (chkDev()) return;
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// EscrPrgsSttMngScheduleTask  입금요청 Start !!                                 ");
//        log.debug("/*=============================================================================*/");
//
//        ArrayList<HashMap<String, Object>> list = escrMngMapper.selectEscrRomReq();
//
//        log.debug("=====>> setEscrRomReq 에스크로 입금요청 [" + list.size() + "]");
//
//        list.forEach(map ->{
//
//            dbCnt =  escrMngMapper.updateEscrRomReq(map);
//
//            log.debug("=====>> setEscrRomReq  ESCR_M_KEY [" +         map.get("ESCR_M_KEY").toString()
//                                           + "] CHRG_DSC [" + (String)map.get("CHRG_DSC"  )
//                                         + "] ROM_PLN_DT [" + (String)map.get("ROM_PLN_DT") + "]");
//
//        });
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// EscrPrgsSttMngScheduleTask  입금요청 End !!                                   ");
//        log.debug("/*=============================================================================*/");
//    }
//
//    /*===========================================================================================*/
//    // setEscrPmntReq 에스크로 기본형 지급요청/지연
//    /*===========================================================================================*/
//    @Scheduled(cron = "20 0/1 * * * ?")
//    public void setEscrPmntReq() {
//
//        if (chkDev()) return;
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// setEscrPmntReq  에스크로 기본형 지급요청/지연 Start !!                        ");
//        log.debug("/*=============================================================================*/");
//
//        ArrayList<HashMap<String, Object>> list = escrMngMapper.selectEscrPmntReq();
//
//        log.debug("=====>> setEscrPmntReq 에스크로 기본형 지급요청/지연 대상 [" + list.size() + "]");
//
//        list.forEach(map ->{
//
//            dbCnt =  escrMngMapper.updateEscrPmntReq(map);
//
//            log.debug("=====>> setEscrPmntReq [" + map.get("ESCR_M_KEY").toString() + "] dbCnt [" + dbCnt + "]");
//
//        });
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// setEscrPmntReq  에스크로 기본형 지급요청/지연 End !!                          ");
//        log.debug("/*=============================================================================*/");
//    }
//
//    /*===========================================================================================*/
//    // selectAsgnVrAcct 가상계좌 할당
//    /*===========================================================================================*/
//    @Scheduled(cron = "40 0/1 * * * ?")
//    @Transactional
//    public void selectAsgnVrAcct(){
//
//        if (chkDev()) return;
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// 가상계좌할당 selectAsgnVrAcct Start !!!    ");
//        log.debug("/*=============================================================================*/");
//
//        ArrayList<HashMap<String, Object>> list     = escrMngMapper.selectAsgnVrAcct();
//        ArrayList<HashMap<String, Object>> listAcct = escrMngMapper.selectVrAcct();
//
//        int listCnt     = list.size();
//        int listAcctCnt = listAcct.size();
//
//        log.debug("=====>> 가상계좌할당 대상 건수 [" + listCnt + "]    미할당 가상계좌 [" + listAcctCnt + "]");
//
//        HashMap<String, Object> rowMap     = null;
//        HashMap<String, Object> rowAcctMap = null;
//
//        for (int i=0; i < listCnt; i++) {
//
//            if (i == listAcctCnt) {
//                log.debug("=====>> 할당 가능한 가상계좌 번호가 없습니다.");
//                //escrSendMsg.sendSMS("김정현", "01033554965", "에스크로입금계좌할당", "할당 가능한 가상계좌번호가 없습니다.");
//                break;
//            }
//
//            rowMap     = list    .get(i);
//            rowAcctMap = listAcct.get(i);
//
//            rowMap.put("VR_ACCT_NO", rowAcctMap.get("VR_ACCT_NO"));
//
//            escrMngMapper.insertVracctAsgn(rowMap);
//            escrMngMapper.updateVrAcct    (rowMap);
//
//        }
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// 가상계좌할당 selectAsgnVrAcct End !!!    ");
//        log.debug("/*=============================================================================*/");
//    }
//
//    /*===========================================================================================*/
//    // setEscrFn 에스크로 종료 Update
//    /*===========================================================================================*/
//    @Scheduled(cron = "50 0/1 * * * ?")
//    public void setEscrFn() {
//
//        if (chkDev()) return;
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// EscrPrgsSttMngScheduleTask  에스크로 종료 Start !!                            ");
//        log.debug("/*=============================================================================*/");
//
//        ArrayList<HashMap<String, Object>> list = escrMngMapper.selectEscrFn();
//
//        log.debug("=====>> setEscrFn 에스크로 종료대상 [" + list.size() + "]");
//
//        list.forEach(map ->{
//
//            String escr_m_key = StringCustUtils.mapToString(map, "ESCR_M_KEY");
//            String prdt_tpc   = StringCustUtils.mapToString(map, "PRDT_TPC"  );
//
//            dbCnt =  escrMngMapper.updateEscrFn(map);
//
//            /*==============================================================*/
//            // 알림톡/PUSH 전송
//            // 일반형만 완료시 발송 / 보험형은 확정일자 입력 시 전송
//            if ("1".equals(prdt_tpc)) {
//                escrSendMsg.sendMsg("", 37001, null, null);
//            } else {
//                escrSendMsg.sendMsg("", 36001, null, null);  // 확정일자 입려 요청
//            }
//            /*==============================================================*/
//
//            log.debug("=====>> setEscrFn [" + escr_m_key + "] dbCnt [" + dbCnt + "]");
//
//        });
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// EscrPrgsSttMngScheduleTask  에스크로 종료 End !!                               ");
//        log.debug("/*=============================================================================*/");
//    }
//
//
//
//    /*===========================================================================================*/
//    // setEscrRomDly 에스크로 입금지연 Update
//    /*===========================================================================================*/
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void setEscrRomDly() {
//
//        if (chkDev()) return;
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// EscrPrgsSttMngScheduleTask  입급지연 Start !!                                 ");
//        log.debug("/*=============================================================================*/");
//
//        ArrayList<HashMap<String, Object>> list = escrMngMapper.selectEscrRomDly();
//
//        log.debug("=====>> setEscrRomDly 에스크로 입금지연 [" + list.size() + "]");
//
//        list.forEach(map ->{
//
//            dbCnt =  escrMngMapper.updateEscrRomDly(map);
//
//            log.debug("=====>> setEscrRomDly  ESCR_M_KEY [" +         map.get("ESCR_M_KEY").toString()
//                                           + "] CHRG_DSC [" + (String)map.get("CHRG_DSC"  )
//                                         + "] ROM_PLN_DT [" + (String)map.get("ROM_PLN_DT") + "]");
//
//        });
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// EscrPrgsSttMngScheduleTask  입급지연 End !!                                   ");
//        log.debug("/*=============================================================================*/");
//    }
//
//    /*===========================================================================================*/
//    // setEscrPayDly 에스크로 지급지연 Update
//    /*===========================================================================================*/
//    @Scheduled(cron = "0 10 1 * * ?")
//    public void setEscrPayDly() {
//
//        if (chkDev()) return;
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// EscrPrgsSttMngScheduleTask  지급지연 Start !!                                 ");
//        log.debug("/*=============================================================================*/");
//
//        ArrayList<HashMap<String, Object>> list = escrMngMapper.selectEscrPayDly();
//
//        log.debug("=====>> setEscrPayDly 에스크로 지급지연 [" + list.size() + "]");
//
//        list.forEach(map ->{
//
//            dbCnt =  escrMngMapper.updateEscrPayDly(map);
//
//            log.debug("=====>> setEscrPayDly  ESCR_M_KEY [" +         map.get("ESCR_M_KEY").toString()
//                                           + "] CHRG_DSC [" + (String)map.get("CHRG_DSC"  )
//                                         + "] ROM_PLN_DT [" + (String)map.get("ROM_PLN_DT") + "]");
//
//        });
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// EscrPrgsSttMngScheduleTask  지급지연 End !!                                   ");
//        log.debug("/*=============================================================================*/");
//    }
//
//    /*===========================================================================================*/
//    // selectRgstrIcdnt 등기사건조회
//    /*===========================================================================================*/
//    @Scheduled(cron = "0 0 7 * * ?")
//    public void selectRgstrIcdnt() {
//
//        if (chkDev()) return;
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// selectRgstrIcdnt  등기사건조회 Start !!                                       ");
//        log.debug("/*=============================================================================*/");
//
//        ArrayList<HashMap<String, Object>> list = rgstrMapper.selectRgstrIncident();
//
//        log.debug("=====>> setEscrFn 등기사건조회 대상건수 [" + list.size() + "]");
//
//        list.forEach(map ->{
//
//            String escr_m_key   = map.get("ESCR_M_KEY").toString();
//            String err_yn       = (String)map.get("ERR_YN"      );
//            String rgstr_unq_no = (String)map.get("RGSTR_UNQ_NO");
//            String prty_nm      = (String)map.get("PRTY_NM"     );
//
//            log.debug("=====>> ESCR_M_KEY [" + escr_m_key + "] ERR_YN [" + err_yn + "] 등기고유번호 [" + rgstr_unq_no + "]");
//
//            if ("Y".equals(err_yn)) {
//                log.error("=====>> 등기고유번호 누락 !!!  ESCR_M_KEY [" + escr_m_key + "] 등기고유번호 [" + rgstr_unq_no + "]");
//            } else {
//                try {
//                    infoTechService.srchInfoTech(map);
//                } catch (Exception Ex) { Ex.printStackTrace(); }
//            }
//        });
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// selectRgstrIcdnt  등기사건조회 End !!                                        ");
//        log.debug("/*=============================================================================*/");
//    }
//
//    /*===========================================================================================*/
//    // selectEscrNoti
//    /*===========================================================================================*/
//    @Scheduled(cron = "40 0 8 * * ?")
//    public void selectEscrNoti(){
//
//        if (chkDev()) return;
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// selectEscrNoti Start !!!    ");
//        log.debug("/*=============================================================================*/");
//
//        ArrayList<HashMap<String, Object>> list = escrMngMapper.selectEscrNoti();
//
//        log.debug("=====>> setEscrPayDly 에스크로 입지급 지연 [" + list.size() + "]");
//
//        list.forEach(map ->{
//
//            String escr_m_key   = map.get("ESCR_M_KEY"  ).toString();
//            String prdt_tpc     = map.get("PRDT_TPC"    ).toString();
//            String escr_pgc     = map.get("ESCR_PGC"    ).toString();
//            String escr_dtl_pgc = map.get("ESCR_DTL_PGC").toString();
//            String chrg_dsc     = map.get("CHRG_DSC"    ).toString();
//            String chrg_dscnm   = map.get("CHRG_DSCNM"  ).toString();
//            String rom_pln_dt   = map.get("ROM_PLN_DT"  ).toString();
//            String bf_pln_day   = map.get("BF_PLN_DAY"  ).toString();
//            String af_pln_day   = map.get("AF_PLN_DAY"  ).toString();
//            String escr_amt     = map.get("ESCR_AMT"    ).toString();
//            String escr_state   = escr_dtl_pgc.substring(1,2);
//
//            Object[] arrRomReqPatten = {chrg_dscnm, bf_pln_day, rom_pln_dt};
//            Object[] arrRomDlyPatten = {chrg_dscnm, af_pln_day, rom_pln_dt};
//
//            log.debug(  "=====>> escr_m_key   [" + escr_m_key   + "]"
//                      + "        prdt_tpc     [" + prdt_tpc     + "]"
//                      + "        escr_pgc     [" + escr_pgc     + "]"
//                      + "        escr_dtl_pgc [" + escr_dtl_pgc + "]"
//                      + "        chrg_dsc     [" + chrg_dsc     + "]"
//                      + "        chrg_dscnm   [" + chrg_dscnm   + "]"
//                      + "        rom_pln_dt   [" + rom_pln_dt   + "]"
//                      + "        bf_pln_day   [" + bf_pln_day   + "]"
//                      + "        af_pln_day   [" + af_pln_day   + "]"
//                      + "        escr_amt     [" + escr_amt     + "]");
//
//            String memb_id = map.get("MEMB_ID").toString();  // 테스트를 위해 임시로
//
//            // 기본형
//            if ("1".equals(prdt_tpc)) {
//
//                if ("1,2,3".indexOf(escr_pgc) >= 0) {
//
//                    if ("1".equals(escr_state)) {
//                        if ("0".equals(bf_pln_day)) { escrSendMsg.sendMsg(escr_m_key, 34002, arrRomReqPatten, null); }  // 입금요청 당일
//                        else                        { escrSendMsg.sendMsg(escr_m_key, 34001, arrRomReqPatten, null); }  // 입금요청
//                    }
//                    if ("3".equals(escr_state))     { escrSendMsg.sendMsg(escr_m_key, 34003, arrRomDlyPatten, null); }  // 입금지연
//                }
//
//                if ("4".equals(escr_pgc)) {
//
//                    if ("1".equals(escr_state)) {
//                        if ("0".equals(af_pln_day)) { escrSendMsg.sendMsg(escr_m_key, 35002, arrRomReqPatten, null); }  // 지급요청 당일
//                        else                        { escrSendMsg.sendMsg(escr_m_key, 35001, arrRomReqPatten, null); }  // 지급요청
//                    }
//                    if ("3".equals(escr_state))     { escrSendMsg.sendMsg(escr_m_key, 35003, arrRomDlyPatten, null); }  // 지급지연
//                }
//            }
//
//            // 보험형
//            if ("2".equals(prdt_tpc)) {
//
//                if ("1".equals(escr_state)) {
//                    if ("0".equals(bf_pln_day)) { escrSendMsg.sendMsg(escr_m_key, 34002, arrRomReqPatten, null); }  // 입금요청 당일
//                    else                        { escrSendMsg.sendMsg(escr_m_key, 34001, arrRomReqPatten, null); }  // 입금요청
//                }
//                if ("3".equals(escr_state))     { escrSendMsg.sendMsg(escr_m_key, 34003, arrRomDlyPatten, null); }  // 입금지연
//
//                if ("4".equals(escr_state)) {
//                    if ("0".equals(af_pln_day)) { escrSendMsg.sendMsg(escr_m_key, 35002, arrRomReqPatten, null); }  // 지급요청 당일
//                    else                        { escrSendMsg.sendMsg(escr_m_key, 35001, arrRomReqPatten, null); }  // 지급요청
//                }
//                if ("6".equals(escr_state))     { escrSendMsg.sendMsg(escr_m_key, 35003, arrRomDlyPatten, null); }  // 지급지연
//            }
//        });
//
//        log.debug("/*=============================================================================*/");
//        log.debug("// selectEscrNoti End !!!    ");
//        log.debug("/*=============================================================================*/");
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
