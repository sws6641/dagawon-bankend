package com.bankle.common.asis.component.frimBanking;

import java.util.HashMap;
import java.util.List;

import com.bankle.common.asis.component.Box;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirmBankingTgBean extends GetSetData {

    /*===========================================================================================*/
    /* LGU+ Firm Banking 전문 통신 공통 Value Object 생성자                                               */
    /*===========================================================================================*/
    public FirmBankingTgBean(List<HashMap<String, Object>> listBnkTgC) throws Exception {
        
            setVariable(listBnkTgC.size());  // Layout Column Count Setting
            
            String[] arrNm  = getArrName  ();
            int   [] arrLen = getArrLength();
            String[] arrTp  = getArrType  ();
            
            int i = 0;
            Box rowBox = new Box();

            for(i = 0; i < listBnkTgC.size(); i++) {

                rowBox.putAll(listBnkTgC.get(i));

                arrNm [i] = rowBox.getString("TG_ITM_CD"  );  // 전문 Layout 컬럼 명   
                arrLen[i] = rowBox.getInt   ("ITM_LEN"    );  // 전문 Layout 컬럼 길이 
                arrTp [i] = rowBox.getString("ITM_PRPT_CD");  // 전문 Layout 컬럼 타입 (N : 숫자, S : String 영문/숫자, K : 한글포함)
                
                //log.info("=====>> TG_ITM_CD [" + arrNm [i] + "]   ITM_LEN [" + arrLen[i] + "]   ITM_PRPT_CD [" + arrTp [i] + "]");                
            }
       
            setMap();
    }
}
