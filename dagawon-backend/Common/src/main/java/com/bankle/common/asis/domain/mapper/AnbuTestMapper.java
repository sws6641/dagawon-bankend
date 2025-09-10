package com.bankle.common.asis.domain.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AnbuTestMapper {

    int selectTrscSeqNo();
    int insertEscrPrtyCtfc(HashMap<String, String> paramMap);
    int insertEscrPrtyD   (HashMap<String, String> paramMap);
    HashMap<String, Object> chkDelEscrPrty (Long escr_prty_d_key);
    HashMap<String, String> selectPrtyCnt  (String escr_m_key);
    int updateEscrDtlPgc(HashMap<String, String> paramMap);
    int updateEscrPmntAcctInfo(HashMap<String, Object> paramMap);
    
}