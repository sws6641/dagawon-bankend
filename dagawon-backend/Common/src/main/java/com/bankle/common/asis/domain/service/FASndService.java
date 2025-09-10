package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.mapper.IfFaCmnMapper;
import com.bankle.common.asis.domain.mapper.IfFaSndMapper;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.asis.domain.service.extnLk.ExtnLkFAService;
import com.bankle.common.asis.domain.service.extnLk.LguFirmBankingService;
import com.bankle.common.asis.utils.EscrSendMsgUtils;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 김배성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FASndService {

    private final IfTgInfoMapper ifTgInfoMapper;
    private final IfFaCmnMapper ifFaCmnMapper;
    private final IfFaSndMapper ifFaSndMapper;
    private final ExtnLkFAService extnLkFaService;
    private final LguFirmBankingService lfbService;
    private final EscrSendMsgUtils escrSendMsg;

    private String rslt_cd = "";      // 결과 코드
    private String rslt_msg = "";

    /*===========================================================================================*/
    // Func : faSscptAskReg  [ 청약의뢰등록 ] Batch 처리 [FA01]
    /*===========================================================================================*/
    public void faSscptAskReg() {

        // 청약등록대상 목록 조회
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> rsltMap = new HashMap<String, Object>();

        ArrayList<HashMap<String, Object>> list = ifFaCmnMapper.selectSscptRegTrgt();

        int i = 0;
        int listSize = 0;
        int dbCnt = 0;

        String tg_cd = "FA01";  // 청약의뢰등록 전문코드
        long escr_m_key = 0;      // 에스크로 기본 키

        listSize = list.size();

        log.info("===>> 청약의뢰요청 건수 : " + listSize);

        if (listSize > 0) {

            for (i = 0; i < listSize; i++) {

                dataMap = (HashMap<String, Object>) list.get(i);    // 청약등록대상 선택
                escr_m_key = (Long) dataMap.get("ESCR_M_KEY");         // 청약등록대상 에스크로기본키

                log.info("===>> 청약의뢰요청 ESCR_M_KEY : " + escr_m_key);

                /*===================================================================================*/
                // 청약의뢰 전송 정보 셋팅
                /*===================================================================================*/
                paramMap.put("tg_cd", tg_cd);
                paramMap.put("tg_cd_s", tg_cd);
                paramMap.put("tg_cd_r", tg_cd.substring(0, 3) + "2");
                paramMap.put("escr_m_key", escr_m_key);
                paramMap.put("ESCR_M_KEY", escr_m_key);

                /*===================================================================================*/
                // 청약의뢰등록 정보 전송
                /*===================================================================================*/
                rsltMap = extnLkFaService.callFA(paramMap);
                rslt_cd = (String) rsltMap.get("rslt_cd");
                rslt_msg = (String) rsltMap.get("rslt_msg");

                log.info("청약의뢰등록   rslt_cd [" + rslt_cd + "]   rslt_msg [" + rslt_msg + "]");

                if (!"0000".equals(rslt_cd)) {
                    continue;
                }  // FA 청약의뢰 오류시 Skip And Continue

                escrSendMsg.sendMsg(escr_m_key + "", 33001, null, null);
                /*===================================================================================*/
            }  // for End !!!
        }  // if listSize > 0 End !!!
    }

    /*===========================================================================================*/
    // Func : faSscptAskModify  [ 청약의뢰수정 ] [FA11]
    /*===========================================================================================*/
    @Transactional
    public HashMap<String, Object> faSscptAskModify(long escr_m_key) throws Exception {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> rsltMap = new HashMap<String, Object>();

        String tg_cd = "FA11";  // 청약의뢰수정 전문코드
        int dbCnt = 0;

        /*===================================================================================*/
        // 청약의뢰 전송 정보 셋팅
        /*===================================================================================*/
        paramMap.put("tg_cd", tg_cd);
        paramMap.put("tg_cd_s", tg_cd);
        paramMap.put("tg_cd_r", tg_cd.substring(0, 3) + "2");
        paramMap.put("escr_m_key", escr_m_key);

        /*===================================================================================*/
        // 청약의뢰수정 정보 전송
        /*===================================================================================*/
        rsltMap = extnLkFaService.callFA(paramMap);

        rslt_cd = (String) rsltMap.get("rslt_cd");
        rslt_msg = (String) rsltMap.get("rslt_msg");

        log.info("청약의뢰수정   rslt_cd [" + rslt_cd + "]   rslt_msg [" + rslt_msg + "]");

        if (!"0000".equals(rslt_cd)) {
            return rsltMap;
        }

        /*===================================================================================*/
        // FA 보험 정보 TET_ESCR_M UPDATE
        /*===================================================================================*/
        paramMap.put("isrn_scrt_no", (String) rsltMap.get("PolicyNum"));  // 보험증권번호
        paramMap.put("isrn_prmm", (String) rsltMap.get("Premium"));  // 보험료
        paramMap.put("isrn_rom_sqn", (String) rsltMap.get("DepositNum"));  // 보험입금일련번호

        // FA 청약의뢰 정보 Update
        dbCnt = ifFaSndMapper.updateEscrSscpt(paramMap);  // 전송전 Validation 으로 체크되어 ( dbCnt = 0 ) 체크하지 않음.
        // TET_ESCR_M 변경내역 TET_ESCR_H 에 복제
        dbCnt = ifTgInfoMapper.insertBackupEscrM(paramMap);

        return rsltMap;
    }

    /*===========================================================================================*/
    // Func : faRschRsltReq  [ Daily조사결과요청 ] [FA41]
    /*===========================================================================================*/
//    @Transactional
//    public HashMap<String, Object> faRschRsltReq(String escr_m_key) throws Exception {
//        
//        HashMap<String, Object> paramMap = new HashMap<String, Object>();
//        HashMap<String, Object> rsltMap  = new HashMap<String, Object>();
//        
//        String tg_cd      = "FA41";  // Daily조사결과요청 전문코드
//        String rslt_cd    = "";      // 결과 코드
//        int    dbCnt      = 0;
//                
//        /*===================================================================================*/
//        // 청약의뢰 전송 정보 셋팅
//        /*===================================================================================*/
//        paramMap.put("tg_cd"     , tg_cd                     );
//        paramMap.put("tg_cd_s"   , tg_cd                     );
//        paramMap.put("tg_cd_r"   , tg_cd.substring(0,3) + "2");
//        paramMap.put("escr_m_key", escr_m_key                );
//        
//        rsltMap  = extnLkFaService.callFA(paramMap);    // 청약의뢰등록 Call
//        
//        rslt_cd  = (String)rsltMap.get("rslt_cd" );
//                
//        if (!"0000".equals(rslt_cd)) { return rsltMap; }  
//        
//        /*===================================================================================*/
//        // FA Daily 조사결과정보 입력 TEI_FA_RSCH_RSLT_I
//        /*===================================================================================*/
//        paramMap.put("rgst_chg_rsch_epsd", (String)rsltMap.get("CheckCnt"       ));   // 등기부 변동 조사 회차
//        paramMap.put("rgst_chg_dsc"      , (String)rsltMap.get("CheckResult"    ));   // 등기부 변동 구분코드
//        paramMap.put("rgst_chg_brkdn_rno", (String)rsltMap.get("CheckChangedCnt"));   // 등기부 변동 내역 개수
//        paramMap.put("rgst_chg_cnfm_cnts", (String)rsltMap.get("ChangedReason"  ));   // 등기부 변동 확인 내용
//        paramMap.put("rgst_chg_cnfm_dt"  , (String)rsltMap.get("ChangedDate"    ));   // 등기부 변동 확인 일자
//        paramMap.put("rmk_fct"           , (String)rsltMap.get("Remark"         ));   // 비고 사항
//        paramMap.put("strn_eane_cd"      , (String)rsltMap.get("AbnormalType"   ));   // 이상 유무 코드
//        paramMap.put("strn_eane_rsn"     , (String)rsltMap.get("AbnormalReason" ));   // 이상 유무 사유
//        
//        // FA 청약의뢰 정보 Update
//        dbCnt = ifFaSndMapper.insertFaRschRsltI  (paramMap);  // 전송전 Validation 으로 체크되어 ( dbCnt = 0 ) 체크하지 않음.
//        
//        return rsltMap;
//    }      

    /*===========================================================================================*/
    // Func : faRomPmntSnd  [ 에스크로 입출금 내역 전송 ] Batch 처리 [FA51]
    /*===========================================================================================*/
    @Transactional
    public void faRomPmntSnd() throws Exception {

        // 청약등록대상 목록 조회
        ArrayList<HashMap<String, Object>> list = ifFaCmnMapper.selectRomPmntTrgt();

        log.info("===>> faRomPmntSnd 대상 Count : " + list.size());

        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> rsltMap = new HashMap<String, Object>();

        int i = 0;
        int listSize = 0;
        int dbCnt = 0;

        String tg_cd = "FA51";  // 청약의뢰등록 전문코드
        long escr_m_key = 0;       // 에스크로 기본 키
        long escr_d_key = 0;       // 에스크로 상세 키
        long escr_trgt_amt = 0;       // 에스크로 대상 금액
        long escr_rom_amt = 0;       // 에스크로 입급 금액
        long escr_pmnt_amt = 0;       // 에스크로 지급 금액
        long pmnt_amt = 0;       // 지급금액
        String prdt_tpc = "";      // 상품유형코드
        String chrg_dsc = "";      // 대금구분코드
        String chrg_dscnm = "";      // 대금구분명
        String rom_yn = "";      // 입금내역 전송 대상 여부
        String pmnt_yn = "";      // 출금내역 전송 대상 여부
        String rom_dtm = "";      // 입금일시
        String pmnt_dtm = "";      // 송금일시
        String prty_nm = "";      // 지급대상명
        String pmnt_bnk_nm = "";      // 지급은행명
        String pmnt_acct_no = "";      // 지급계좌번호

        listSize = list.size();

        if (listSize > 0) {

            for (i = 0; i < listSize; i++) {

                dataMap = (HashMap<String, Object>) list.get(i);  // 청약등록대상 선택

                escr_m_key = StringUtil.mapToStringL(dataMap, "ESCR_M_KEY");   // 청약등록대상 에스크로기본키
                escr_d_key = StringUtil.mapToStringL(dataMap, "ESCR_D_KEY");   // 청약등록대상 에스크로상세키
                escr_trgt_amt = StringUtil.mapToStringL(dataMap, "ESCR_TRGT_AMT");   // 에스크로 대상 금액
                escr_rom_amt = StringUtil.mapToStringL(dataMap, "ESCR_ROM_AMT");   // 에스크로 입급 금액
                escr_pmnt_amt = StringUtil.mapToStringL(dataMap, "ESCR_PMNT_AMT");   // 에스크로 지급 금액
                prdt_tpc = (String) dataMap.get("PRDT_TPC");     // 상품 유형코드
                chrg_dsc = (String) dataMap.get("CHRG_DSC");     // 대금구분코드
                chrg_dscnm = (String) dataMap.get("CHRG_DSCNM");     // 대금구분코드
                rom_yn = (String) dataMap.get("ROM_TRAN_OBJ_YN");     // 입금내역 전송 대상 여부
                pmnt_yn = (String) dataMap.get("PMNT_TRAN_OBJ_YN");     // 송금내역 전송 대상 여부
                rom_dtm = (String) dataMap.get("ROM_DTM");     // 임금 일시
                pmnt_dtm = (String) dataMap.get("PMNT_DTM");     // 송금 일시
                prty_nm = (String) dataMap.get("PRTY_NM");     // 송금 일시
                pmnt_bnk_nm = (String) dataMap.get("PMNT_BNK_NM");     // 지급은행명
                pmnt_acct_no = (String) dataMap.get("PMNT_ACCT_NO");     // 지급계좌번호

                /*===================================================================================*/
                // 입출금 내역 전송 정보 셋팅
                /*===================================================================================*/
                paramMap.put("tg_cd", tg_cd);
                paramMap.put("tg_cd_s", tg_cd);
                paramMap.put("tg_cd_r", tg_cd.substring(0, 3) + "2");
                paramMap.put("escr_m_key", escr_m_key);
                paramMap.put("escr_d_key", escr_d_key);
                paramMap.put("chrg_dsc", chrg_dsc);
                paramMap.put("rom_dtm", rom_dtm);
                paramMap.put("pmnt_dtm", pmnt_dtm);
                paramMap.put("rom_yn", rom_yn);
                paramMap.put("pmnt_yn", pmnt_yn);

                if ("2".equals(prdt_tpc)) {  // 에스크로 보험형

                    if ("Y".equals(rom_yn)) {
                        /*===================================================================================*/
                        // 입출금 내역 등록 정보 전송 (입급)
                        /*===================================================================================*/
                        rsltMap = extnLkFaService.callFA(paramMap);
                        rslt_cd = (String) rsltMap.get("rslt_cd");
                        rslt_msg = (String) rsltMap.get("rslt_msg");

                        log.info("입출금내역등록정보전송(입금)   rslt_cd [" + rslt_cd + "]   rslt_msg [" + rslt_msg + "]");

                        // 테스트를 위해 전송 결과와 상관없이 DB 처리부분 적용되게 수정
                        // 향후 에러 발생 시 어떻게 처리해야 할 지 결정 필요
                        // if (!"0000".equals(rslt_cd)) { continue; }  // FA 청약의뢰 오류시 Skip And Continue
                    }

                    if ("Y".equals(pmnt_yn)) {
                        /*===================================================================================*/
                        // 입출금 내역 등록 정보 전송 (지급)
                        /*===================================================================================*/
                        rsltMap = extnLkFaService.callFA(paramMap);
                        rslt_cd = (String) rsltMap.get("rslt_cd");
                        rslt_msg = (String) rsltMap.get("rslt_msg");

                        log.info("입출금내역등록정보전송(지급)   rslt_cd [" + rslt_cd + "]   rslt_msg [" + rslt_msg + "]");

                        // 테스트를 위해 전송 결과와 상관없이 DB 처리부분 적용되게 수정
                        // 향후 에러 발생 시 어떻게 처리해야 할 지 결정 필요                        
                        // if (!"0000".equals(rslt_cd)) { continue; }  // FA 청약의뢰 오류시 Skip And Continue                    
                    }
                }

                /*===================================================================================*/
                // FA 보험 정보 TET_ESCR_M UPDATE
                /*===================================================================================*/

                // FA Escr 입금출금 전송 정보 Update
                dbCnt = ifFaSndMapper.updateEscrRomPmnt(paramMap);  // 전송전 Validation 으로 체크되어 ( dbCnt = 0 ) 체크하지 않음.

                if ("Y".equals(rom_yn)) {
                    dbCnt = ifFaSndMapper.updateRomEscrMDV(paramMap);
                    dbCnt = ifTgInfoMapper.insertBackupEscrM(paramMap);
                }

                if ("Y".equals(pmnt_yn)) {
                    dbCnt = ifFaSndMapper.updatePmntEscrMDV(paramMap);
                    dbCnt = ifTgInfoMapper.insertBackupEscrM(paramMap);
                }

            }  // for End !!!
        }  // if listSize > 0 End !!!        
    }

    /*===========================================================================================*/
    // Func : faEscrIsrnRschCnfm  [ 에스크로 보험조사서확인 ] [FA71]
    /*===========================================================================================*/
    @Transactional
    public HashMap<String, Object> faEscrIsrnRschCnfm(String escr_m_key) throws Exception {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> rsltMap = new HashMap<String, Object>();

        String tg_cd = "FA71";  // FA 연계 - 증권확인 송신 전문 [조사서] (파일스트림  Detail 테이블 없음)

        /*===================================================================================*/
        // 보험조사서확인 전송 정보 셋팅
        /*===================================================================================*/
        paramMap.put("tg_cd", tg_cd);
        paramMap.put("tg_cd_s", tg_cd);
        paramMap.put("tg_cd_r", tg_cd.substring(0, 3) + "2");
        paramMap.put("escr_m_key", escr_m_key);

        rsltMap = extnLkFaService.callFA(paramMap);    // 보험조사서확인 등록 Call
        rslt_cd = (String) rsltMap.get("rslt_cd");
        rslt_msg = (String) rsltMap.get("rslt_msg");

        log.info("에스크로보험조사서확인   rslt_cd [" + rslt_cd + "]   rslt_msg [" + rslt_msg + "]");

//        if (!"0000".equals(rslt_cd)) { return rsltMap; }  

        return rsltMap;
    }

    /*===========================================================================================*/
    // Func : faEscrIsrnScrtCnfm  [ 에스크로 보험증권확인 ] [FA81]
    /*===========================================================================================*/
    @Transactional
    public HashMap<String, Object> faEscrIsrnScrtCnfm(long escr_m_key) throws Exception {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> rsltMap = new HashMap<String, Object>();

        String tg_cd = "FA81";  // FA 연계 - 증권확인 송신 전문 [보험증권] (파일스트림  Detail 테이블 없음)

        /*===================================================================================*/
        // 보험증권확인 전송 정보 셋팅
        /*===================================================================================*/
        paramMap.put("tg_cd", tg_cd);
        paramMap.put("tg_cd_s", tg_cd);
        paramMap.put("tg_cd_r", tg_cd.substring(0, 3) + "2");
        paramMap.put("escr_m_key", escr_m_key);

        rsltMap = extnLkFaService.callFA(paramMap);    // 보험증권확인 Call
        rslt_cd = (String) rsltMap.get("rslt_cd");
        rslt_msg = (String) rsltMap.get("rslt_msg");

        log.info("에스크로보험증권확인   rslt_cd [" + rslt_cd + "]   rslt_msg [" + rslt_msg + "]");

//        if (!"0000".equals(rslt_cd)) { return rsltMap; }  

        return rsltMap;
    }

    /*===========================================================================================*/
    // Func : faEscrSscptCancel  [ 에스크로 청약취소 ] [FA91]
    /*===========================================================================================*/
    @Transactional
//    public HashMap<String, Object> faEscrSscptCancel(long escr_m_key , String cntrt_cncl_rsn_cd, String cntrt_cncl_rsn_cnts) throws Exception {
//    해지사유코드/설명 은  '02' 에스크로해지 만 존재하여 Parameter 에서 제외시킴.
    public HashMap<String, Object> faEscrSscptCancel(long escr_m_key) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> rsltMap = new HashMap<String, Object>();

        String tg_cd = "FA91";  // 청약취소 전문코드
        int dbCnt = 0;

        /*===================================================================================*/
        // 청약취소 전송 정보 셋팅
        /*===================================================================================*/
        paramMap.put("tg_cd", tg_cd);
        paramMap.put("tg_cd_s", tg_cd);
        paramMap.put("tg_cd_r", tg_cd.substring(0, 3) + "2");
        paramMap.put("escr_m_key", escr_m_key);
//        paramMap.put("cntrt_cncl_rsn_cd"  , cntrt_cncl_rsn_cd         );
//        paramMap.put("cntrt_cncl_rsn_cnts", cntrt_cncl_rsn_cnts       );
        paramMap.put("cntrt_cncl_rsn_cd", "02");  // 02. 에스크로 해지
        paramMap.put("cntrt_cncl_rsn_cnts", "에스크로 해지");

        rsltMap = extnLkFaService.callFA(paramMap);    // 청약취소 Call
        rslt_cd = (String) rsltMap.get("rslt_cd");
        rslt_msg = (String) rsltMap.get("rslt_msg");

        log.info("에스크로청약취소   rslt_cd [" + rslt_cd + "]   rslt_msg [" + rslt_msg + "]");

//        if (!"0000".equals(rslt_cd)) { return rsltMap; }  

        return rsltMap;
    }
}