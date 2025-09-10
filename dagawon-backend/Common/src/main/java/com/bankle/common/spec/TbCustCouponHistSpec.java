package com.bankle.common.spec;

import com.bankle.common.entity.TbCustCouponHist;
import org.springframework.data.jpa.domain.Specification;

public class TbCustCouponHistSpec {


    public static Specification<TbCustCouponHist> equalsMembNo(String membNo) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("membNo"), membNo);
    }

    public static Specification<TbCustCouponHist> greaterThanOrEqualToExpireDtm(String expireDtm) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.greaterThanOrEqualTo(root.get("expireDtm"), expireDtm);
    }

    public static Specification<TbCustCouponHist> equalsUseYn(String useYn) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("useYn"), useYn);
    }

    public static Specification<TbCustCouponHist> lessThanExpireDtm(String expireDtm) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.lessThan(root.get("expireDtm"), expireDtm);
    }
}
