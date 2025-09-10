package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "TEC_MEMB_M")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@ToString
public class Members extends BaseTimeEntityByAllDtm {

    @Id
    @Column(name = "MEMB_ID")
    @NotEmpty
    @Size(max = 25)
    private String membNo;

    @NotEmpty
    @Size(max = 100)
    @JsonIgnore
    private String pwd;

    @Column(name = "MEMB_DSC")
    @ColumnDefault("1") //개인
    private String membDsc;

    @Column(name = "MEMB_NM")
    private String membNm;

    @Column(name = "TELOP_CD")
    private String telopCd;

    @Column(name = "HP_NO")
    private String hpNo;

    @Column(name = "BIRTH_DT")
    @Size(max = 8)
    private String birthDt;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "NTV_FRNR_DSC")
    private String ntvFrnrDsc;

    @Column(name = "EMAIL")
    @Email
    private String email;

    @Column(name = "ENTR_DT")
    private String entrDt;

    @Column(name = "FNL_CNCT_DT")
    private String fnlCnctDt;

    @Column(name = "FCM_ID")
    private String fcmId;

    private String udid;

    private String ci;

    private String di;

    @Column(name = "DVC_KND")
    private String dvcKnd;

    @Column(name = "ENTR_STC")
    private String entrStc;

    @Column(name = "ESCR_NOTI_YN")
    private String escrNotiYn;

    @Column(name = "PUSH_NOTI_YN")
    private String pushNotiYn;

    @Column(name = "KATOK_NOTI_YN")
    private String katokNotiYn;

    @Column(name = "MKT_NOTI_YN")
    private String mktNotiYn;

    // 2022.07.22 추가
    @Transient
    private String existEntrYn; // 기가입여부
}
