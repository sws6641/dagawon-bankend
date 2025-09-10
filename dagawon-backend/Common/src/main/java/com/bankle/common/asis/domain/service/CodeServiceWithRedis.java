package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.CodeDetail;
import com.bankle.common.asis.domain.entity.CodeMaster;
import com.bankle.common.asis.domain.repositories.CodeMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeServiceWithRedis {

    private final RedisTemplate redisTemplate;
    private final CodeMasterRepository codeMasterRepository;

    public void saveToRedis() {

        List<CodeMaster> all = codeMasterRepository.findAllByUseYn("Y");
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        for (CodeMaster codeMaster : all) {
            String grpCd = codeMaster.getGrpCd();

            if (StringUtils.hasText(grpCd)) {
                List<CodeDetail> details = codeMaster.getCodeDetails();
                for (CodeDetail detail : details) {
                    if ("Y".equals(detail.getUseYn()))
                        hashOperations.put(grpCd,
                                detail.getCodeDetailId().getCmnCd(),
                                detail.getCmnCdNm());
                }
            }
        } //end of for
    }

    public Map<String, String> getCodeFromRedis(String grpCd) throws Exception {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(grpCd);
    }

    /**
     * 공통코드 조회
     *
     * @param grpCd
     * @return
     * @throws Exception
     */
    public String getCmnNm(String grpCd, String cmnCd) throws Exception {
        Map<String, String> codeFromRedis = getCodeFromRedis(grpCd);
        return codeFromRedis.get(cmnCd);
    }
}
