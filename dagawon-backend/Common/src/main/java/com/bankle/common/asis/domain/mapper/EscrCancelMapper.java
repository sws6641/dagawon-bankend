package com.bankle.common.asis.domain.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface EscrCancelMapper {
    
    HashMap<String, Object> checkEscrCancel(HashMap<String, Object> paramMap);
    
    int updateVrAcctM    (HashMap<String, Object> paramMap);  // 가상계좌 할당여부 수정
    int updateVracctAsgnD(HashMap<String, Object> paramMap);  // 가상계좌 할당 사용여부 수정
    int insertEscrPmntD  (HashMap<String, Object> paramMap);  // 반환수수료/에스크로반환금 지급상세 INSERT
    int updateEscrM      (HashMap<String, Object> paramMap);  // 해지진행코드 및 수수료/반환금액 정보 수정
}
