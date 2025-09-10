package com.bankle.common.spec;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class TbEscrFeeCalcSpec {

    public static Specification<TbEscrFeeCalcSpec> lessThan(BigDecimal grntAmt) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.lessThan(root.get("startAmt"), grntAmt);
    }
    public static Specification<TbEscrFeeCalcSpec> greaterThanOrEqualTo(BigDecimal grntAmt) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.greaterThanOrEqualTo(root.get("endAmt"), grntAmt);
    }

    public static Specification<TbEscrFeeCalcSpec> equalsPrdtTpCd(String prdtTpCd) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("prdtTpCd"), prdtTpCd);
    }

    public static Specification<TbEscrFeeCalcSpec> equalsprdtDtlGbCd(String prdtDtlGbCd) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("prdtDtlGbCd"), prdtDtlGbCd);
    }
}
