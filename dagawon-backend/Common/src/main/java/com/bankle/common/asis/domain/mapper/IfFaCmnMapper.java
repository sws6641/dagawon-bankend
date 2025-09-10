package com.bankle.common.asis.domain.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IfFaCmnMapper {

    // 청약등록대상 목록 조회 (수수료 납입 완료 에스크로)
    ArrayList<HashMap<String, Object >>       selectSscptRegTrgt();
    // 에스크로 입금/출금 완료 전송 대상 목록 조회
    ArrayList<HashMap<String, Object >> selectRomPmntTrgt   ();        
    // 에스크로 청약 대상 Validation
    HashMap<String, Object>                   selectValidEscrSscpt(HashMap<String, Object> paramMap);
}
