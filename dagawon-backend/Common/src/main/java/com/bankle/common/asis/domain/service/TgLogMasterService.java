package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.TgLogMaster;
import com.bankle.common.asis.domain.repositories.TgLogMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TgLogMasterService {
    private final TgLogMasterRepository tgLogMasterRepository;

    public TgLogMaster save(TgLogMaster entity) {
        return tgLogMasterRepository.save(entity);
    }
}
