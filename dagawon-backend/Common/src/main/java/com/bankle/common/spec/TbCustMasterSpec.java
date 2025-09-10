package com.bankle.common.spec;

import org.springframework.data.jpa.domain.Specification;

public class TbCustMasterSpec {

    public static Specification<TbCustMasterSpec> equalsMembNm(String membNm) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("membNm"), membNm);
    }
}
