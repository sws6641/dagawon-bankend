package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.RgstrIcdnt;
import com.bankle.common.asis.domain.repositories.RgstIncdntRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RgstIncdntService {
    private final RgstIncdntRepository rgstIncdntRepository;

    /**
     * 등기변동 조사 결과 조회
     *
     * @param escrMKey
     * @return
     */
    public List<RgstrIcdnt> findByEscrMKey(Long escrMKey) {
        return rgstIncdntRepository.findByEscrMKeyOrderByAcptDtDesc(escrMKey);


    }
}
