package com.bankle.common.spec;

import com.bankle.common.entity.TbCustAgreement;
import org.springframework.data.jpa.domain.Specification;

public class TbCustAgreementSpec {
    /**
     * equal reformDt
     */
    public static Specification<TbCustAgreement> equalReformDt(String reformDt) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reformDt"), reformDt));
    }

    /**
     * equal agreeCd
     */
    public static Specification<TbCustAgreement> equalAgreeCd(String agreeCd) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("agreeCd"), agreeCd);
    }

    /**
     * equal agreeDtlCd
     */
    public static Specification<TbCustAgreement> equalAgreeDtlCd(String agreeDtlCd) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("agreeDtlCd"), agreeDtlCd);
    }

    /**
     * equal delYn
     * */
    public static Specification<TbCustAgreement> equalDelYn(String delYn) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("delYn"), delYn);
    }


}
