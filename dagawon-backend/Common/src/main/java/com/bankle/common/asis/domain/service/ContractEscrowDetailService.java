package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.ContractEscrowDetail;
import com.bankle.common.asis.domain.repositories.ContractEscrowDetailsRepository;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractEscrowDetailService {
    private final ContractEscrowDetailsRepository contractEscrowDetailsRepository;

    /**
     * 삭제될 키 조회
     * @param allByEscrMKey
     * @param details
     * @return
     */
    private String getWillBeDeletedEscrDKey(List<ContractEscrowDetail> allByEscrMKey, List<HashMap<String, Object>> details){
        ArrayList oldEscrDKey = new ArrayList();
        allByEscrMKey.forEach(d -> oldEscrDKey.add(d.getEscrDKey()));


        ArrayList newEscrDKey = new ArrayList();
        details.forEach(d -> newEscrDKey.add(String.valueOf(d.get("escrDKey"))));


        AtomicReference<String> escrDKey = new AtomicReference<>("");
        oldEscrDKey.stream().filter(s -> !newEscrDKey.contains(String.valueOf(s)))
                .findAny()
                .ifPresent(s -> escrDKey.set(String.valueOf(s)));

        return escrDKey.get();
    }

    /**
     * 계약금 ~ 잔금 수정
     * @param escrMKey
     * @param allByEscrMKey
     * @param details
     */
    @Transactional
    public void updateEscrowDetails(Long escrMKey, List<ContractEscrowDetail> allByEscrMKey, List<HashMap<String, Object>> details){

        String willBeDeletedEscrDKey = getWillBeDeletedEscrDKey(allByEscrMKey, details);
        //삭제
        allByEscrMKey.stream()
                .filter(asD -> StringUtil.equals(willBeDeletedEscrDKey, String.valueOf(asD.getEscrDKey())))
                .findAny()
                .ifPresent(asD -> contractEscrowDetailsRepository.deleteById(asD.getEscrDKey()));

        //신규
        details.stream().filter(newD -> (!StringUtils.hasText(String.valueOf(newD.get("escrDKey")))))
                .findAny()
                .ifPresent(newD ->{
                    ContractEscrowDetail build = ContractEscrowDetail.builder()
                            .chrgDsc((String) newD.get("chrgDsc"))
                            .escrAmt(StringUtil.stringToLong(String.valueOf(newD.get("escrAmt"))))
                            .romPlnDt(((String) newD.get("romPlnDt")).replaceAll("-", ""))
                            .escrMKey(escrMKey)
                            .build();
                    contractEscrowDetailsRepository.save(build);
                });

        //업데이트
        allByEscrMKey.forEach(asD -> {
            if(asD.getEscrDKey() != null){
                details.stream()
                        .filter(newD -> StringUtil.equals(String.valueOf(newD.get("escrDKey")), asD.getEscrDKey().toString()))
                        .findFirst()
                        .ifPresent(d -> {
                            Optional<ContractEscrowDetail> byId = contractEscrowDetailsRepository.findById(asD.getEscrDKey());
                            if (byId.isPresent()) {
                                byId.get().setChrgDsc((String) d.get("chrgDsc"));
                                byId.get().setEscrAmt(Long.valueOf(String.valueOf(d.get("escrAmt")).replaceAll(",","")));
                                byId.get().setRomPlnDt((String.valueOf(d.get("romPlnDt"))).replaceAll("-", ""));
                                contractEscrowDetailsRepository.save(byId.get());
                            }
                        });
            }
        });
    }

    public List<ContractEscrowDetail> getDetails(Long escrMKey){
        return contractEscrowDetailsRepository.findAllByEscrMKey(escrMKey);
    }
}
