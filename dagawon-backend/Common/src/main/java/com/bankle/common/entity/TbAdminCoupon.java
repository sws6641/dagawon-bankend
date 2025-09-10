package com.bankle.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_ADMIN_COUPON")
public class TbAdminCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 100)
    @Column(name = "COUP_NO", nullable = false, length = 100)
    private String coupNo;

    @Size(max = 100)
    @NotNull
    @Column(name = "COUP_NM", nullable = false, length = 100)
    private String coupNm;

    @Size(max = 2)
    @NotNull
    @Column(name = "COUP_TP_CD", nullable = false, length = 2)
    private String coupTpCd;

    @Column(name = "DISCNT_VAL", precision = 6, scale = 2)
    private BigDecimal disCntVal;

    @Size(max = 8)
    @NotNull
    @Column(name = "COUP_INPUT_DT", nullable = false, length = 8)
    private String coupInputDt;

    @Size(max = 8)
    @NotNull
    @Column(name = "COUP_ED_DT", nullable = false, length = 8)
    private String coupEdDt;

    @Size(max = 1)
    @NotNull
    @Column(name = "ABLE_YN", nullable = false, length = 1)
    private String ableYn;

    @NotNull
    @Column(name = "CRT_DTM", nullable = false)
    private Instant crtDtm;

    @Size(max = 12)
    @NotNull
    @Column(name = "CRT_MEMB_NO", nullable = false, length = 12)
    private String crtMembNo;

    @NotNull
    @Column(name = "CHG_DTM", nullable = false)
    private Instant chgDtm;

    @Size(max = 12)
    @NotNull
    @Column(name = "CHG_MEMB_NO", nullable = false, length = 12)
    private String chgMembNo;

}