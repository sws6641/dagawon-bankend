package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.FlDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlDetailRepository extends JpaRepository<FlDetail, Long> {

}
