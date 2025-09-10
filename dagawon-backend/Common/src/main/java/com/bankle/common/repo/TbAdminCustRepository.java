package com.bankle.common.repo;

import com.bankle.common.entity.TbAdminCust;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TbAdminCustRepository extends JpaRepository<TbAdminCust, String> {
    Optional<TbAdminCust> findByLognId(String lognId);

}

