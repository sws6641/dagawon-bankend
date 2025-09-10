package kr.co.anbu.domain.service;

import com.bankle.common.asis.domain.mapper.IfFaRcvMapper;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.asis.utils.EscrSendMsgUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 김배성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FARcvService {

    private final IfTgInfoMapper ifTgInfoMapper;
    private final IfFaRcvMapper ifFaRcvMapper;
    private final EscrSendMsgUtils escrSendMsg;

    public HashMap<String, Object> checkReqEscr(HashMap<String, Object> paramMap) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        int chkValue = ifFaRcvMapper.checkReqEscr(paramMap);

        String errMsg = "";

        if (0 == chkValue) {
            errMsg = "정상";
        } else if (1 == chkValue) {
            errMsg = "안부 에스크로 관리번호가 존재하지 않습니다.";
        } else if (2 == chkValue) {
            errMsg = "안부 에스크로 보험형이 아닙니다.";
        } else if (3 == chkValue) {
            errMsg = "보험 가입단계인 에스크로 거래가 아닙니다.";
        } else if (4 == chkValue) {
            errMsg = "보험 청약중인 에스크로 거래가 아닙니다.";
        } else if (5 == chkValue) {
            errMsg = "보험 가입이 승인되지 않았거나 거절된 거래 입니다.";
        } else if (6 == chkValue) {
            errMsg = "보험증권번호가 존재하지 않습니다.";
        } else if (7 == chkValue) {
            errMsg = "보험증권번호가 상이 합니다.";
        } else {
            errMsg = "Validation 장애 발생";
        }

        rtnMap.put("result", chkValue + "");
        rtnMap.put("errordesc", errMsg);

        return rtnMap;
    }

    /*===========================================================================================*/
    // updateRschRslt : 조사결과 TEI_ESCR_M 테이블 UPDATE
    /*===========================================================================================*/
    @Transactional
    public int updateRschRslt(Map<String, Object> validOKJsonMap) {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("escr_m_key", validOKJsonMap.get("VMngCode"));   // 안부 에스크로 관리번호
        paramMap.put("cntrt_ask_agr_cd", validOKJsonMap.get("RR"));   // 계약 의뢰 승낙 코드
        paramMap.put("cntrt_cncl_rsn_cd", validOKJsonMap.get("RRCode"));   // 계약해지사유코드
        paramMap.put("cntrt_cncl_rsn_cnts", validOKJsonMap.get("DeniedRemark"));   // 계약해지사유내용
        paramMap.put("isrn_prmm", validOKJsonMap.get("Premium"));   // 보험료
        paramMap.put("cnfmtn_url", validOKJsonMap.get("ReportUrl"));   // 확인서 URL
        paramMap.put("rgst_rd_cnts", validOKJsonMap.get("ReportContents"));   // 등기부열람내용
        paramMap.put("rgst_rd_dtm", validOKJsonMap.get("ContentsDate"));   // 등기부열람일시
        paramMap.put("rmk_fct", validOKJsonMap.get("ReportRemark"));   // 비고 사항

        log.info("=====>> validOKJsonMap [" + validOKJsonMap.toString() + "]");

        int dbCnt = 0;
        dbCnt = ifFaRcvMapper.updateRschRslt(paramMap);
        dbCnt = ifTgInfoMapper.insertBackupEscrM(paramMap);


        String cntrt_ask_agr_cd = validOKJsonMap.get("RR").toString();

        if ("1".equals(cntrt_ask_agr_cd)) {
            escrSendMsg.sendMsg("", 33002, null, null);    // 청약승인
        } else {
            escrSendMsg.sendMsg("", 33003, null, null);    // 청약거절
        }

        return dbCnt;
    }

    /*===========================================================================================*/
    // updateRschRslt : 보험증권정보 TEI_ESCR_M 테이블 UPDATE
    /*===========================================================================================*/
    @Transactional
    public int updateIsrnScrtPrt(Map<String, Object> validOKJsonMap) {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("escr_m_key", validOKJsonMap.get("VMngCode"));   // 안부 에스크로 관리번호
        paramMap.put("prdt_mnl_url", validOKJsonMap.get("ManualUrl"));   // 상품설명서 URL
        paramMap.put("isrn_scrt_url", validOKJsonMap.get("PolicyUrl"));   // 보험증권 URL

        int dbCnt = 0;
        dbCnt = ifFaRcvMapper.updateIsrnScrtPrtw(paramMap);
        dbCnt = ifTgInfoMapper.insertBackupEscrM(paramMap);
        return dbCnt;
    }
}
