package com.bankle.common.repo;

import com.bankle.common.entity.TbEscrFeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TbEscrFeePaymentRepository extends JpaRepository<TbEscrFeePayment, Long> {

  Optional<TbEscrFeePayment> findTopByEscrNoOrderByCrtDtmDesc(String escrNo);
}