package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TB_BOARD_MASTER")
public class TbBoardMaster extends BaseTimeEntity {
    @Id
    @Column(name = "SEQ", nullable = false)
    private Long seq;

    @Size(max = 2)
    @Column(name = "PT_GB_CD", length = 2)
    private String ptGbCd;

    @Size(max = 2)
    @Column(name = "PT_TRGT_GB_CD", length = 2)
    private String ptTrgtGbCd;

    @Size(max = 1)
    @Column(name = "OPN_YN", length = 1)
    private String opnYn;

    @Size(max = 8)
    @Column(name = "PT_ST_DT", length = 8)
    private String ptStDt;

    @Size(max = 8)
    @Column(name = "PT_END_DT", length = 8)
    private String ptEndDt;

    @Size(max = 100)
    @Column(name = "PT_TTL", length = 100)
    private String ptTtl;

    @Size(max = 5000)
    @Column(name = "PT_CNTS", length = 5000)
    private String ptCnts;

    @Column(name = "DEPT_NO")
    private int deptNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SEQ")
    @NotFound(action = NotFoundAction.IGNORE)
    private TbBoardMaster parentSeq;

    @Size(max = 1)
    @Column(name = "DEL_YN", length = 1)
    private String delYn;

    @Column(name = "SORT")
    private Long sort;

    @OneToMany(mappedBy = "parentSeq", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("deptNo asc")
    @JsonManagedReference
    private List<TbBoardMaster> child = new ArrayList<>();
}