package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractEscrowRepository extends JpaRepository<ContractEscrow, Long> {

    List<ContractEscrow> findAllByMembNo(String membNo);

    List<ContractEscrow> findAllByMembNoOrderByEscrMKeyDesc(String membNo);

    Optional<ContractEscrow> findContractEscrowByEscrMKey(Long escrMKey);

    List<ContractEscrow> findAllByPmntPlnDtIsNotNullAndPrdtTpc(String prdtTpc);

    List<ContractEscrow> findAllByPrdtTpc(String prdtTpc);

    List<ContractEscrow> findAllByEscrDtlPgc(String escrDtlPgc);

    List<ContractEscrow> findByMembNo(String membNo);
}
