package com.bankle.common.spec;

import com.bankle.common.entity.TbMesgTplt;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TbMesgTpltSpec {

    public static Specification<TbMesgTplt> equalTpltGbCd(String tpltGbCd) {
        return new Specification<TbMesgTplt>() {
            @Override
            public Predicate toPredicate(Root<TbMesgTplt> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("tpltGbCd"), tpltGbCd);
            }
        };
    }

    public static Specification<TbMesgTplt> equalTpltSeq(String tpltSeq) {
        return new Specification<TbMesgTplt>() {
            @Override
            public Predicate toPredicate(Root<TbMesgTplt> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("tpltSeq"), tpltSeq);
            }
        };
    }

    public static Specification<TbMesgTplt> equalTpltKndGbCd(String tpltKndGbCd) {
        return new Specification<TbMesgTplt>() {
            @Override
            public Predicate toPredicate(Root<TbMesgTplt> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("tpltKndGbCd"), tpltKndGbCd);
            }
        };
    }

    public static Specification<TbMesgTplt> likeTpltSeq(String tpltSeq) {
        return new Specification<TbMesgTplt>() {
            @Override
            public Predicate toPredicate(Root<TbMesgTplt> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("addre"), "%" + tpltSeq + "%");
            }
        };
    }
}
