package com.bankle.common.repo;

import com.bankle.common.entity.TbRgstrCaseInfoHist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbRgstrCaseInfoHistRepository extends JpaRepository<TbRgstrCaseInfoHist, Long> {
    Optional<TbRgstrCaseInfoHist> findTop1ByEscrNoOrderBySrchDtDescCaseNoSeqDesc(String escrNo);

    Optional<TbRgstrCaseInfoHist> findByRgstrCaseNo(Long rgstrCaseNo);

    Page<TbRgstrCaseInfoHist> findByEscrNoOrderByCrtDtmDesc(String escrNo, Pageable pageable);

    boolean existsByEscrNo(String escrNo);
}