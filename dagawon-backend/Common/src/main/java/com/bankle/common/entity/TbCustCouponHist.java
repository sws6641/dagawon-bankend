package com.bankle.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_CUST_COUPON_HIST")
public class TbCustCouponHist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 20)
    @Column(name = "SEQ", nullable = false, length = 20)
    private String seq;

    @Size(max = 12)
    @Column(name = "MEMB_NO", length = 12)
    private String membNo;

    @Size(max = 100)
    @Column(name = "COUP_NO", length = 100)
    private String coupNo;

    @Size(max = 100)
    @Column(name = "COUP_NM", length = 100)
    private String coupNm;

    @Size(max = 2)
    @NotNull
    @Column(name = "COUP_TP_CD", nullable = false, length = 2)
    private String coupTpCd;

    @Column(name = "DISCNT_VAL", precision = 6, scale = 2)
    private BigDecimal disCntVal;

    @Size(max = 8)
    @Column(name = "EXPIRE_DTM", length = 8)
    private String expireDtm;

    @Size(max = 1)
    @Column(name = "USE_YN", length = 1)
    private String useYn;

    @Column(name = "USE_DTM")
    private Instant useDtm;

    @Column(name = "CRT_DTM")
    private Instant crtDtm;

    @Size(max = 12)
    @Column(name = "CRT_MEMB_NO", length = 12)
    private String crtMembNo;

    @Column(name = "CHG_DTM")
    private Instant chgDtm;

    @Size(max = 12)
    @Column(name = "CHG_MEMB_NO", length = 12)
    private String chgMembNo;

}