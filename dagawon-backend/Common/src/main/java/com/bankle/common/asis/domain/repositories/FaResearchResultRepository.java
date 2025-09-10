package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.FaResearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaResearchResultRepository extends JpaRepository<FaResearchResult, Long> {

//    List<FaResearchResult> findByEscrMKeyOrderByRegDtmDesc(Long escrMKey);
}
