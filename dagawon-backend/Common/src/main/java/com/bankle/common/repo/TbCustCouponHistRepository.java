package com.bankle.common.repo;

import com.bankle.common.entity.TbCustCouponHist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbCustCouponHistRepository extends JpaRepository<TbCustCouponHist, String> {

    List<TbCustCouponHist> findByMembNoAndUseYn(String membNo, String useYn);

    Optional<TbCustCouponHist> findByMembNoAndCoupNo(String membNo, String coupNo);


    List<TbCustCouponHist> findAll(Specification<TbCustCouponHist> specification);

    Page<TbCustCouponHist> findAll(Specification<TbCustCouponHist> specification, Pageable pageable);
}