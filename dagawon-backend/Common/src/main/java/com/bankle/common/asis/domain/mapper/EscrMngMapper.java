package com.bankle.common.asis.domain.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface EscrMngMapper {
    
    // 입지급 완료 에스크로 조회
    ArrayList<HashMap<String, Object>> selectEscrFn();
    
    // 에스크로 진행상태 수정 (에스크로종료)
    int updateEscrFn(HashMap<String, Object> paramMap);
    
    ArrayList<HashMap<String, Object>> selectEscrRomReq();
    
    int updateEscrRomReq(HashMap<String, Object> paramMap); 

    ArrayList<HashMap<String, Object>> selectEscrRomDly();
    
    int updateEscrRomDly(HashMap<String, Object> paramMap);

    ArrayList<HashMap<String, Object>> selectEscrPayDly();
    
    int updateEscrPayDly(HashMap<String, Object> paramMap);    

    // 에스크로 알림 처리 대상 (입금요청, 입금지연, 지급요청, 지급지연)
    ArrayList<HashMap<String, Object>> selectEscrNoti();  
    
    ArrayList<HashMap<String, Object>> selectAsgnVrAcct(); // 가상계좌 할당 입금 대상 조회
    
    ArrayList<HashMap<String, Object>> selectVrAcct();  // 사용 가능 가상계좌 조회
    
    int insertVracctAsgn(HashMap<String, Object> paramMap);
    
    int updateVrAcct(HashMap<String, Object> paramMap);


    ArrayList<HashMap<String, Object>> selectEscrPmntReq();  // 사용 가능 가상계좌 조회
    
    int updateEscrPmntReq(HashMap<String, Object> paramMap);
    
}
