package com.bankle.common.asis.domain.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FbTgInfoMapper {
    
    // 가상계좌번호로 회원명 조회
    HashMap<String, Object> selectAcctMembNmDV (HashMap<String, Object> paramMap);
    HashMap<String, Object> selectAcctMembNmEV (HashMap<String, Object> paramMap);
    
    // 대금 구분코드 조회
    HashMap<String, Object> selectRomKeyInfo(HashMap<String, Object> paramMap);

    // 수수료 입금 가산계좌로 ESCR_M_KEY 조회
    long selectFeeKeyInfo  (HashMap<String, Object> paramMap);
    int  selectValidFeeAmt (HashMap<String, Object> paramMap);
    
    // (당행/타행) DB 이체 대상 조회
    HashMap<String, Object> selectTrnTrgt(HashMap<String, Object> paramMap);

    // 보험료 조회
    HashMap<String, Object> selectIsrnPrmm(HashMap<String, Object> paramMap);    

    // 가상계좌 입금정보 조회
    HashMap<String, Object> selectRomInfo(HashMap<String, Object> paramMap);
    
    // 가상계좌입금내역    
    int insertVrAcctRomI    (HashMap<String, Object> paramMap);
    int insertEscrRomD      (HashMap<String, Object> paramMap);
    int updateEscrDDV       (HashMap<String, Object> paramMap);
    int updateFeeRomDEV     (HashMap<String, Object> paramMap);
    int updateVrAcctAsgnDDV (HashMap<String, Object> paramMap);
    int updateVrAcctMDV     (HashMap<String, Object> paramMap);
    int updateEscrMDV       (HashMap<String, Object> paramMap);
    
    int updateVrAcctMEV     (HashMap<String, Object> paramMap);
    int updateEscrMEV       (HashMap<String, Object> paramMap);
    int updateIsrnPrmmRom   (HashMap<String, Object> paramMap);
    int updateEscrPmntResult(HashMap<String, Object> paramMap);
    
    int selectTrnCount(); // 이체거래건수조회 (예금거래명세결번요구 전문 처리용)
    
    ArrayList<HashMap<String, Object>> selectTrNmagMsno(int trnCnt);
    
    int insertTeiTrnErrI (long escr_m_key);
    int updateEscrIsrnPrmmRom(HashMap<String, Object> paramMap);    
}
