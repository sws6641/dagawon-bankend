package com.bankle.common.repo;

import com.bankle.common.entity.TbCustMaster;
import com.bankle.common.spec.TbCustMasterSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TbCustMasterRepository extends JpaRepository<TbCustMaster, String> {
    List<TbCustMaster> findByMembNmContainingAndStatCd(String membNm, String statCd);

    Optional<TbCustMaster> findByMembNo(String membNo);

    boolean existsByMembNo(String membNo);

    boolean existsByCiKeyAndStatCd(String cikey , String statCd);

    Optional<TbCustMaster> findByCiKeyAndStatCd(String ci, String statCd);

    //List<TbCustMaster> findAll(Specification<TbCustMasterSpec> spec);

    Page<TbCustMaster> findAll(Specification<TbCustMasterSpec> spec, Pageable pageable);

    Optional<TbCustMaster> findByMembNmAndBirthDtAndCphnNoAndStatCd(String membNm, String birthDt, String cphnNo , String statCd);
}