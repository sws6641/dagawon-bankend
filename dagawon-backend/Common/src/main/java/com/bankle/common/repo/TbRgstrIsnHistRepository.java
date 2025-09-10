package com.bankle.common.repo;

import com.bankle.common.entity.TbRgstrIsnHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TbRgstrIsnHistRepository extends JpaRepository<TbRgstrIsnHist, Long> {
    @Query(value = " SELECT * " +
            " FROM TB_RGSTR_ISN_HIST " +
            " WHERE RGSTR_UNQ_NO = :uniqNo " +
            " AND READ_DTM > DATE_ADD(NOW(), INTERVAL -1 HOUR) " +
            " ORDER BY RGSTR_ISN_SEQ DESC " +
            " LIMIT 1", nativeQuery = true)
    Optional<TbRgstrIsnHist> getRestrHistDtm(@Param("uniqNo") String uniqNo);
}