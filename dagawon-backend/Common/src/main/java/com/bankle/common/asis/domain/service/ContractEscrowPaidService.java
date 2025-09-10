package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.mapper.ContractEscrowMapper;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.asis.domain.repositories.ContractEscrowPaidRepository;
import com.bankle.common.asis.domain.service.extnLk.LguFirmBankingService;
import com.bankle.common.asis.utils.EscrSendMsgUtils;
import com.bankle.common.asis.utils.FirmBankingUtils;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractEscrowPaidService {

    private final ContractEscrowPaidRepository contractEscrowPaidRepository;
    private final ContractEscrowService contractEscrowService;
//    private final FaResearchResultService faResearchResultService;
    private final ContractEscrowMapper escrMapper;
    private final LguFirmBankingService LFBService;
    private final FirmBankingUtils fbUtils;
    private final IfTgInfoMapper ifTgInfoMapper;
    private final EscrSendMsgUtils escrSendMsg;

    /**
     * 지급내역 저장
     *
     * @param body
     */
    public HashMap<String, Object> approval(HashMap<String, String> paramMap) throws Exception {

        String escr_m_key = "";
        String prdt_dsc = "";
        String chrg_dsc = "";
        String chkValue = "";
        String res_cd = "";
        String res_msg = "";

        HashMap<String, Object> selectMap = escrMapper.checkEscrPmnt(paramMap);

        escr_m_key = StringUtil.mapToString(selectMap, "ESCR_M_KEY");
        prdt_dsc = StringUtil.mapToString(selectMap, "PRDT_DSC");
        chrg_dsc = StringUtil.mapToString(selectMap, "CHRG_DSC");
        chkValue = StringUtil.mapToString(selectMap, "CHECK_VALUE");
        selectMap.put("escr_m_key", escr_m_key);

        log.debug("=====>> Service approval  escr_m_key [" + escr_m_key + "]   prdt_dsc [" + prdt_dsc + "]   chrg_dsc [" + chrg_dsc + "]   chkValue [" + chkValue + "]");

        if (!"SUCCESS".equals(chkValue)) {
            throw new Exception(chkValue);
        }

        String svrGbn = "7".equals(chrg_dsc) ? "EW" : "DW";  // 7:수수로 환급 (에스크로 모계좌 지급)

        if (!fbUtils.checkBankTgOpen(svrGbn)) {
            throw new Exception("은행 거래 개시 오류 !!");
        }
        /*=====================================================================================*/
        // 에스크로 출금 처리 
        /*=====================================================================================*/
        int dbCnt = escrMapper.insertEscrPmntD(selectMap);
        dbCnt = escrMapper.updateEscrPmnt(selectMap);
        dbCnt = ifTgInfoMapper.insertBackupEscrM(selectMap);

        HashMap<String, Object> resMap = new HashMap<String, Object>();
        resMap = LFBService.sendEscrTrn(Long.parseLong(escr_m_key), chrg_dsc);

        /*===================================================================================*/
        // 알림톡/PUST 전송
        Object[] msgPatten = {
                StringUtil.mapToString(selectMap, "CHRG_DSCNM")
                , StringUtil.mapToString(selectMap, "PRTY_NM")
                , StringUtil.mapToString(selectMap, "PMNT_BNK_NM")
                , StringUtil.mapToString(selectMap, "PMNT_ACCT_NO")
                , StringUtil.mapToString(selectMap, "ESCR_TRGT_AMT")
        };
        escrSendMsg.sendMsg(escr_m_key, 10001, msgPatten, null);
        /*===================================================================================*/
        res_cd = (String) resMap.get("RES_CD");
        res_msg = (String) resMap.get("RES_MSG");
        return resMap;
    }

//    @Transactional(rollbackFor= {Exception.class})
//    public boolean savePmntInfo(HashMap<String, Object> paramMap) {
//        
//        boolean rtnValue = true;
//        int     dbCnt    = 0;
//        /*=====================================================================================*/
//        // 에스크로 출금 처리 
//        /*=====================================================================================*/
//        dbCnt = escrMapper.insertEscrPmntD(paramMap);
//        
//        if (dbCnt <= 0 ) rtnValue = false;
//        
//        dbCnt = escrMapper.updateEscrPmnt (paramMap);
//        
//        if (dbCnt <= 0 ) rtnValue = false;
//            
//        return rtnValue;
//    }

//    /**
//     * 해지 반환금액 정보 저장
//     * @param map
//     * @return
//     */
//    @Transactional
//    public void savePmntInfoForCancel(HashMap<String, String> map){
//        saveRefundAmoutInfo(map, "8");
//        saveRefundFeeAmountInfo(map, "9");
//    }
//
//    /**
//     * 에스크로대금 반환
//     * @param map
//     * @param chrgDsc
//     */
//    private void saveRefundAmoutInfo(HashMap<String, String> map, String chrgDsc){
//        contractEscrowPaidRepository.save(ContractEscrowPmnt.builder()
//                .chrgDsc(chrgDsc)
//                .pmntBnkCd(map.get("pmntBnkCd"))
//                .pmntAcctNo(map.get("pmntAcctNo"))
//                .pmntDt(map.get("pmntDt"))
//                .pmntAmt(Long.valueOf(map.get("refundAmt")))
//                .escrMKey(Long.valueOf(map.get("escrMKey")))
//                .build());
//    }
//
//    /**
//     * 에스크로 수수료금액 반환
//     * @param map
//     * @param chrgDsc
//     */
//    private void saveRefundFeeAmountInfo(HashMap<String, String> map, String chrgDsc){
//        contractEscrowPaidRepository.save(ContractEscrowPmnt.builder()
//                .chrgDsc(chrgDsc)
//                .pmntBnkCd(map.get("pmntBnkCd"))
//                .pmntAcctNo(map.get("pmntAcctNo"))
//                .pmntDt(map.get("pmntDt"))
//                .pmntAmt(Long.valueOf(map.get("refundFeeAmt")))
//                .escrMKey(Long.valueOf(map.get("escrMKey")))
//                .build());
//    }
}
