package com.bankle.common.spec;

import com.bankle.common.entity.TbMesgSendHist;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TbMesgSendHistSpec {

    public static Specification<TbMesgSendHist> likeAddreNm(String addreNm) {
        return new Specification<TbMesgSendHist>() {
            @Override
            public Predicate toPredicate(Root<TbMesgSendHist> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("addre"), "%" + addreNm + "%");
            }
        };
    }

    public static Specification<TbMesgSendHist> equalAddreNm(String addreNm) {
        return new Specification<TbMesgSendHist>() {
            @Override
            public Predicate toPredicate(Root<TbMesgSendHist> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("addreNm"), addreNm);
            }
        };
    }
}
