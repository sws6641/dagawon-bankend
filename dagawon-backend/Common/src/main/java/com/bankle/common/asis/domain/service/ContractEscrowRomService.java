package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.ContractEscrowRom;
import com.bankle.common.asis.domain.repositories.ContractEscrowRomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractEscrowRomService {
    private final ContractEscrowRomRepository contractEscrowRomRepository;

    /**
     * 입금내역 조회
     * @param escrMKey
     * @return
     */
    public List<ContractEscrowRom> getAllRom(Long escrMKey){
        return contractEscrowRomRepository.findAllByescrMKeyOrderByEscrRomDKeyDesc(escrMKey);
    }
}
