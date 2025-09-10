package com.bankle.common.repo;

import com.bankle.common.entity.TbCustAlramYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbCustAlramYnRepository extends JpaRepository<TbCustAlramYn, Long> {
    Optional<TbCustAlramYn> findByMembNoAndAlramGbCd(String membNo, String alramGbCd);

    List<TbCustAlramYn> findByMembNo(String membNo);
}