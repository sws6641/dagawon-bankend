package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ContractEscrowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractEscrowDetailsRepository extends JpaRepository<ContractEscrowDetail, Long> {

    void deleteAllByEscrMKey(Long escrMKey);

    List<ContractEscrowDetail> findAllByEscrMKey(Long escrMKey);

    ContractEscrowDetail findByEscrMKeyAndChrgDsc(Long escrMKey, String chrgDsc);
}
