package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.CodeDetail;
import com.bankle.common.asis.domain.entity.CodeMaster;
import com.bankle.common.asis.domain.repositories.CodeMasterRepository;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeService {

    private final CodeMasterRepository codeMasterRepository;

    /**
     * 코드조회
     * @param grpCd
     * @return
     */
    public CodeMaster findByGrpCd(String grpCd){
        Optional<CodeMaster> byId = codeMasterRepository.findByGrpCdAndUseYn(grpCd,"Y");
        if(byId.isPresent()){
            log.debug(byId.toString());
            return byId.get();
        }else{
            return null;
        }
    }

    /**
     * 전체코드 조회
     * @return
     */
    public List<CodeMaster> getCodes() {
        return codeMasterRepository.findAllByUseYn("Y");
    }

    public String getCmnNm(String grpCd, String cmnCd) {
        AtomicReference<String> cmnNm = new AtomicReference<>("");
        CodeMaster byGrpCd = findByGrpCd(grpCd);
        if(byGrpCd != null){
            List<CodeDetail> codeDetails = byGrpCd.getCodeDetails();
            codeDetails.stream().filter(d -> StringUtil.equals(cmnCd, d.getCodeDetailId().getCmnCd()))
                    .findFirst()
                    .ifPresent(codeDetail -> cmnNm.set(codeDetail.getCmnCdNm()));
        }

        return cmnNm.get();
    }
}
