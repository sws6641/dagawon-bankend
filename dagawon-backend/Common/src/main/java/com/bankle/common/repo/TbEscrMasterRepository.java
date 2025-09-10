package com.bankle.common.repo;

import com.bankle.common.entity.TbEscrMaster;
import com.bankle.common.spec.TbEscrMasterSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TbEscrMasterRepository extends JpaRepository<TbEscrMaster, String> {

    List<TbEscrMaster> findAll(Specification<TbEscrMasterSpec> spec);

    Optional<TbEscrMaster> findByEscrNo(String escrNo);

    List<TbEscrMaster> findAllByCrtMembNoAndEscrDtlProgCdNotInOrderByEscrNoDesc(String membNo, List<String> escrDtlProgCd);

    Page<TbEscrMaster> findAll(Specification<TbEscrMasterSpec> spec, Pageable pageable);

    boolean existsByEscrNo(String escrNo);

    boolean existsByCrtMembNoAndEscrDtlProgCdNotIn(String membNo, List<String> escrDtlProgCd);

    List<TbEscrMaster> findByCrtMembNoAndEscrProgCdIn(String crtMembNo, List<String> escrProgCd);

    Optional<TbEscrMaster> findTopByCrtMembNoOrderByCrtDtmDesc(String membNo);
}