package com.bankle.common.asis.component;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FirmBankingTrnData {
   
    private String tr_acct_bnk_cd;  // (당/타행계좌이체) 거래계좌은행코드
    private String tr_acct_no;      // (당/타행계좌이체) 거래계좌번호
    private String trmn_nm;         // (당/타행계좌이체) 거래인명
    private long   trn_amt;         // (당/타행계좌이체) 이체금액
    
    /*===========================================================================================*/
    
    private String trn_tg_trns_dt;  // (이체결과조회) 이체전문전송일자
    private String trn_tg_trns_no;  // (이체결과조회) 이체전문전송번호
    
    /*===========================================================================================*/
    
    private String nmag_noti_trns_dt;   // (예금거래명세통지) 명세통지전송일자
    private String nmag_noti_tg_no  ;   // (예금거래명세통지) 명세통지전문번호
    
}