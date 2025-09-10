package com.bankle.common.repo;

import com.bankle.common.entity.TbAdminCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TbAdminCouponRepository extends JpaRepository<TbAdminCoupon, String> {

    Optional<TbAdminCoupon> findByCoupNo(String coupNo);
}