package com.bankle.common.asis.utils;

import com.bankle.common.asis.domain.service.CodeService;
import com.bankle.common.asis.domain.service.CodeServiceWithRedis;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CommonUtils {


    private static CodeServiceWithRedis codeServiceWithRedis;
    private static CodeService codeService;

    /**
     * 임차인/매수인 중에 PRDT_DSC에 해당하는 단어 리턴
     *
     * @param prdtDsc PRDT_DSC(1: 임대, 2: 매매)
     * @param prtyDsc
     * @return
     */
    public static String getPrtyDscValue(String prdtDsc, String prtyDsc) throws Exception {
        String cmnNm = CommonUtils.getCmnNm("PRTY_DSC", prtyDsc);
        String[] split = cmnNm.split("/");
        return ("1".equals(prdtDsc)) ? split[1] : split[0];
    }

    /**
     * 공통코드명 조회
     *
     * @param grpCd
     * @param cmnCd
     * @return
     * @throws Exception
     */
    public static String getCmnNm(String grpCd, String cmnCd) throws Exception {
        String cmnNm = codeServiceWithRedis.getCmnNm(grpCd, cmnCd);
        if (!StringUtils.hasText(cmnNm)) {
            cmnNm = codeService.getCmnNm(grpCd, cmnCd);
        }
        return cmnNm;
    }
}
