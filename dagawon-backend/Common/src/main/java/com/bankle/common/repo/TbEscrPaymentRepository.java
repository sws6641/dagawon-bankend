package com.bankle.common.repo;

import com.bankle.common.entity.TbEscrPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbEscrPaymentRepository extends JpaRepository<TbEscrPayment, Long> {
    Optional<TbEscrPayment> findByEscrNoAndChrgGbCd(String escrNo, String chrgGb);

    List<TbEscrPayment> findByEscrNo(String escrNo);

    List<TbEscrPayment> findByEscrNoAndChrgGbCdIn(String escrNo, List<String> chrgGbs);

    Optional<TbEscrPayment> findTop1ByEscrNoAndProcCdAndChrgGbCdInOrderByDepDtmDesc(String escrNo, String number, List<String> chrgGbs);

    Optional<TbEscrPayment> findTop1ByEscrNoAndDepAcctNoIsNotNull(String escrNo);
}