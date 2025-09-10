package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_ADMIN_CUST")
public class TbAdminCust extends BaseTimeEntity {
    @Id
    @Size(max = 100)
    @Column(name = "LOGN_ID", nullable = false, length = 100)
    private String lognId;

    @Size(max = 100)
    @Column(name = "PWD", length = 100)
    private String pwd;

    @Size(max = 2)
    @Column(name = "MNGR_GB_CD", length = 2)
    private String mngrGbCd;

    @Size(max = 100)
    @Column(name = "MNGR_NM", length = 100)
    private String mngrNm;

    @Column(name = "LOGN_FAIL_CNT", precision = 5)
    private BigDecimal lognFailCnt;

    @Size(max = 50)
    @Column(name = "CPHN_NO", length = 50)
    private String cphnNo;
}