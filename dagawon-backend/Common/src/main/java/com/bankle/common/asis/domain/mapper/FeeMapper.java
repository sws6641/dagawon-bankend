package com.bankle.common.asis.domain.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FeeMapper {

    int insertFeeStmtH   (HashMap<String, Object> paramMap);  // Toss 결제 결과 이력 등록
    int insertFeeRomTossD(HashMap<String, Object> paramMap);  // Toss 결제 등록
    int updateEscrFeeRom (HashMap<String, Object> paramMap);  // 수수료납입 진행상태 update
    HashMap<String, String> selectcFeeStmtH (HashMap<String, String> paramMap);
}