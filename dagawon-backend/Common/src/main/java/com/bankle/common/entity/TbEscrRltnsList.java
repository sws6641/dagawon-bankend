package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_ESCR_RLTNS_LIST")
public class TbEscrRltnsList extends BaseTimeEntity {
    @Id
    @Column(name = "ESCR_RLTNS_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long escrRltnsSeq;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 2)
    @Column(name = "RLTNS_GB_CD", length = 2)
    private String rltnsGbCd;

    @Size(max = 100)
    @Column(name = "RLTNS_NM", length = 100)
    private String rltnsNm;

    @Size(max = 20)
    @Column(name = "RLTNS_CPHN_NO", length = 20)
    private String rltnsCphnNo;

    @Size(max = 8)
    @Column(name = "RLTNS_BIRTH_DT", length = 8)
    private String rltnsBirthDt;

    @Size(max = 2)
    @Column(name = "RLTNS_SEX_GB", length = 2)
    private String rltnsSexGb;

    @Size(max = 100)
    @Column(name = "RLTNS_EMAIL", length = 100)
    private String rltnsEmail;

    @Size(max = 3)
    @Column(name = "RLTNS_BNK_CD", length = 3)
    private String rltnsBnkCd;

    @Size(max = 30)
    @Column(name = "RLTNS_ACCT_NO", length = 30)
    private String rltnsAcctNo;

    @Size(max = 8)
    @Column(name = "TR_AGREE_DT", length = 8)
    private String trAgreeDt;

    @Size(max = 1)
    @Column(name = "TR_AGREE_YN", length = 1)
    private String trAgreeYn;

    @Size(max = 32)
    @Column(name = "KAKAO_RECEIPT_ID", length = 32)
    private String kakaoReceiptId;

    @Column(name = "KAKAO_STATE")
    private Integer kakaoState;

    @Size(max = 14)
    @Column(name = "KAKAO_VERIFY_DT", length = 14)
    private String kakaoVerifyDt;

    @Size(max = 14)
    @Column(name = "KAKAO_EXPIRE_DT", length = 14)
    private String kakaoExpireDt;

    @Size(max = 14)
    @Column(name = "KAKAO_COMPLETE_DT", length = 14)
    private String kakaoCompleteDt;

    @Size(max = 14)
    @Column(name = "KAKAO_VIEW_DT", length = 14)
    private String kakaoViewDt;

    @Size(max = 14)
    @Column(name = "KAKAO_REQUEST_DT", length = 14)
    private String kakaoRequestDt;

}