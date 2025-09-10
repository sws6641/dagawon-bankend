package com.bankle.common.repo;

import com.bankle.common.entity.TbBoardMaster;
import com.bankle.common.entity.TbEscrFeeCalc;
import com.bankle.common.spec.TbBoardMasterSpec;
import com.bankle.common.spec.TbEscrFeeCalcSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbBoardMasterRepository extends JpaRepository<TbBoardMaster, Long> {

    List<TbBoardMaster> findAll(Specification<TbBoardMaster> specification);

    Page<TbBoardMaster> findAll(Specification<TbBoardMaster> specification, Pageable pageable);
}