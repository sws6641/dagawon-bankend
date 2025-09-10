package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.RgstrIcdnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RgstIncdntRepository extends JpaRepository<RgstrIcdnt, Long> {

    List<RgstrIcdnt> findByEscrMKeyOrderByAcptDtDesc(Long escrMKey);
}
