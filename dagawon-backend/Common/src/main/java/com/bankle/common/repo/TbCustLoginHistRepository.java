package com.bankle.common.repo;

import com.bankle.common.entity.TbCustLoginHist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbCustLoginHistRepository extends JpaRepository<TbCustLoginHist, Long> {
}