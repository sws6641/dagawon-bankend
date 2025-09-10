package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TB_CUST_AGREEMENT")
public class TbCustAgreement extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false)
    private Long id;

    @Size(max = 8)
    @NotNull
    @Column(name = "REFORM_DT", nullable = false, length = 8)
    private String reformDt;

    @Size(max = 1)
    @NotNull
    @Column(name = "DEFAULT_YN", nullable = false, length = 1)
    private String defaultYn;

    @Size(max = 2)
    @NotNull
    @Column(name = "AGREE_CD", nullable = false, length = 2)
    private String agreeCd;

    @Size(max = 2)
    @NotNull
    @Column(name = "AGREE_DTL_CD", nullable = false, length = 2)
    private String agreeDtlCd;

    @Size(max = 2)
    @NotNull
    @Column(name = "FORM_CD", nullable = false, length = 2)
    private String formCd;

    @Lob
    @Column(name = "AGREE_TEXT")
    private String agreeText;

    @Size(max = 1000)
    @Column(name = "AGREE_URL", length = 1000)
    private String agreeUrl;

    @Size(max = 1000)
    @Column(name = "AGREE_FILE", length = 1000)
    private String agreeFile;

    @NotNull
    @Column(name = "SORT", nullable = false)
    private Long sort;

    @Size(max = 1)
    @NotNull
    @Column(name = "DEL_YN", nullable = false, length = 1)
    private String delYn;
}