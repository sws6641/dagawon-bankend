package com.bankle.common.spec;

import org.springframework.data.jpa.domain.Specification;

public class TbEscrMasterSpec {

    public static Specification<TbEscrMasterSpec> equalsEscrNo(String escrNo) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("escrNo"), escrNo);
    }
}
