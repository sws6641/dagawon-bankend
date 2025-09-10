package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "TB_RGSTR_CASE_INFO")
@ToString
public class TbRgstrCaseInfo extends BaseTimeEntity {
    @Id
    @Column(name = "RGSTR_CASE_NO", nullable = false)
    private Long rgstrCaseNo;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 8)
    @Column(name = "SRCH_DT", length = 8)
    private String srchDt;

    @Lob
    @Column(name = "RGSTR_READ_CNTS")
    private String rgstrReadCnts;

    @Size(max = 8)
    @Column(name = "ACPT_DT", length = 8)
    private String acptDt;

    @Size(max = 20)
    @Column(name = "ACPT_NO", length = 20)
    private String acptNo;

    @Size(max = 100)
    @Column(name = "CPT_REGO", length = 100)
    private String cptRego;

    @Size(max = 100)
    @Column(name = "REGO_DEPT", length = 100)
    private String regoDept;

    @Size(max = 100)
    @Column(name = "LOTNUM_ADDR", length = 100)
    private String lotnumAddr;

    @Size(max = 5000)
    @Column(name = "RGSTR_PRPS", length = 5000)
    private String rgstrPrps;

    @Size(max = 100)
    @Column(name = "PROC_STAT", length = 100)
    private String procStat;

    @Size(max = 1)
    @Column(name = "CHG_YN", length = 1)
    private String chgYn;

}