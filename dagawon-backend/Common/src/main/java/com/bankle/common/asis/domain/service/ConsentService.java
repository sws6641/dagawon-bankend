package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.mapper.MemberMapper;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsentService {

    private final MemberMapper memberMapper;

    /**
     * 가장 최근의 약관내용 리스트 조회
     *
     * @return
     */
    public List<HashMap<String, Object>> getConsentContents() throws Exception {

        return memberMapper.selectEntrAsntCnts();
    }


    public int saveEntrAsnt(String memb_id, HashMap<String, Object> body) {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("memb_id", memb_id);

        paramMap.put("chkCd", getCdCheck(body));

        int dbCnt = memberMapper.deleteEntrAsnt(paramMap);
        dbCnt = memberMapper.insertEntrAsnt(paramMap);

        return dbCnt;
    }

    public String getCdCheck(HashMap<String, Object> body) {

        String rtnValue = "";
        String mktNotiYn = StringUtil.mapToString(body, "mktNotiYn");

        if ("N".equals(mktNotiYn)) {
            rtnValue = "01,02";
        } else {
            rtnValue = "01,02,03";
        }

        log.debug("=====>> getCdCheck [" + rtnValue + "]");
        return rtnValue;
    }
}
