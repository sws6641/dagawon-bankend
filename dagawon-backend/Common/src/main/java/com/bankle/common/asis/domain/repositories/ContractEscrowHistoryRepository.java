package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ContractEscrowHIstory;
import com.bankle.common.asis.domain.entity.ids.ContractEscrowHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractEscrowHistoryRepository extends JpaRepository<ContractEscrowHIstory, ContractEscrowHistoryId> {
}
