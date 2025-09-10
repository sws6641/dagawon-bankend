package com.bankle.common.repo;

import com.bankle.common.entity.TbMesgTplt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TbMesgTpltRepository extends JpaRepository<TbMesgTplt, Long>, JpaSpecificationExecutor<TbMesgTplt> {
    Optional<TbMesgTplt> findByTpltSeq(String tpltSeq);
}