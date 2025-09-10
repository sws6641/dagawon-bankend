package com.bankle.common.repo;

import com.bankle.common.entity.TbEscrDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbEscrDepositRepository extends JpaRepository<TbEscrDeposit, Long> {
    List<TbEscrDeposit> findByEscrNo(String escrNo);
}