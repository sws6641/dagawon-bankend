package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ContractEscrowParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractEscrowPartyRepository extends JpaRepository<ContractEscrowParty, Long> {

    List<ContractEscrowParty> findAllByEscrMKey(Long escrMKey);

    ContractEscrowParty findByEscrMKeyAndPrtyHpNo(Long escrMKey, String prtyHpNo);

    Optional<ContractEscrowParty> findByEscrMKeyAndPrtyNmAndPrtyBirthDtAndPrtySex(Long escrMKey, String prtyNm, String prtyBirthDate, String prtySex);

}
