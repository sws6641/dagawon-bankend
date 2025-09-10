package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ContractEscrowNoti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractEscrowNotiRepository extends JpaRepository<ContractEscrowNoti, Long> {
    List<ContractEscrowNoti> findAllByEscrMKey(Long escrMKey);
}
