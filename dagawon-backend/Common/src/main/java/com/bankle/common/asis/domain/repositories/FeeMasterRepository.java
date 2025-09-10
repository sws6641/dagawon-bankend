package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.FeeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FeeMasterRepository extends JpaRepository<FeeMaster, Long> {

    @Query("select f.feeRt from FeeMaster f where :#{#escrTrgtAmt} between f.frAmt and f.toAmt")
    Optional<BigDecimal> findFeeRtByEscrTrgtAmt(Long escrTrgtAmt);
}
