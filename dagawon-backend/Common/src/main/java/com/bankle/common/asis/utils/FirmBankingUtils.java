package com.bankle.common.asis.utils;

import com.bankle.common.asis.component.FirmBankingTrnData;
import com.bankle.common.asis.component.frimBanking.FirmBankingTgBean;
import com.bankle.common.asis.component.properties.LguFBEvrProperties;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.util.DateUtil;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirmBankingUtils {

    private final LguFBEvrProperties fbEnvProp;
    private final IfTgInfoMapper ifTgInfoMapper;

    /*===========================================================================================*/
    /* Function Name : getTgCd                                                                   */
    /*                 전문헤더의 전문구분코드/업무구분코드 기준으로 TG_CD 셋팅                  */
    /*===========================================================================================*/
    public String getTgCd(String svcGbn, String tg_dsc, String wk_dsc) {

        String tg_cd = "";

        if ("DW".equals(svcGbn) || "EW".equals(svcGbn)) {

            if (("0800".equals(tg_dsc) || "0810".equals(tg_dsc)) && "100".equals(wk_dsc)) {
                tg_cd = "WA01";
            }  // 개시전문
            else if (("0100".equals(tg_dsc) || "0110".equals(tg_dsc)) && "100".equals(wk_dsc)) {
                tg_cd = "WA02";
            }  // LGU+ 펌뱅킹 원화거래 당행이체
            else if (("0101".equals(tg_dsc) || "0111".equals(tg_dsc)) && "100".equals(wk_dsc)) {
                tg_cd = "WA03";
            }  // LGU+ 펌뱅킹 원화거래 타행이체
            else if (("0600".equals(tg_dsc) || "0610".equals(tg_dsc)) && "100".equals(wk_dsc)) {
                tg_cd = "WA04";
            }  // LGU+ 펌뱅킹 원화거래 이체처리결과조회
            else if (("0600".equals(tg_dsc) || "0610".equals(tg_dsc)) && "300".equals(wk_dsc)) {
                tg_cd = "WA05";
            }  // LGU+ 펌뱅킹 원화거래 잔액조회
            else if (("0700".equals(tg_dsc) || "0710".equals(tg_dsc)) && "100".equals(wk_dsc)) {
                tg_cd = "WA06";
            }  // LGU+ 펌뱅킹 원화거래 이체집계
            else if (("0200".equals(tg_dsc) || "0210".equals(tg_dsc)) && "300".equals(wk_dsc)) {
                tg_cd = "WA07";
            }  // LGU+ 펌뱅킹 원화거래 예금거래명세통지
            else if (("0400".equals(tg_dsc) || "0410".equals(tg_dsc)) && "100".equals(wk_dsc)) {
                tg_cd = "WA08";
            }  // LGU+ 펌뱅킹 원화거래 타행불능통지
            else if (("0200".equals(tg_dsc) || "0210".equals(tg_dsc)) && "640".equals(wk_dsc)) {
                tg_cd = "WA09";
            }  // LGU+ 펌뱅킹 원화거래 예금거래명세결번요구
            else if (("0400".equals(tg_dsc) || "0410".equals(tg_dsc)) && "640".equals(wk_dsc)) {
                tg_cd = "WA10";
            }  // LGU+ 펌뱅킹 원화거래 타행불능결번요구

        } else {

            if (("0800".equals(tg_dsc) || "0810".equals(tg_dsc)) && "100".equals(wk_dsc)) {
                tg_cd = "VA01";
            }  // 개시전문
            else if (("0300".equals(tg_dsc) || "0310".equals(tg_dsc)) && "300".equals(wk_dsc)) {
                tg_cd = "VA02";
            }  // 수취인조회
            else if (("0200".equals(tg_dsc) || "0210".equals(tg_dsc)) && "300".equals(wk_dsc)) {
                tg_cd = "VA03";
            }  // 가상계좌통지

        }

        if ("".equals(tg_cd)) {
            tg_cd = "9999";
        }  // 전문구분코드, 업무구분코드 오류

        log.info("=====>> RCV 전문 Header svcGbn [ " + svcGbn + " ]   tg_dsc [" + tg_dsc + "]   wk_dsc [" + wk_dsc + "]   tg_cd [" + tg_cd + "]");

        return tg_cd;
    }

    public void getTgDsc(String tg_cd, FirmBankingTgBean fbTgBean) throws Exception {

        String tg_dsc = "";

        if ("VA02".equals(tg_cd)) {
            tg_dsc = "310";
        } else if ("VA03".equals(tg_cd)) {
            tg_dsc = "210";
        } else if ("WA07".equals(tg_cd)) {
            tg_dsc = "210";
        } else if ("WA08".equals(tg_cd)) {
            tg_dsc = "410";
        }

        fbTgBean.setData("TG_DSC", tg_dsc);
    }

    /*===========================================================================================*/
    /* Function Name : makePW  당타행 이체처리시 이중암호 생성                                   */
    /* Parameter     : tg_sqn : [공통은행전문내역 TLC_BNK_TG_I] 전문일련번호 (key)               */
    /* Retrurn       : [공통은행전문내역 TLC_BNK_TG_I] HashMap                                   */
    /*===========================================================================================*/
    public String makePW(String svrGbn, FirmBankingTrnData trnData) {

        String CURRENT_DATE = DateUtil.getDate("yyyyMMdd");
        String temp = "";
        long trn_amt = trnData.getTrn_amt();

        temp = CURRENT_DATE.substring(2)
                + StringUtil.rpad(trnData.getTr_acct_no(), 15, "0")
                + StringUtil.lpad(trn_amt + "", 13, "0")
                + trnData.getTr_acct_bnk_cd()
                + StringUtil.rpad(fbEnvProp.getAcctNo(svrGbn), 15, "0");

        int sum = 0;
        for (int i = 0; i < temp.length(); i++) {
            sum += Integer.parseInt(temp.substring(i, i + 1));
        }

        String num1 = String.valueOf(trn_amt / sum);
        String num2 = String.valueOf(trn_amt % sum);

        log.debug(">>>> 결과 : " + StringUtil.lpad(String.valueOf(sum), 3, "0") + StringUtil.lpad(num2, 3, "0"));
        return StringUtil.lpad(String.valueOf(sum), 3, "0") + StringUtil.lpad(num2, 3, "0");
    }

    /*===========================================================================================*/
    /* Function Name : checkResponse  우리은행 전문 응답 확인                                    */
    /* Parameter     : tg_sqn : [공통은행전문내역 TLC_BNK_TG_I] 전문일련번호 (key)               */
    /* Retrurn       : [공통은행전문내역 TLC_BNK_TG_I] HashMap                                   */
    /*===========================================================================================*/
    public HashMap<String, Object> checkResponse(long tg_log_m_key) throws Exception {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        HashMap<String, Object> selectMap = new HashMap<String, Object>();
        String snd_rcv_rslt = "";

        log.debug("=====>> checkResponse tg_log_m_key [" + tg_log_m_key + "]");

        paramMap.put("tg_log_m_key", tg_log_m_key);

        if (tg_log_m_key != 0) {

            //타임아웃동안 응답결과 조회
            int retrycnt = 0;

            while ("".equals(StringUtil.nvl(snd_rcv_rslt))) {

                selectMap.clear();
                selectMap = ifTgInfoMapper.selectResponse(paramMap);
                snd_rcv_rslt = StringUtil.mapToString(selectMap, "SND_RCV_RSLT");

                log.debug("=====>>>"
                        + "\n escr_m_key       [" + StringUtil.mapToString(selectMap, "ESCR_M_KEY") + "]"
                        + "\n tg_cd            [" + StringUtil.mapToString(selectMap, "TG_CD") + "]"
                        + "\n snd_rcv_rslt     [" + StringUtil.mapToString(selectMap, "SND_RCV_RSLT") + "]"
                        + "\n snd_rcv_rslt_msg [" + StringUtil.mapToString(selectMap, "SND_RCV_RSLT_MSG") + "]"
                );

                log.info("=====>> checkResponse tg_log_m_key [" + tg_log_m_key + "]   SND_RCV_RSLT [" + snd_rcv_rslt + "]");

                retrycnt++;
                if (retrycnt == 16) {
                    break;
                }
                Thread.sleep(2000);
            }

            //타임아웃시 RES_CODE 를 999 로 업데이트
            if ("".equals(StringUtil.nvl(snd_rcv_rslt))) {

                paramMap.put("snd_rcv_rslt", "9999");
                paramMap.put("snd_rcv_rslt_msg", "Response 누락");
                paramMap.put("lk_tg", "");
                selectMap.put("RES_CD", "9999");
                selectMap.put("RES_MSG", "Response 누락");

                updSndTgLog_M(paramMap);

            } else {
                selectMap.put("RES_CD", StringUtil.mapToString(selectMap, "SND_RCV_RSLT"));
                selectMap.put("RES_MSG", StringUtil.mapToString(selectMap, "SND_RCV_RSLT_MSG"));
            }
        }

        log.info("=====>> checkResponse   res_cd [" + (String) selectMap.get("RES_CD") + "]   res_msg [" + (String) selectMap.get("RES_MSG") + "]");
        return selectMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updSndTgLog_M(HashMap<String, Object> paramMap) {
        ifTgInfoMapper.updSndTgLog_M(paramMap);
    }

    public void getErrMsg(String tg_dsc, String err_cd, HashMap<String, Object> paramMap) {

        paramMap.put("tg_dsc", tg_dsc);
        paramMap.put("err_cd", err_cd);


        String res_msg = ifTgInfoMapper.selectErrMsg(paramMap);

        paramMap.put("RES_CD", err_cd);
        paramMap.put("RES_MSG", res_msg);
    }

    public boolean checkBankTgOpen(String svrGbn) {

        boolean rtnValue = false;
        HashMap<String, Object> selectMap = ifTgInfoMapper.selectBnkOpenDt(svrGbn);
        String chkYn = StringUtil.mapToString(selectMap, "CHK_YN");

        if ("Y".equals(chkYn)) {
            rtnValue = true;
        }

        return rtnValue;
    }

}
