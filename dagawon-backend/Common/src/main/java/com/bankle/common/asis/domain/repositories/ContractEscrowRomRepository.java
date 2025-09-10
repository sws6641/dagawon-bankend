package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ContractEscrowRom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractEscrowRomRepository extends JpaRepository<ContractEscrowRom, Long> {
    List<ContractEscrowRom> findAllByescrMKeyOrderByEscrRomDKeyDesc(Long escrMKey);


}
