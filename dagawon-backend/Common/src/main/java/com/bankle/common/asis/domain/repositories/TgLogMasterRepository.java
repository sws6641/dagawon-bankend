package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.TgLogMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TgLogMasterRepository extends JpaRepository<TgLogMaster, Long> {
}
