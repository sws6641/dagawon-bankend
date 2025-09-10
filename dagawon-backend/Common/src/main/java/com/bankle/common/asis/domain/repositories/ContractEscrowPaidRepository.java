package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ContractEscrowPmnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractEscrowPaidRepository extends JpaRepository<ContractEscrowPmnt, Long> {
}
