package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.FlMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlMasterRepository extends JpaRepository<FlMaster, Long> {


}
