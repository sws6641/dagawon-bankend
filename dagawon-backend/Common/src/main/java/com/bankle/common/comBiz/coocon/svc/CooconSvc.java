package com.bankle.common.comBiz.coocon.svc;

import com.bankle.common.comBiz.coocon.vo.CooconSvo;
import com.bankle.common.config.CommonConfig;
import com.bankle.common.enums.Sequence;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.util.BizUtil;
import com.bankle.common.util.httpapi.HttpApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CooconSvc {

    private final static String API_KEY = "ACCTNM_RCMS_WAPI";
    private final BizUtil bizUtil;

    public String searchAcctNo(List<CooconSvo.AcctReqData> cooconSvoList) throws Exception {

        String trscSeqNo = bizUtil.getSeq(Sequence.ACCT_VERF);
        String apiNm = "";
        try {

            List<CooconSvo.AcctComm> cooconVoList = new ArrayList<>();
            for (CooconSvo.AcctReqData cooconSvo : cooconSvoList) {
                // 공통부 작성
                CooconSvo.AcctComm cooconVo = new CooconSvo.AcctComm();
                cooconVo.setSecrKey(CommonConfig.COOCON_KEY);
                cooconVo.setKey(API_KEY);
                cooconVo.setCharSet("UTF-8");


                List<CooconSvo.AcctReqData> acctReqList = new ArrayList<>();

                CooconSvo.AcctReqData acctReq = CooconSvo.AcctReqData.builder()
                        .bankCd(cooconSvo.getBankCd())
                        .searchAcctNo(cooconSvo.getSearchAcctNo())
                        .acnmNo(cooconSvo.getAcnmNo())
                        .trscSeqNo("0" + trscSeqNo.substring(8, 14))
                        .build();
                acctReqList.add(acctReq);
                cooconVo.setReqData(acctReqList);

                cooconVoList.add(cooconVo);
            }

            // 예금주 리스트만큼 For문 작성
            for(CooconSvo.AcctComm acctComm:cooconVoList){

                var api = HttpApi.create(HttpMethod.POST, CommonConfig.COOCON_URL)
                        .header("Content-Type", "application/json")
                        .inserter(BodyInserters.fromValue(acctComm));

                var res = api.sync(CooconSvo.AcctResData.class);
                var apiRes = res.getBody();
                log.debug("COOCON API SEARCH RESPONSE CODE =>{}", res.getStatusCode());
                if (!"000".equals(apiRes.getRsltCd())) {
                    log.debug("*******************************************************");
                    res.getBody().getRsltMg();
                    log.debug("*******************************************************");
                    continue;
                }

                // 위 결과가 통과가 되었다 하면 apinm에 담아준다
                apiNm = apiRes.getRespData().get(0).getAcctNm();
                break;
            }


            return apiNm;

        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }
}
