package com.bankle.common.repo;

import com.bankle.common.entity.TbCommAppVrHist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TbCommAppVrHistRepository extends JpaRepository<TbCommAppVrHist, Long> {
    Optional<TbCommAppVrHist> findTop1ByOrderByCrtDtmDesc();
}