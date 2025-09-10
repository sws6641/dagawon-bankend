package com.bankle.common.repo;

import com.bankle.common.entity.TbCustAgreementHist;
import com.bankle.common.entity.TbCustAgreementHistId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbCustAgreementHistRepository extends JpaRepository<TbCustAgreementHist, TbCustAgreementHistId> {
}