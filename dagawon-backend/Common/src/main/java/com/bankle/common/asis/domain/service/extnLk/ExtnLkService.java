package com.bankle.common.asis.domain.service.extnLk;

import java.util.ArrayList;
import java.util.HashMap;

import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.util.StringUtil;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExtnLkService {

    private final IfTgInfoMapper iftgInfoMapper;
    private       long           tg_log_m_key;
    private       String         rowCnts;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long saveTg(HashMap<String, Object> paramMap, HashMap<String, Object> tgDataMap) {

        String tg_cd  = (String)paramMap.get("tg_cd" );
        String tg_kcd = "";

        HashMap<String, Object> tgInfoMMap = iftgInfoMapper.selectTgM(paramMap);
        
        String escrYn      = "";  // 1 : Escr 송수신,  2 : FA 송수신 
        String tg_dsc      = (String)tgInfoMMap.get("TG_DSC"     );
        String snd_rcv_dsc = (String)tgInfoMMap.get("SND_RCV_DSC");
        String sr_dsc      = "";  // Escrow 송수신 구분 [ S : 송신, R : 수신 ]        
        String iu_dsc      = "";  // 
        
        if ("FA".equals(tg_dsc)) {
            
            if ("1".equals(snd_rcv_dsc)) {
                
                if ("1".equals(tg_cd.substring(3,4))) { iu_dsc = "I"; tg_kcd="S";} 
                else                                  { iu_dsc = "U"; tg_kcd="R";}
                
            } else {               
 
                if ("1".equals(tg_cd.substring(3,4))) { iu_dsc = "U"; tg_kcd="S";} 
                else                                  { iu_dsc = "I"; tg_kcd="R";}
                
            }
            
        } else if ("VA".equals(tg_dsc) || "WA".equals(tg_dsc)) {
            
            tg_kcd = (String)paramMap.get("tg_kcd");
            
            
            if ("1".equals(snd_rcv_dsc)) { 
                if ("S".equals(tg_kcd)) { iu_dsc = "I"; }
                else                    { iu_dsc = "U"; }
            } else {
                if ("R".equals(tg_kcd)) { iu_dsc = "I"; }
                else                    { iu_dsc = "U"; }                
            }           
        } else { iu_dsc = "1"; tg_kcd = "S" ; }
        
        log.info("=====>> snd_rcv_dsc [" + snd_rcv_dsc + "]     iu_dsc [" + iu_dsc  + "]     tg_kcd [" + tg_kcd + "]" );
        
        /*=======================================================================================*/
        /* 전문 상세 정보 등록                                                                   */
        /*=======================================================================================*/
        HashMap<String, Object> dbMap   = new HashMap<String, Object>();
        JSONObject              jsonObj = new JSONObject(tgDataMap);
        
        dbMap.put("tg_kcd"          , tg_kcd                      );
        dbMap.put("tg_log_m_key"    , paramMap.get("tg_log_m_key"));
        dbMap.put("escr_m_key"      , paramMap.get("escr_m_key"  ));
        dbMap.put("snd_rcv_rslt"    , paramMap.get("res_cd"      ));
        dbMap.put("snd_rcv_rslt_msg", paramMap.get("res_msg"     ));
        dbMap.put("tg_cd"           , paramMap.get("tg_cd"       ));
        dbMap.put("lk_tg"           , jsonObj.toJSONString()      );

        int  dbCnt     = 0;

        if ("1".equals(snd_rcv_dsc)) {        
            if ("I".equals(iu_dsc)) {
                iftgInfoMapper.insSndTgLog_M(dbMap);
                tg_log_m_key = (Long)dbMap.get("tg_log_m_key");                
            } else {
                dbCnt        =  iftgInfoMapper.updSndTgLog_M(dbMap); 
                tg_log_m_key =  (Long)paramMap.get("tg_log_m_key");                
            }            
        } else {
            if ("I".equals(iu_dsc)) {
                iftgInfoMapper.insRcvTgLog_M(dbMap); 
                tg_log_m_key = (Long)dbMap.get("tg_log_m_key");                
            } else {
                dbCnt        =  iftgInfoMapper.updRcvTgLog_M(dbMap);
                tg_log_m_key =  (Long)paramMap.get("tg_log_m_key");                
            }            
        }

        log.info("=====>> tg_log_m_key     : " + tg_log_m_key);
        
        ArrayList<HashMap<String, Object>> listTgInfoDMap = iftgInfoMapper.selectTgD(paramMap);
        int listSize = listTgInfoDMap.size();
        
        ArrayList<HashMap<String, Object>> listD = new ArrayList<HashMap<String, Object>>();        
        HashMap<String, Object>            map   = null;
        
        for (int i=0; i<listSize;i++) {
            
            map = listTgInfoDMap.get(i);
            
            rowCnts = (tgDataMap.get(map.get("TG_ITM_CD").toString())+"").trim();
            
            if (!"".equals(StringUtil.nvl(rowCnts)) && !"null".equals(rowCnts) && !"0".equals(rowCnts)) {
                HashMap<String, Object> logDMap = new HashMap<String, Object>();
                
                logDMap.put("tg_kcd"      , tg_kcd                  );
                logDMap.put("tg_log_m_key", tg_log_m_key            );
                logDMap.put("tg_cd"       , dbMap    .get("tg_cd"  ));
                logDMap.put("tg_odn"      , map      .get("TG_ODN" ));
                logDMap.put("tg_cnts"     , rowCnts                 );
                
                listD.add(logDMap);
            }            
        }
        
        if (listSize > 0 ) {
            
            iftgInfoMapper.insertTg_Log_D(listD);
        }        

        return tg_log_m_key;
    }
    
}
