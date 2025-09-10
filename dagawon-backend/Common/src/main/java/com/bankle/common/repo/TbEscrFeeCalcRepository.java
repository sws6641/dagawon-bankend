package com.bankle.common.repo;

import com.bankle.common.entity.TbEscrFeeCalc;
import com.bankle.common.spec.TbEscrFeeCalcSpec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbEscrFeeCalcRepository extends JpaRepository<TbEscrFeeCalc, Long> {

    List<TbEscrFeeCalc> findAll(Specification<TbEscrFeeCalcSpec> specification);
}