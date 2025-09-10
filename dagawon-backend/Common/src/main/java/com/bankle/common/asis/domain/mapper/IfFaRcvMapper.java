package com.bankle.common.asis.domain.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IfFaRcvMapper {
    
    int checkReqEscr      (HashMap<String, Object> paramMap);
    int updateRschRslt    (HashMap<String, Object> paramMap);
    int updateIsrnScrtPrtw(HashMap<String, Object> paramMap);
}
