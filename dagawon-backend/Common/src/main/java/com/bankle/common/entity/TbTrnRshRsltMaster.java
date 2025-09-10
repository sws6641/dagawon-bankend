package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_TRN_RSH_RSLT_MASTER")
public class TbTrnRshRsltMaster extends BaseTimeEntity {
    @Id
    @Column(name = "RSH_RSLT_SEQ", nullable = false)
    private Long id;

    @Column(name = "ESCR_NO")
    private Long escrNo;

    @Column(name = "RGSTR_CHG_RSH_EPSD")
    private Long rgstrChgRshEpsd;

    @Size(max = 2)
    @Column(name = "RGSTR_CHG_GB_CD", length = 2)
    private String rgstrChgGbCd;

    @Column(name = "RGSTR_CHG_INFO_CNT")
    private Long rgstrChgInfoCnt;

    @Size(max = 5000)
    @Column(name = "RGSTR_CHG_CNFM_CNTS", length = 5000)
    private String rgstrChgCnfmCnts;

    @Size(max = 8)
    @Column(name = "RGSTR_CHG_CNFM_DT", length = 8)
    private String rgstrChgCnfmDt;

    @Size(max = 4000)
    @Column(name = "RMK", length = 4000)
    private String rmk;

    @Size(max = 2)
    @Column(name = "ABNL_EANE_CD", length = 2)
    private String abnlEaneCd;

    @Size(max = 5000)
    @Column(name = "ABNL_EANE_RSN", length = 5000)
    private String abnlEaneRsn;
}