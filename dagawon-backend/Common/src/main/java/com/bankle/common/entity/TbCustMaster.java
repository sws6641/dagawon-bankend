package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "TB_CUST_MASTER", schema = "anbu")
@EntityListeners(AuditingEntityListener.class)
public class TbCustMaster extends BaseTimeEntity {
    @Id
    @Size(max = 12)
    @Column(name = "MEMB_NO", nullable = false, length = 12)
    private String membNo;

    @Size(max = 50)
    @NotNull
    @Column(name = "MEMB_NM", nullable = false, length = 50)
    private String membNm;

    @Size(max = 2)
    @NotNull
    @Column(name = "STAT_CD", nullable = false, length = 2)
    private String statCd;

    @Size(max = 8)
    @NotNull
    @Column(name = "BIRTH_DT", nullable = false, length = 8)
    private String birthDt;

    @Size(max = 1)
    @NotNull
    @Column(name = "SEX_GB", nullable = false, length = 1)
    private String sexGb;

    @Size(max = 1)
    @NotNull
    @Column(name = "NTV_FRNR_GB_CD", nullable = false, length = 1)
    private String ntvFrnrGbCd;

    @Size(max = 50)
    @NotNull
    @Column(name = "PIN_NO", nullable = false, length = 50)
    private String pinNo;

    @Size(max = 100)
    @Column(name = "PATN_NO", length = 100)
    private String patnNo;

    @Size(max = 100)
    @NotNull
    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Size(max = 10)
    @Column(name = "ENTR_DT", length = 10)
    private String entrDt;

    @Size(max = 250)
    @Column(name = "FCM_ID", length = 250)
    private String fcmId;

    @Size(max = 250)
    @NotNull
    @Column(name = "CI_KEY", nullable = false, length = 250)
    private String ciKey;

    @Size(max = 250)
    @NotNull
    @Column(name = "DI_KEY", nullable = false, length = 250)
    private String diKey;

    @Size(max = 1)
    @NotNull
    @Column(name = "DVCE_KND", nullable = false, length = 1)
    private String dvceKnd;

    @Size(max = 150)
    @NotNull
    @Column(name = "CPHN_NO", nullable = false, length = 150)
    private String cphnNo;

    @NotNull
    @Column(name = "LOGN_FAIL_CNT", nullable = false)
    private Integer lognFailCnt;

    @Size(max = 50)
    @NotNull
    @Column(name = "DVCE_UNQ_NUM", nullable = false, length = 50)
    private String dvceUnqNum;

}