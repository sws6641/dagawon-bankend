package com.bankle.common.asis.domain.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IfFaSndMapper {
    
    // FA 청약의뢰 전송정보 조회
    HashMap<String, Object> selectEscrSscpt  (HashMap<String, Object> paramMap);
    // FA 입금출금 전송정보 조회
    HashMap<String, Object> selectEscrRomPmnt(HashMap<String, Object> paramMap);
    
    // FA 청약의뢰 정보 Update
    int updateEscrSscpt  (HashMap<String, Object> paramMap);    
    // Daily 조사결과 등록
    int insertFaRschRsltI(HashMap<String, Object> paramMap);
    // FA 입금출금 전송 정보 Update
    int updateEscrRomPmnt(HashMap<String, Object> paramMap);
    int updateRomEscrMDV (HashMap<String, Object> paramMap);
    int updatePmntEscrMDV(HashMap<String, Object> paramMap);
}
