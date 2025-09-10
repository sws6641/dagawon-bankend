package com.bankle.common.asis.domain.service.extnLk;

import com.bankle.common.asis.component.FirmBankingTrnData;
import com.bankle.common.asis.component.frimBanking.FirmBankingSocketClient;
import com.bankle.common.asis.component.frimBanking.FirmBankingTgBean;
import com.bankle.common.asis.component.properties.FaProperties;
import com.bankle.common.asis.component.properties.LguFBEvrProperties;
import com.bankle.common.asis.domain.mapper.ContractEscrowMapper;
import com.bankle.common.asis.domain.mapper.FbTgInfoMapper;
import com.bankle.common.asis.domain.mapper.IfFaSndMapper;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.asis.utils.EscrSendMsgUtils;
import com.bankle.common.asis.utils.FirmBankingUtils;
import com.bankle.common.util.DateUtil;
import com.bankle.common.util.NumberUtil;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author 김배성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LguFirmBankingService {

    private final IfTgInfoMapper ifTgInfoMapper;
    private final FirmBankingSocketClient fbSocket;
    private final ExtnLkService extnLkSService;
    private final LguFBEvrProperties fbEnvProp;
    private final FaProperties faProp;
    private final FbTgInfoMapper fbTgInfoMapper;
    private final IfFaSndMapper ifFaSndMapper;
    private final FirmBankingUtils fbUtils;
    private long tg_log_m_key;
    private long chg_tg_log_m_key;
    private final EscrSendMsgUtils sendMsgService;
    private final ContractEscrowMapper escrMapper;
    private final EscrSendMsgUtils escrSendMsg;
    /*===================================================================================*/
    // #  svrGbn (서버 / 서비스 구분)
    /*===================================================================================*/
    // DW [ DB   원화거래 ] DV [ DB   가상계좌 ]
    // EW [ ESCR 원화거래 ] EV [ ESCR 가상계좌 ]
    /*===================================================================================*/

    /*===========================================================================================*/
    // sendTrOpen : 거래개시전문 VA01, WA01
    /*===========================================================================================*/
    public HashMap<String, Object> sendTrOpen(String svrGbn, String tg_cd) {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        String res_cd = "";

        try {
            resMap = sendSocket(svrGbn, "0800", "100", 0, null);
            res_cd = resMap.get("RES_CD").toString();

            if ("0000".equals(res_cd)) {

                log.debug("Call checkResponse tg_log_m_key [" + tg_log_m_key + "]");

                resMap = fbUtils.checkResponse(tg_log_m_key);
                res_cd = (String) resMap.get("RES_CD");

                if ("0000".equals(res_cd)) {
                    ifTgInfoMapper.updateBnkOpenDt(svrGbn);
                }
            }
        } catch (Exception Ex) {
            resMap.put("RES_CD", "8888");
            resMap.put("RES_MSG", Ex.getMessage());
            Ex.printStackTrace();
        }

        log.info("=====>> sendEscrTrn End   RES_CD [" + (String) resMap.get("RES_CD") + "]   RES_MSG [" + (String) resMap.get("RES_MSG") + "]");
        return resMap;
    }

    /*===========================================================================================*/
    // sendEscrTrn : 에스크로 이체 (DW)
    /*===========================================================================================*/
    public HashMap<String, Object> sendEscrTrn(long escr_m_key, String chrg_dsc) {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        String res_cd = "";
        String prdt_dsc = "";
        long pmnt_amt = 0;  // 지급대상금액 (미지급 입금액)

        paramMap.put("escr_m_key", escr_m_key);
        paramMap.put("chrg_dsc", chrg_dsc);

        try {
            HashMap<String, Object> selectMap = fbTgInfoMapper.selectTrnTrgt(paramMap);

            if (selectMap != null) {

                FirmBankingTrnData trnData = new FirmBankingTrnData();

                prdt_dsc = StringUtil.mapToString(selectMap, "PRDT_DSC");
                pmnt_amt = Long.parseLong(StringUtil.mapToString(selectMap, "PMNT_AMT"));

                log.debug(
                        "\n====================================================================="
                                + "\n=====>> 에스크로 키 [" + escr_m_key + "]   지급금액 [" + pmnt_amt + "]"
                                + "\n====================================================================="
                );

                if (pmnt_amt > 0) {

                    trnData.setTr_acct_bnk_cd((String) selectMap.get("PMNT_BNK_CD"));
                    trnData.setTr_acct_no((String) selectMap.get("PMNT_ACCT_NO"));
                    trnData.setTrmn_nm((String) selectMap.get("PRTY_NM"));
                    trnData.setTrn_amt(pmnt_amt);

                    String svrGbn = "7".equals(chrg_dsc) ? "EW" : "DW";  // 7:수수로 환급 (에스크로 모계좌 지급)

                    resMap = sendBnkTrn(svrGbn, escr_m_key, trnData);
                    res_cd = resMap.get("RES_CD").toString();

                    if ("0000".equals(res_cd)) {

                        resMap = fbUtils.checkResponse(tg_log_m_key);
                        res_cd = (String) resMap.get("RES_CD");

                        if ("0000".equals(res_cd)) {
                            paramMap.put("pmnt_amt", pmnt_amt);
                            fbTgInfoMapper.updateEscrPmntResult(paramMap);

                            // 에스크로 기본형 [1] / 대금지급 [4] 인 경우 TET_ESCR_D 의 금액 모두를 지급처리 UPDATE
                            if ("1".equals(prdt_dsc) && ("4".equals(chrg_dsc) || "8".equals(chrg_dsc))) {
                                escrMapper.updateEscrDPmnt(selectMap);
                            }

                        } else {
                            fbTgInfoMapper.insertTeiTrnErrI(escr_m_key);

                            String smsMsg = "에스크로번호 [" + escr_m_key + "]  성명 [" + trnData.getTrmn_nm() + "]  금액 [" + NumberUtil.getNumberFormat(pmnt_amt) + "] 대금지급 계좌이체 중 오류가 발생했습니다.";
                            sendMsgService.sendSysMngSMS("계좌이체오류 [" + svrGbn + "]", smsMsg);
                        }
                    }
                } else {
                    resMap.put("RES_CD", "7771");
                    resMap.put("RES_MSG", "지급금액이 0원 입니다.");
                }
            } else {
                resMap.put("RES_CD", "8881");
                resMap.put("RES_MSG", "출금 대상/계좌 정보가 없거나 이미 출금 되었습니다.");
            }
        } catch (Exception Ex) {
            resMap.put("RES_CD", "8888");
            resMap.put("RES_MSG", Ex.getMessage());
            Ex.printStackTrace();
        }


        log.info("=====>> sendEscrTrn End   RES_CD [" + (String) resMap.get("RES_CD") + "]   RES_MSG [" + (String) resMap.get("RES_MSG") + "]");
        return resMap;
    }

    /*===========================================================================================*/
    // sendIsrnPrmmTrn : 보험료 이체 (EW)
    /*===========================================================================================*/
    public HashMap<String, Object> sendIsrnPrmmTrn(long escr_m_key) {

        log.info("=====>> sendIsrnPrmmTrn");

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        String res_cd = "";
        long trnAmt = 0;

        paramMap.put("escr_m_key", escr_m_key);

        try {

            HashMap<String, Object> selectMap = fbTgInfoMapper.selectIsrnPrmm(paramMap);

            if (selectMap != null) {
                FirmBankingTrnData trnData = new FirmBankingTrnData();

                trnAmt = Long.parseLong(selectMap.get("ISRN_PRMM").toString());  // 보험료

                trnData.setTr_acct_bnk_cd("0" + faProp.getBankCode());
                trnData.setTr_acct_no(faProp.getAccNo());
                trnData.setTrmn_nm((String) selectMap.get("ISRN_PRMM"));
                trnData.setTrn_amt(trnAmt);

                resMap = sendBnkTrn("EW", escr_m_key, trnData);
                res_cd = resMap.get("RES_CD").toString();

                if ("0000".equals(res_cd)) {

                    resMap = fbUtils.checkResponse(tg_log_m_key);
                    res_cd = (String) resMap.get("RES_CD");

                    if (!"0000".equals(res_cd)) {
                        fbTgInfoMapper.insertTeiTrnErrI(escr_m_key);

                        String smsMsg = "에스크로번호 [" + escr_m_key + "]  성명 [" + trnData.getTrmn_nm() + "]  금액 [" + NumberUtil.getNumberFormat(trnAmt) + "] 보험료 계좌이체 중 오류가 발생했습니다.";
                        sendMsgService.sendSysMngSMS("계좌이체오류 [EW]", smsMsg);
                    }
                }
            } else {
                resMap.put("RES_CD", "8881");
                resMap.put("RES_MSG", "보험료 지급 대상 에스크로 계약이 없습니다. [" + escr_m_key + "]");
            }
        } catch (Exception Ex) {
            resMap.put("RES_CD", "8888");
            resMap.put("RES_MSG", Ex.getMessage());
            Ex.printStackTrace();
        }

        return resMap;
    }

    /*===========================================================================================*/
    // sendDbBnkTrn : 은행이체전문 (당행 / 타행) WA02, WA03 
    // svrGbn       : DW [ DB 원화 ], EW [ 에스크로 원화 ] 
    /*===========================================================================================*/
    public HashMap<String, Object> sendBnkTrn(String svrGbn, long escr_m_key, FirmBankingTrnData trnData) {

        if ("020".equals(trnData.getTr_acct_bnk_cd())) {
            return sendSocket(svrGbn, "0100", "100", escr_m_key, trnData);
        } else {
            return sendSocket(svrGbn, "0101", "100", escr_m_key, trnData);
        }
    }

    /*===========================================================================================*/
    // sendDbBnkTrn : 이체처리결과조회  WA04
    // trnData      : trn_tg_trns_dt;  // (이체결과조회) 이체전문전송일자
    //                trn_tg_trns_no;  // (이체결과조회) 이체전문전송번호    
    /*===========================================================================================*/
    public HashMap<String, Object> sendTrnProcRslt(String svrGbn, FirmBankingTrnData trnData) {

        HashMap<String, Object> resMap = new HashMap<String, Object>();
        String res_cd = "";

        try {
            resMap = sendSocket(svrGbn, "0600", "100", 0, trnData);
            res_cd = resMap.get("RES_CD").toString();

            if ("0000".equals(res_cd)) {
                resMap = fbUtils.checkResponse(tg_log_m_key);
            }
        } catch (Exception Ex) {
            resMap.put("RES_CD", "8888");
            resMap.put("RES_MSG", Ex.getMessage());
        }

        return resMap;
    }

    /*===========================================================================================*/
    // sendDbBnkTrn : 잔액조회 WA05
    /*===========================================================================================*/
    public HashMap<String, Object> sendSrchBlnc(String svrGbn) {

        HashMap<String, Object> resMap = new HashMap<String, Object>();
        String res_cd = "";

        try {
            resMap = sendSocket(svrGbn, "0600", "300", 0, null);
            res_cd = resMap.get("RES_CD").toString();

            if ("0000".equals(res_cd)) {
                resMap = fbUtils.checkResponse(tg_log_m_key);

                res_cd = (String) resMap.get("RES_CD");
                if ("0000".equals(res_cd)) {
                    getRcvTgInfo(resMap, "WTDW_PSSB_AMT");  // 즉시이체가능금액                    
                }
            }
        } catch (Exception Ex) {
            resMap.put("RES_CD", "8888");
            resMap.put("RES_MSG", Ex.getMessage());
            Ex.printStackTrace();
        }

        return resMap;
    }

    /*===========================================================================================*/
    // sendTrnSmmr : 이체집계 WA06
    /*===========================================================================================*/
    public HashMap<String, Object> sendTrnSmmr(String svrGbn) {
        return sendSocket(svrGbn, "0700", "100", 0, null);
    }

    /*===========================================================================================*/
    // sendTrnSmmr : 명세결번요구
    // trnData     : nmag_noti_trns_dt;   // (예금거래명세통지) 명세통지전송일자
    //               nmag_noti_tg_no  ;   // (예금거래명세통지) 명세통지전문번호
    /*===========================================================================================*/
    public HashMap<String, Object> sendNmagMsnoReq(String svrGbn, FirmBankingTrnData trnData) {
        return sendSocket(svrGbn, "0200", "640", 0, trnData);
    }

    /*===========================================================================================*/
    // sendDfbnkUnblMsnoReq : 타행불능결번요구
    // trnData              : nmag_noti_trns_dt;   // (예금거래명세통지) 명세통지전송일자
    //                        nmag_noti_tg_no  ;   // (예금거래명세통지) 명세통지전문번호
    /*===========================================================================================*/
    public HashMap<String, Object> sendDfbnkUnblMsnoReq(String svrGbn, FirmBankingTrnData trnData) {
        return sendSocket(svrGbn, "0400", "640", 0, trnData);
    }

    /*===========================================================================================*/
    // receiveSocket : FirmBanking 전문 수신
    /*===========================================================================================*/
    public HashMap<String, Object> receiveSocket(String svrGbn, String tg_cd, InputStream is) {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        long escr_m_key = 0;

        try {

            paramMap.put("tg_cd", tg_cd);
            log.info("====>> receiveSocket   svrGbn [" + svrGbn + "]    tg_cd [" + tg_cd + "]");

            HashMap<String, Object> selectTgM = ifTgInfoMapper.selectTgM(paramMap);

            List<HashMap<String, Object>> listTgD = ifTgInfoMapper.selectFBTgD(paramMap);

            String snd_rcv_dsc = selectTgM.get("SND_RCV_DSC").toString();

            log.debug("=====>> snd_rcv_dsc [" + snd_rcv_dsc + "]");

            FirmBankingTgBean fbTgBean = new FirmBankingTgBean(listTgD);

            fbTgBean.readDataStream(is);

            String tmpLog = fbTgBean.toString("Y");  // IF 전문 로그용

            String res_cd = "";

            if ("1".equals(snd_rcv_dsc)) {  // ESCROW 송신에 대한 Response


                escr_m_key = setRcvIdvdptTg(svrGbn, tg_cd, fbTgBean);

                log.info("====>> receiveSocket   escr_m_key [" + escr_m_key + "]");

                updateTgLog(tg_cd, escr_m_key, "R", fbTgBean);

                res_cd = fbTgBean.getData("RES_CD", "N").trim();
                log.info("====>> receiveSocket   res_cd [" + res_cd + "]");
                fbUtils.getErrMsg("WA", res_cd, paramMap);  // paramMap => RES_CD, RES_MSG 셋팅

            } else {

                log.info("====>> receiveSocket   insertTgLog Start ~~");
                insertTgLog(tg_cd, escr_m_key, "R", fbTgBean);


                log.debug("=================================================================================");
                log.debug("=======테스트 용 로직 START !!!!");
                log.debug("=================================================================================");
                log.debug("개발 환경 상 DW/DV 테스트가 안되는 상황 임으로");
                log.debug("EW/EV 로 들어오는 수신데이터를 DW/DV 로 변경하여 에스크로 입출금 테스트로 진행");
                log.debug("테스트 후 아래 url변경 로직은 삭제 해야 함.");
                log.debug("=================================================================================");

                // 최종 반영시 if / else if / else  삭제하고
                // esle 밑의 주석 된 부분 살림
                if ("DW".equals(svrGbn)) {
                    setRcvSndCmnptArea("EW", fbTgBean);
                } else if ("DV".equals(svrGbn)) {
                    setRcvSndCmnptArea("EV", fbTgBean);
                } else {
                    setRcvSndCmnptArea(svrGbn, fbTgBean);
                }  // 수신 전문에 대한 송신응답전문 공통부 변경

                // setRcvSndCmnptArea(svrGbn, fbTgBean);  // 수신 전문에 대한 송신응답전문 공통부 변경
                log.debug("=================================================================================");
                log.debug("=======테스트 용 로직 END !!!!");
                log.debug("=================================================================================");

                escr_m_key = setRcvIdvdptTg(svrGbn, tg_cd, fbTgBean);

                log.info("====>> receiveSocket   escr_m_key [" + escr_m_key + "]");
                tmpLog = fbTgBean.toString("Y");  // IF 전문 로그용                

                updateTgLog(tg_cd, escr_m_key, "S", fbTgBean);

                res_cd = fbTgBean.getData("RES_CD", "N");
                fbUtils.getErrMsg("WA", res_cd, paramMap);  // paramMap => RES_CD, RES_MSG 셋팅

            }

        } catch (Exception Ex) {
            /*===================================================================================*/
            // Exception 처리 Logic 추가
            /*===================================================================================*/
            paramMap.put("RES_CD", "A809"); /* 시스템 장애    (업무장애) */
            paramMap.put("RES_MSG", Ex.getMessage());
            log.info("=====>> receiveSocket Exception\n" + Ex.getMessage());
            Ex.printStackTrace();
        }

        return paramMap;
    }

    /*===========================================================================================*/
    // 전문 셋팅 후 송신
    /*===========================================================================================*/
    public HashMap<String, Object> sendSocket(String svrGbn, String tg_dsc, String wk_dsc, long escr_m_key, FirmBankingTrnData trnData) {


        log.debug("=================================================================================");
        log.debug("=================================================================================");
        log.debug("=================================================================================");
        log.debug("");
        log.debug("=======테스트 용 로직 START !!!!");
        log.debug("");
        log.debug("=================================================================================");
        log.debug("=================================================================================");
        log.debug("=================================================================================");
        log.debug("개발 환경 상 DW/DV 테스트가 안되는 상황 임으로");
        log.debug("EW/EV 로 들어오는 수신데이터를 DW/DV 로 변경하여 에스크로 입출금 테스트로 진행");
        log.debug("테스트 후 아래 svrGbn 변경 로직은 삭제 해야 함.");
        log.debug("");
        log.debug("DW ==> EW");
        log.debug("DV ==> EV");
        log.debug("=================================================================================");
        if ("DW".equals(svrGbn)) svrGbn = "EW";
        if ("DV".equals(svrGbn)) svrGbn = "EV";
        log.debug("=================================================================================");
        log.debug("");
        log.debug("=======테스트 용 로직 END !!!!");
        log.debug("");
        log.debug("=================================================================================");
        log.debug("=================================================================================");
        log.debug("=================================================================================");


        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        String tg_cd = fbUtils.getTgCd(svrGbn, tg_dsc, wk_dsc);

        log.info("=====>> sendSocket svrGbn [" + svrGbn + "]   tg_cd [" + tg_cd + "]   tg_dsc [" + tg_dsc + "]   wk_dsc [" + wk_dsc + "]");

        paramMap.put("tg_cd", tg_cd);

        List<HashMap<String, Object>> listTgD = ifTgInfoMapper.selectFBTgD(paramMap);

        try {

            FirmBankingTgBean fbTgBean = new FirmBankingTgBean(listTgD);

            setCmnptArea(svrGbn, tg_dsc, wk_dsc, fbTgBean);  // 공통부

            setSndIdvdptTg(svrGbn, tg_cd, fbTgBean, trnData);  // 개별부

            insertTgLog(tg_cd, escr_m_key, "S", fbTgBean);                   // Send 전문 Log 저장

            String tmpLog = fbTgBean.toString("Y");  // IF 전문 로그용

            String sendResult = fbSocket.sendDataByte(svrGbn, fbTgBean.toAllByte());

            if ("success".equals(sendResult)) {
                paramMap.put("RES_CD", "0000");
                paramMap.put("RES_MSG", "정상");
            } else {
                paramMap.put("RES_CD", "7001");
                paramMap.put("RES_MSG", "Socket 전송 실패");
            }
        } catch (Exception Ex) {
            /*===================================================================================*/
            // Exception 처리 Logic 추가
            /*===================================================================================*/
            paramMap.put("RES_CD", "FB01");
            paramMap.put("RES_MSG", Ex.getMessage());
            log.info("=====>> sendSocket Exception\n" + Ex.getMessage());
            Ex.printStackTrace();
        }

        return paramMap;

    }

    /*===========================================================================================*/
    // 수신전문에 대한 송신응답전문 공통부 셋팅
    /*===========================================================================================*/
    public void setRcvSndCmnptArea(String svrGbn, FirmBankingTgBean fbTgBean) throws Exception {

        String CURRENT_DATE = DateUtil.getDate("yyyyMMdd");
        String CURRENT_TIME = DateUtil.getDate("kkmmss");

        fbTgBean.setData("SNDER_FLAG", "2");    //  3. 송신자FLAG1
        fbTgBean.setData("SNDER_ID", fbEnvProp.getSndId(svrGbn));    //  4. 송신자 ID
        fbTgBean.setData("RCVER_ID", fbEnvProp.getRcvId(svrGbn));    //  5. 수신자 ID
        fbTgBean.setData("TRNS_DT", CURRENT_DATE);    // 12. 전송일자
        fbTgBean.setData("TRNS_TM", CURRENT_TIME);    // 13. 전송시간
    }

    /*===========================================================================================*/
    // 전문 공통부 셋팅
    /*===========================================================================================*/
    public void setCmnptArea(String svrGbn, String tg_dsc, String wk_dsc, FirmBankingTgBean fbTgBean) throws Exception {

        String CURRENT_DATE = DateUtil.getDate("yyyyMMdd");
        String CURRENT_TIME = DateUtil.getDate("kkmmss");

        fbTgBean.setData("ID_CD", "");    //  1. 식별코드
        fbTgBean.setData("SRVC_DSC", fbEnvProp.getSrvcDsc(svrGbn));    //  2. 서비스구분
        fbTgBean.setData("SNDER_FLAG", "1");    //  3. 송신자FLAG1
        fbTgBean.setData("SNDER_ID", fbEnvProp.getSndId(svrGbn));    //  4. 송신자 ID
        fbTgBean.setData("RCVER_ID", fbEnvProp.getRcvId(svrGbn));    //  5. 수신자 ID
        fbTgBean.setData("MSG_ID", "");    //  6. 메시지 ID
        fbTgBean.setData("CUST_CD", fbEnvProp.getCustId(svrGbn));    //  7. 고객코드
        fbTgBean.setData("BNK_CD", "20");    //  8. 은행코드
        fbTgBean.setData("TG_DSC", tg_dsc);    //  9. 전문코드
        fbTgBean.setData("WK_DSC", wk_dsc);    // 10. 업무구분
        fbTgBean.setData("TG_NO", "");    // 11. 전문번호
        fbTgBean.setData("TRNS_DT", CURRENT_DATE);    // 12. 전송일자
        fbTgBean.setData("TRNS_TM", CURRENT_TIME);    // 13. 전송시간
        fbTgBean.setData("RES_CD", "");    // 14. 응답코드
        fbTgBean.setData("RSRV_AR", "");    // 15. 예비영역
    }

    /*===========================================================================================*/
    // setSndIdvdptTg : 송신 전문 개별부 셋팅
    /*===========================================================================================*/
    public void setSndIdvdptTg(String svrGbn, String tg_cd, FirmBankingTgBean fbTgBean, FirmBankingTrnData trnData) throws Exception {

        if ("VA01".equals(tg_cd) || "WA01".equals(tg_cd)) {
            /*===================================================================================*/
            // [ VA01 ] LGU+ 펌뱅킹 가상계좌 개시전문
            // [ WA01 ] LGU+ 펌뱅킹 원화거래 개시전문
            /*===================================================================================*/
            fbTgBean.setData("CMNL_APL_DSC", "N");
            fbTgBean.setData("CMNPT_BNK_CD", "020");

        } else if ("WA02".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA02 ] LGU+ 펌뱅킹 원화거래 당행이체
            /*===================================================================================*/

            fbTgBean.setData("MO_ACCT_NO", fbEnvProp.getAcctNo(svrGbn));   // 01. 모 계좌번호
            fbTgBean.setData("PWD", fbEnvProp.getAcctPW(svrGbn));   // 02. 모 계좌 통장 비밀번호
            fbTgBean.setData("DUL_PWD", fbUtils.makePW(svrGbn, trnData));   // 03. 복기부호 (은행과 협의 후 사용)
            fbTgBean.setData("TR_DSC", "30");   // 04. 수납(자동이체) = “20”, 지급 = “30”
            fbTgBean.setData("TRN_AMT", trnData.getTrn_amt() + "");   // 05. 이체 금액(신한은행은 부분수납출금 가능(협의))
            fbTgBean.setData("TR_ACCT_BNK_CD", "20");   // 08. 거래 계좌 은행코드, (국민은행 수납이체는 ‘99’)
            fbTgBean.setData("TR_ACCT_NO", trnData.getTr_acct_no());   // 09. 거래 계좌번호
            fbTgBean.setData("TRNMN_NM", trnData.getTrmn_nm());   // 12. 통장인자 부분 ? 은행과 협의
            fbTgBean.setData("CMNL_APL_DSC", "N");   // 19. 적용고객: Y  미적용고객: N
            fbTgBean.setData("CMNPT_BNK_CD", "020");   // 20. 3자리 은행코드
            fbTgBean.setData("IDVDPT_BNK_CD", "020");   // 21. 거래계좌 은행코드(3자리)

        } else if ("WA03".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA03 ] LGU+ 펌뱅킹 원화거래 타행이체
            /*===================================================================================*/

            fbTgBean.setData("MO_ACCT_NO", fbEnvProp.getAcctNo(svrGbn));   // 모 계좌 번호
            fbTgBean.setData("PWD", fbEnvProp.getAcctPW(svrGbn));   // 비밀번호
            fbTgBean.setData("DUL_PWD", fbUtils.makePW(svrGbn, trnData));   // 이중 암호
            fbTgBean.setData("TR_DSC", "30");   // 거래 구분코드
            fbTgBean.setData("TRN_AMT", trnData.getTrn_amt() + "");   // 이체 금액
            fbTgBean.setData("TR_ACCT_BNK_CD", trnData.getTr_acct_bnk_cd().substring(1, 3));   // 거래 계좌 은행 코드
            fbTgBean.setData("TR_ACCT_NO", trnData.getTr_acct_no());   // 거래 계좌 번호
            fbTgBean.setData("TRNMN_NM", trnData.getTrmn_nm());   // 송금인 명
            fbTgBean.setData("CMNL_APL_DSC", "N");   // 자통법 적용 구분코드
            fbTgBean.setData("CMNPT_BNK_CD", "020");   // 공통부 은행 코드
            fbTgBean.setData("IDVDPT_BNK_CD", trnData.getTr_acct_bnk_cd());   // 개별부 은행 코드

        } else if ("WA04".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA04 ] LGU+ 펌뱅킹 원화거래 이체처리결과조회
            /*===================================================================================*/
            fbTgBean.setData("TRN_TG_TRNS_DT", trnData.getTrn_tg_trns_dt());   // 이체전문전송일자
            fbTgBean.setData("TRN_TG_TG_NO", trnData.getTrn_tg_trns_no());   // 이체전문전송번호
            fbTgBean.setData("CMNL_APL_DSC", "N");   // 자통법 적용 구분코드
            fbTgBean.setData("CMNPT_BNK_CD", "020");   // 공통부 은행 코드

        } else if ("WA05".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA05 ] LGU+ 펌뱅킹 원화거래 잔액조회
            /*===================================================================================*/
            fbTgBean.setData("SRCH_ACCT_NO", fbEnvProp.getAcctNo(svrGbn));   // 자통법 적용 구분코드
            fbTgBean.setData("CMNL_APL_DSC", "N");   // 자통법 적용 구분코드
            fbTgBean.setData("CMNPT_BNK_CD", "020");   // 공통부 은행 코드

        } else if ("WA06".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA06 ] LGU+ 펌뱅킹 원화거래 이체집계 
            /*===================================================================================*/
            fbTgBean.setData("SRCH_ACCT_NO", fbEnvProp.getAcctNo(svrGbn));   // 자통법 적용 구분코드
            fbTgBean.setData("CMNL_APL_DSC", "N");   // 자통법 적용 구분코드
            fbTgBean.setData("CMNPT_BNK_CD", "020");   // 공통부 은행 코드

        } else if ("WA07".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA07] LGU+ 펌뱅킹 원화거래 예금거래명세통지 
            /*===================================================================================*/
            fbTgBean.setData("SRCH_ACCT_NO", fbEnvProp.getAcctNo(svrGbn));   // 자통법 적용 구분코드
            fbTgBean.setData("CMNL_APL_DSC", "N");   // 자통법 적용 구분코드
            fbTgBean.setData("CMNPT_BNK_CD", "020");   // 공통부 은행 코드

        } else if ("WA09".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA09 ] LGU+ 펌뱅킹 원화거래 예금거래명세통지 
            /*===================================================================================*/
            fbTgBean.setData("NMAG_NOTI_TRNS_DT", trnData.getNmag_noti_trns_dt());   // 명세 통지 전송 일자
            fbTgBean.setData("NMAG_NOTI_TG_NO", trnData.getNmag_noti_tg_no());   // 명세 통지 전문 번호
            fbTgBean.setData("CMNL_APL_DSC", "N");   // 자통법 적용 구분코드
            fbTgBean.setData("CMNPT_BNK_CD", "020");   // 공통부 은행 코드

        } else if ("WA10".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA10 ] LGU+ 펌뱅킹 원화거래 예금거래명세통지 
            /*===================================================================================*/
            fbTgBean.setData("UNBL_NOTI_TRNS_DT", trnData.getNmag_noti_trns_dt());   // 불능 통지 전송 일자
            fbTgBean.setData("UNBL_NOTI_TG_NO", trnData.getNmag_noti_tg_no());   // 불능 통지 전문 번호
            fbTgBean.setData("CMNL_APL_DSC", "N");   // 자통법 적용 구분코드
            fbTgBean.setData("CMNPT_BNK_CD", "020");   // 공통부 은행 코드

        }

    }

    /*===========================================================================================*/
    // setRcvIdvdptTg : 수신 전문 개별부 셋팅
    /*===========================================================================================*/
    public long setRcvIdvdptTg(String svrGbn, String tg_cd, FirmBankingTgBean fbTgBean) throws Exception {

        int chkVal = 0;
        boolean reSndYn = false;
        long escr_m_key = 0;

        log.info("=====>> setRcvIdvdptTg   svrGbn [" + svrGbn + "]   tg_cd [" + tg_cd + "]");

        HashMap<String, Object> paramMap = fbTgBean.getDataMap();
        HashMap<String, Object> selectMap = new HashMap<String, Object>();

        if ("VA01".equals(tg_cd) || "WA01".equals(tg_cd)) {
            /*===================================================================================*/
            // [ VA01 ] LGU+ 펌뱅킹 가상계좌 개시전문
            // [ WA01 ] LGU+ 펌뱅킹 원화거래 개시전문
            /*===================================================================================*/
            // 별도 개별부 셋팅 없음.

        } else if ("VA02".equals(tg_cd)) {

            /*===================================================================================*/
            // [ VA02 ] LGU+ 펌뱅킹 가상계좌 수취인 조회 요청전문
            /*===================================================================================*/
            fbUtils.getTgDsc(tg_cd, fbTgBean);

            if ("DV".equals(svrGbn)) {
                selectMap = fbTgInfoMapper.selectAcctMembNmDV(paramMap);
            }
            if ("EV".equals(svrGbn)) {
                selectMap = fbTgInfoMapper.selectAcctMembNmEV(paramMap);
            }

            if (selectMap == null) {

                fbTgBean.setData("RES_CD", "0711");  // 수취인 계좌 없음

            } else {

                escr_m_key = (Long) selectMap.get("ESCR_M_KEY");
                chkVal = (Integer) selectMap.get("CHK_VAL");

                if (chkVal == 0) {
                    fbTgBean.setData("RES_CD", "0711");
                }  // 수취인 계좌 없음
                else {
                    fbTgBean.setData("RES_CD", "0000");
                }

            }

            fbTgBean.setData("TG_DSC", "0310");

            reSndYn = true;

        } else if ("VA03".equals(tg_cd)) {
            /*===================================================================================*/
            // [ VA03 ] LGU+ 펌뱅킹 가상계좌 통지 전문
            //    공통   가상계좌입금내역 Insert
            //    DV  => 대금구분코드 조회
            //           에스크로입금상세 Insert
            //           에스크로상세 Update
            //           에스크로가상계좌할당상세 Update
            //           가상계좌기본 (TEC_VR_ACCT_M) 할당여부 Update
            //           에스크로기본 Update
            //    EV  => 수수료입금상세 Update
            //           에스크로가상계좌할당상세 Update            
            //           가상계좌기본 (TEC_VR_ACCT_M) 할당여부 Update
            //           에스크로기본 Update
            /*===================================================================================*/
            fbUtils.getTgDsc(tg_cd, fbTgBean);

            if ("DV".equals(svrGbn)) {

                log.info("=====>> setRcvIdvdptTg VA03 DV VR_ACCT_NO [" + (String) paramMap.get("VR_ACCT_NO") + "]");

                selectMap = fbTgInfoMapper.selectRomKeyInfo(paramMap);

                if (selectMap == null) {
                    fbTgBean.setData("RES_CD", "0711");   // 수취인 계좌 없음
                } else {

                    log.debug("=====>> selectMap ESCR_M_KEY [" + selectMap.get("ESCR_M_KEY") + "]");
                    log.debug("=====>> selectMap CHRG_DSC   [" + selectMap.get("CHRG_DSC") + "]");

                    escr_m_key = StringUtil.mapToStringL(selectMap, "ESCR_M_KEY");

                    log.info("=====>> setRcvIdvdptTg VA03 escr_m_key [" + escr_m_key + "]");

                    paramMap.put("ESCR_M_KEY", escr_m_key + "");
                    paramMap.put("escr_m_key", escr_m_key + "");
                    paramMap.put("CHRG_DSC", selectMap.get("CHRG_DSC").toString());

                    chkVal = fbTgInfoMapper.insertEscrRomD(paramMap);
                    chkVal = fbTgInfoMapper.updateEscrDDV(paramMap);
                    chkVal = fbTgInfoMapper.updateVrAcctAsgnDDV(paramMap);
                    chkVal = fbTgInfoMapper.updateVrAcctMDV(paramMap);

                    /*====================================================================*/
                    // 알림톡/PUSH 전송
                    /*====================================================================*/
                    HashMap<String, Object> sendInfo = fbTgInfoMapper.selectRomInfo(paramMap);

                    String allRomDsc = StringUtil.mapToString(sendInfo, "ALL_ROM_DSC");
                    String chrgDscNm = StringUtil.mapToString(sendInfo, "CHRG_DSCNM");
                    String romBnkNm = StringUtil.mapToString(sendInfo, "ROM_BNK_NM");
                    String romAcctNo = StringUtil.mapToString(sendInfo, "ROM_ACCT_NO");
                    String membNm = StringUtil.mapToString(sendInfo, "MEMB_NM");
                    String trAmt = NumberUtil.toDecimalFormat(StringUtil.mapToStringL(paramMap, "TR_AMT"));

                    if ("1".equals(allRomDsc)) {
                        Object[] msgPatten = {chrgDscNm, romBnkNm, romAcctNo, membNm, trAmt};
                        escrSendMsg.sendMsg("", 34005, msgPatten, null);
                    } else {
                        Object[] msgPatten = {chrgDscNm};
                        escrSendMsg.sendMsg("", 34004, msgPatten, null);
                    }
                }
            }

            if ("EV".equals(svrGbn)) {

                escr_m_key = (Long) fbTgInfoMapper.selectFeeKeyInfo(paramMap);

                if (escr_m_key == 0) {
                    fbTgBean.setData("RES_CD", "0711");   // 수취인 계좌 없음
                } else {
                    paramMap.put("ESCR_M_KEY", escr_m_key + "");
                    paramMap.put("escr_m_key", escr_m_key + "");
                    paramMap.put("TR_AMT", fbTgBean.getData("TR_AMT", "N"));

                    log.info("=====>> escr_m_key [" + escr_m_key + "]   TR_AMT [" + fbTgBean.getData("TR_AMT", "N") + "]");

                    chkVal = (int) fbTgInfoMapper.selectValidFeeAmt(paramMap);   // 수수료 금액과 전문 거래금액 비교

                    if (chkVal == 0) {
                        fbTgBean.setData("RES_CD", "5915");   // 금액부분 오류    
                    } else {
                        chkVal = fbTgInfoMapper.updateFeeRomDEV(paramMap);
                        chkVal = fbTgInfoMapper.updateVrAcctMEV(paramMap);
                        chkVal = fbTgInfoMapper.updateEscrMEV(paramMap);
                        chkVal = ifTgInfoMapper.insertBackupEscrM(paramMap);
                    }
                }
            }

            if (chkVal > 0) {
                fbTgBean.setData("RES_CD", "0000");
            }
            fbTgBean.setData("TG_DSC", "0210");
            reSndYn = true;
        } else if ("WA02".equals(tg_cd) || "WA03".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA02 ] 당행이체 [ WA03 ] 타행이체
            /*===================================================================================*/
            escr_m_key = getOriLogMKey(tg_cd, fbTgBean);
            log.info("=====>> setRcvIdvdptTg WA02 / WA03 tg_log_m_key [" + tg_log_m_key + "]   escr_m_key [" + escr_m_key + "]");


            if ("EW".equals(svrGbn)) {   // 에스크로 수수료 환급  & FA 권원보험료 납입

                paramMap.put("tg_log_m_key", tg_log_m_key);
                paramMap.put("escr_m_key", escr_m_key);

                chkVal = fbTgInfoMapper.updateIsrnPrmmRom(paramMap);

                log.info("=====>> updateIsrnPrmmRom chkVal [" + chkVal + "]");

                chkVal = ifTgInfoMapper.insertBackupEscrM(paramMap);

                log.info("=====>> insertBackupEscrM chkVal [" + chkVal + "]");

                fbTgBean.setData("RES_CD", "0000");
            }

//            if ("DW".equals(svrGbn)) {   // DB에 보험료 입금
//                
//                paramMap.put("tg_log_m_key", tg_log_m_key);
//                paramMap.put("escr_m_key"  , escr_m_key  );
//                
//                chkVal = fbTgInfoMapper.updateIsrnPrmmRom(paramMap);
//    
//                log.info("=====>> updateIsrnPrmmRom chkVal [" + chkVal + "]");
//                
//                chkVal = ifTgInfoMapper.insertBackupEscrM(paramMap);
//                
//                log.info("=====>> insertBackupEscrM chkVal [" + chkVal + "]");
//                
//                fbTgBean.setData("RES_CD", "0000");                
//            }

        } else if (
                "WA01".equals(tg_cd) ||
                        "WA04".equals(tg_cd) || "WA05".equals(tg_cd) ||
                        "WA06".equals(tg_cd) || "WA09".equals(tg_cd) ||
                        "WA10".equals(tg_cd)
        ) {
            /*===================================================================================*/
            // [ WA01 ] LGU+ 펌뱅킹 원화거래 개시전문    
            // [ WA04 ] LGU+ 펌뱅킹 원화거래 이체처리결과조회
            // [ WA05 ] LGU+ 펌뱅킹 원화거래 잔액조회    
            // [ WA06 ] LGU+ 펌뱅킹 원화거래 이체집계    
            // [ WA09 ] LGU+ 펌뱅킹 원화거래 예금거래명세결번요구
            // [ WA10 ] LGU+ 펌뱅킹 원화거래 타행불능결번요구  
            /*===================================================================================*/
            // 별도 개별부 셋팅 없음.  ( Send 전문에 대한 Receive 전문 )

        } else if ("WA07".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA07 ] LGU+ 펌뱅킹 원화거래 예금거래명세통지
            /*===================================================================================*/
            fbUtils.getTgDsc(tg_cd, fbTgBean);

            fbTgBean.setData("RES_CD", "0000");

            reSndYn = true;

        } else if ("WA08".equals(tg_cd)) {
            /*===================================================================================*/
            // [ WA08 ] LGU+ 펌뱅킹 원화거래 타행불능통지
            /*===================================================================================*/
            fbUtils.getTgDsc(tg_cd, fbTgBean);

            fbTgBean.setData("RES_CD", "0000");

            reSndYn = true;
        }

        /*=========================================================================================================*/
        // 응답 전문 전송 (ESCROW => LGU+ FB)
        /*=========================================================================================================*/
        if (reSndYn) {
            fbSocket.sendDataByte(svrGbn, fbTgBean.toAllByte());
        }

        return escr_m_key;
    }

    public void insertTgLog(String tg_cd, long escr_m_key, String tg_kcd, FirmBankingTgBean fbTgBean) {

        /*===================================================================================*/
        // 펌뱅킹 전문 전문번호(TG_NO) 가 6자리라서.. 일자별 RowNumber 로 Change             */
        /*===================================================================================*/
        try {
            HashMap<String, Object> paramMap = new HashMap<String, Object>();

            log.debug("=====>> insertTgLog tg_cd [" + tg_cd + "]  tg_kcd [" + tg_kcd + "]  escr_m_key [" + escr_m_key + "]");

            paramMap.put("tg_cd", tg_cd);  // S : Send, R : Receive
            paramMap.put("tg_kcd", tg_kcd);  // S : Send, R : Receive
            paramMap.put("escr_m_key", escr_m_key);

            tg_log_m_key = extnLkSService.saveTg(paramMap, fbTgBean.getDataMap());

            paramMap.put("tg_log_m_key", tg_log_m_key);

            chg_tg_log_m_key = ifTgInfoMapper.getChgLogMKey(paramMap);

            log.info("=====>> insertTgLog tg_log_m_key [" + tg_log_m_key + "] ==>> chg_tg_log_m_key : " + chg_tg_log_m_key);

            fbTgBean.setData("TG_NO", chg_tg_log_m_key + "");   // 11. 전문번호

        } catch (Exception Ex) {
            log.error("=====>> insertTgLog ERR_MSG [" + Ex.getMessage() + "]");
            Ex.printStackTrace();
        }
        /*===================================================================================*/
    }

    public void updateTgLog(String tg_cd, long escr_m_key, String tg_kcd, FirmBankingTgBean fbTgBean) {

        log.info("=====>> updateTgLog tg_cd [" + tg_cd + "]   escr_m_key [" + escr_m_key + "]   tg_kcd [" + tg_kcd + "]");

        String res_cd = "";
        String res_msg = "";
        /*===================================================================================*/
        // 펌뱅킹 전문 전문번호(TG_NO) 가 6자리라서.. 일자별 RowNumber 로 Change             */
        /*===================================================================================*/
        try {
            HashMap<String, Object> paramMap = new HashMap<String, Object>();

            chg_tg_log_m_key = Long.parseLong(fbTgBean.getData("TG_NO", "N"));

            paramMap.put("tg_kcd", tg_kcd);
            paramMap.put("tg_cd", tg_cd);
            paramMap.put("chg_tg_log_m_key", chg_tg_log_m_key);
            paramMap.put("escr_m_key", escr_m_key);

            res_cd = fbTgBean.getData("RES_CD", "N");
            fbUtils.getErrMsg(tg_cd.substring(0, 2), res_cd, paramMap);  // paramMap => RES_CD, RES_MSG 셋팅

            paramMap.put("res_cd", res_cd);
            paramMap.put("res_msg", (String) paramMap.get("RES_MSG"));

            getOriLogMKey(tg_cd, fbTgBean);

            log.info("=====>> updateTgLog chg_tg_log_m_key [" + chg_tg_log_m_key + "] ==>> tg_log_m_key [" + tg_log_m_key + "]");

            paramMap.put("tg_log_m_key", tg_log_m_key);

            tg_log_m_key = extnLkSService.saveTg(paramMap, fbTgBean.getDataMap());

        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        /*===================================================================================*/
    }

    public long getOriLogMKey(String tg_cd, FirmBankingTgBean fbTgBean) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        chg_tg_log_m_key = Long.parseLong(fbTgBean.getData("TG_NO", "N"));

        paramMap.put("tg_cd", tg_cd);
        paramMap.put("chg_tg_log_m_key", chg_tg_log_m_key);

        HashMap<String, Object> resMap = ifTgInfoMapper.getOriLogMKey(paramMap);
        long escr_m_key = 0;

        tg_log_m_key = (Long) resMap.get("TG_LOG_M_KEY");
        escr_m_key = (Long) resMap.get("ESCR_M_KEY");

        log.info("=====>> getOriLogMKey tg_log_m_key [" + tg_log_m_key + "]   escr_m_key [" + escr_m_key + "]");

        return escr_m_key;
    }

    public HashMap<String, Object> directTrn(HashMap<String, Object> paramMap) {

        HashMap<String, Object> resMap = new HashMap<String, Object>();

        resMap.put("RES_CD", "0000");
        resMap.put("RES_MSG", "정상");

        try {


            String svrGbn = getMapData(paramMap, "SVR_GBN");
            String escrMKey = getMapData(paramMap, "escrMKey");
            String bnk_cd = getMapData(paramMap, "BNK_CD");
            String acct_no = getMapData(paramMap, "ACCT_NO");
            String name = getMapData(paramMap, "NAME");
            String amt = getMapData(paramMap, "AMT");

            log.debug("=====>> svrGbn[" + svrGbn + "]  escrMKey [" + escrMKey + "]  bnk_cd [" + bnk_cd + "]   acct_no [" + acct_no + "]   name [" + name + "]   amt [" + amt + "]");

            if ("".equals(StringUtil.nvl(escrMKey))) {
                resMap.put("RES_CD", "1002");
                resMap.put("RES_MSG", "파라미터 오류 !!!  ESCR_M_KEY 누락");
                return resMap;
            }

            if ("".equals(svrGbn) || "".equals(bnk_cd) || "".equals(acct_no) || "".equals(name) || "".equals(amt)) {
                resMap.put("RES_CD", "1001");
                resMap.put("RES_MSG", "파라미터 오류 !!!");
                return resMap;
            }

            FirmBankingTrnData trnData = new FirmBankingTrnData();

            trnData.setTr_acct_bnk_cd(bnk_cd);
            trnData.setTr_acct_no(acct_no);
            trnData.setTrmn_nm(name);
            trnData.setTrn_amt(Long.parseLong(amt));

            String tg_dsc = "";
            String wk_dsc = "";
            String tg_cd = "";

            if ("020".equals(bnk_cd)) {
                tg_dsc = "0100";
                wk_dsc = "100";
            } else {
                tg_dsc = "0101";
                wk_dsc = "100";
            }
            tg_cd = fbUtils.getTgCd(svrGbn, tg_dsc, wk_dsc);

            paramMap.put("tg_cd", tg_cd);

            List<HashMap<String, Object>> listTgD = ifTgInfoMapper.selectFBTgD(paramMap);

            FirmBankingTgBean fbTgBean = new FirmBankingTgBean(listTgD);

            setCmnptArea(svrGbn, tg_dsc, wk_dsc, fbTgBean);  // 공통부
            setSndIdvdptTg(svrGbn, tg_cd, fbTgBean, trnData);  // 개별부
            insertTgLog(tg_cd, Long.parseLong(escrMKey), "S", fbTgBean);                   // Send 전문 Log 저장
            log.debug("======================================================================");
            log.debug("======================================================================");
            log.debug("======================================================================");
            log.debug("=====>>> Batch Test Run !!!");
            log.debug("======================================================================");
            log.debug("======================================================================");
            log.debug("======================================================================");
            fbTgBean.toString("Y");  // IF 전문 log.debug 출력

            String sendResult = fbSocket.sendDataByte(svrGbn, fbTgBean.toAllByte());

            if ("success".equals(sendResult)) {
                resMap = fbUtils.checkResponse(tg_log_m_key);
            } else {
                resMap.put("RES_CD", "7001");
                resMap.put("RES_MSG", "Socket 전송 실패");
            }
        } catch (Exception Ex) {
            /*===================================================================================*/
            // Exception 처리 Logic 추가
            /*===================================================================================*/
            resMap.put("RES_CD", "9999");
            resMap.put("RES_MSG", Ex.getMessage());
        }

        return resMap;
    }

    public String getMapData(HashMap<String, Object> paramMap, String keyName) {

        String rsltMsg = "";
        try {
            rsltMsg = (String) paramMap.get(keyName);
        } catch (Exception Ex) {
            rsltMsg = "";
        }
        return rsltMsg;
    }

    public void getRcvTgInfo(HashMap<String, Object> paramMap, String tg_itm_cd) {


        paramMap.put("tg_log_m_key", tg_log_m_key);
        paramMap.put("tg_itm_cd", tg_itm_cd);
        HashMap<String, Object> selectMap = ifTgInfoMapper.selectRcvTgLogInfo(paramMap);

        paramMap.put("RSLT_NAME", (String) selectMap.get("TG_ITM_NM"));
        paramMap.put("RSLT_DATA", (String) selectMap.get("TG_CNTS"));
    }
}

