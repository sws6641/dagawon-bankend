package com.bankle.common.asis.domain.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BatchTestMapper {
    
    // 입지급 완료 에스크로 조회
    HashMap<String, Object> selectEscr(String escr_m_key);    
}
