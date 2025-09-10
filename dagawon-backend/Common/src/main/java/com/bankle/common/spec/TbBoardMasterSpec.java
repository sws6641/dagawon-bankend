package com.bankle.common.spec;

import com.bankle.common.entity.TbBoardMaster;
import org.springframework.data.jpa.domain.Specification;

public class TbBoardMasterSpec {

    public static Specification<TbBoardMaster> equalsId(long seq) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("seq"), seq);
    }

    public static Specification<TbBoardMaster> equalsPtGbCd(String ptGbCd) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("ptGbCd"), ptGbCd);
    }

    public static Specification<TbBoardMaster> equalsPtTrgtGbCd(String ptTrgtGbCd) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("ptTrgtGbCd"), ptTrgtGbCd);
    }

    public static Specification<TbBoardMaster> equalsOpnYn(String opnYn) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("opnYn"), opnYn);
    }

    public static Specification<TbBoardMaster> greaterTnEqlToPtStDt(String ptStDt) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.greaterThanOrEqualTo(root.get("ptStDt"), ptStDt);
    }

    public static Specification<TbBoardMaster> lessEqlTnToPtEndDt(String ptEndDt) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.lessThanOrEqualTo(root.get("ptEndDt"), ptEndDt);
    }

    public static Specification<TbBoardMaster> equalsDeptNo(int deptNo) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("deptNo"), deptNo);
    }

    public static Specification<TbBoardMaster> equalsParentSeq(long parentSeq) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("parentSeq"), parentSeq);
    }

    public static Specification<TbBoardMaster> equalsSort(long sort) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("sort"), sort);
    }

    public static Specification<TbBoardMaster> notEqualsSort(long sort) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.notEqual(root.get("sort"), sort);
    }
}



