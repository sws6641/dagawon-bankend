package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.VirtualAccountAssignDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VirtualAccountAssignDetailRepository extends JpaRepository<VirtualAccountAssignDetail, Long> {

    Optional<VirtualAccountAssignDetail> findByescrMKeyAndUseYn(Long escrMKey, String useYn);
}
