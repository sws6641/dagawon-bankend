package com.bankle.common.util;

import com.bankle.common.enums.Sequence;
import com.bankle.common.repo.TbSequenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BizUtil {

    // Seq 조회
    private final TbSequenceRepository tbSequenceRepository;

    /**
     * Sequence 가져오기
     *
     * @param : Sequence 구분값
     * @return : 20230915000001
     * @name : WooriCmnSvc.getSeq
     * @author : tigerBK
     **/
    public String getSeq(Sequence seqType){
        log.debug("seqType:"+seqType.toString());

        return tbSequenceRepository.getSeq(seqType.getSeqNm() , seqType.getSeqLen());
    }
}
