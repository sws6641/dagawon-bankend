package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ConsentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsentMasterRepository extends JpaRepository<ConsentMaster, String> {

    Optional<ConsentMaster> findById(String entrAsntCd);
}
