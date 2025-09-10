package com.bankle.common.asis.domain.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IfTgInfoMapper {
    
    HashMap<String, Object>            selectTgM     (HashMap<String, Object> paramMap);
    HashMap<String, Object>            selectTgMEscr (HashMap<String, Object> paramMap);    
    ArrayList<HashMap<String, Object>> selectTgD     (HashMap<String, Object> paramMap);
    ArrayList<HashMap<String, Object>> selectFBTgD   (HashMap<String, Object> paramMap);
    
    HashMap<String, Object>            selectResponse(HashMap<String, Object> paramMap);
    HashMap<String, Object>            selectResponse1(HashMap<String, Object> paramMap);
    
    String                             selectErrMsg  (HashMap<String, Object> paramMap);
    
    int insSndTgLog_M(HashMap<String, Object> paramMap);
    int updSndTgLog_M(HashMap<String, Object> paramMap);
    
    int insRcvTgLog_M(HashMap<String, Object> paramMap);
    int updRcvTgLog_M(HashMap<String, Object> paramMap);
    
    int insertTg_Log_D(ArrayList<HashMap<String, Object>> list);
    
    long getChgLogMKey(HashMap<String, Object> paramMap);
    HashMap<String, Object> getOriLogMKey(HashMap<String, Object> paramMap);
    
    int  insertBackupEscrM(HashMap<String, Object> paramMap);
    
    /* KAKAO Message, SMS Message Send */
    HashMap<String, Object> selectSendMsg(HashMap<String, Object> paramMap);
    HashMap<String, Object> selectSendMsgNoEscr(HashMap<String, Object> paramMap);
    
    HashMap<String, Object> selectRcvTgLogInfo(HashMap<String, Object> paramMap);
    
    // 은행거래 개시전문 확인
    HashMap<String, Object> selectBnkOpenDt   (String svr_gbn);
    int                     updateBnkOpenDt   (String svr_gbn);
}
