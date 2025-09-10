package com.bankle.common.spec;

import com.bankle.common.entity.TbEscrRltnsList;
import com.bankle.common.entity.TbMesgSendHist;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TbEscrRltnsListSpec {


    public static Specification<TbEscrRltnsList> equalescrNo(String escrNo) {
        return new Specification<TbEscrRltnsList>() {
            @Override
            public Predicate toPredicate(Root<TbEscrRltnsList> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("escrNo"), escrNo);
            }
        };
    }

}
