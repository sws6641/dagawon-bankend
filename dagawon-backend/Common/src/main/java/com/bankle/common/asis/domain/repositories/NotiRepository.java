package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.NotiMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotiRepository extends JpaRepository<NotiMaster, Long> {
    Optional<NotiMaster> findByNotiMKey(Long notiMKey);
}
