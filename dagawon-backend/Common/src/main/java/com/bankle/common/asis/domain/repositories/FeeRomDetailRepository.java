package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.FeeRomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeRomDetailRepository extends JpaRepository<FeeRomDetail, Long> {
    Optional<FeeRomDetail> findByEscrMKey(Long escrMKey);
}
