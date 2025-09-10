package com.bankle.common.repo;

import com.bankle.common.entity.TbCustAgreement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbCustAgreementRepository extends JpaRepository<TbCustAgreement, Long> {

    List<TbCustAgreement> findByDefaultYnInAndDelYnAndAgreeCdNotOrderByAgreeCdAscSortAsc(List<String> defaultYn, String delYn, String agreeCd);


    List<TbCustAgreement> findByAgreeCdAndDefaultYnInAndDelYnOrderByAgreeCdAscSortAsc(String agreeCd, List<String> defaultYn, String delYn);

    List<TbCustAgreement> findAll(Specification<TbCustAgreement> spec);

    Page<TbCustAgreement> findAll(Specification<TbCustAgreement> spec, Pageable pageable);

    Optional<TbCustAgreement> findByReformDtAndAgreeCdAndAgreeDtlCd(String reformDt, String agreeCd, String agreeDtlCd);

    List<TbCustAgreement> findByAgreeCd(String agreeCd);
}