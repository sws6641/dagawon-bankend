package com.bankle.common.asis.domain.mapper;

import com.bankle.common.asis.infra.ContractEscrowReportDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface ContractEscrowMapper {
    
    List<ContractEscrowReportDto> selectContractEscrowReport(HashMap<String, Long> inputMap);
    
    List<HashMap<String, Object>> selectEscrPrtyD(HashMap<String, Object> paramMap);    
    
    HashMap<String, Object> checkEscrPmnt(HashMap<String, String> parammap);
    
    int insertEscrPmntD(HashMap<String, Object> paramMap);
    int updateEscrPmnt (HashMap<String, Object> paramMap);
    int updateEscrDPmnt(HashMap<String, Object> paramMap);
    
    int updateNop      (HashMap<String, Object> paramMap);
    
    
    int checkTrAsntFn(HashMap<String, Object> paramMap);
    String getRomPlnDt(String escr_m_key);
}
