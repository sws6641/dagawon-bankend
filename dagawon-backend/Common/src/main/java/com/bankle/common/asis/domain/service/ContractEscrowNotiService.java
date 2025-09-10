package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.entity.ContractEscrowNoti;
import com.bankle.common.asis.domain.repositories.ContractEscrowNotiRepository;
import com.bankle.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractEscrowNotiService {

    private final ContractEscrowNotiRepository contractEscrowNotiRepository;
    private final ContractEscrowService contractEscrowService;

    /**
     * 알림저장
     * @param map
     * @return
     */
    public ContractEscrowNoti save(HashMap<String, String> map){

        ContractEscrowNoti build = ContractEscrowNoti.builder()
                .notiDtm(DateUtil.getThisDate("yyyyMMdd"))
                .notiTtl(map.get("notiTtl"))
                .notiCnts(map.get("notiCnts"))
                .build();

        if(map.get("escrMKey") != null && StringUtils.hasText(map.get("escrMKey")))
            build.setEscrMKey(Long.valueOf(map.get("escrMKey")));

        return contractEscrowNotiRepository.save(build);
    }

    /**
     * 알림내역조회
     * @param escrMKey
     * @return
     * @throws Exception
     */
    public List<ContractEscrowNoti> getAllNoti(Long escrMKey){

        ContractEscrow contract = contractEscrowService.getContractEscrow(escrMKey);
        if(contract == null)
            throw new RuntimeException("잘못된 요청 입니다.");

        return contractEscrowNotiRepository.findAllByEscrMKey(escrMKey);
    }
}
