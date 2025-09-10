package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_CUST_AGREEMENT_HIST")
public class TbCustAgreementHist extends BaseTimeEntity {
    @EmbeddedId
    private TbCustAgreementHistId id;

    @Size(max = 1)
    @NotNull
    @Column(name = "DEFAULT_YN", nullable = false, length = 1)
    private String defaultYn;

    @Size(max = 1)
    @NotNull
    @Column(name = "AGREE_YN", nullable = false, length = 1)
    private String agreeYn;

    @Size(max = 8)
    @NotNull
    @Column(name = "AGREE_DT", nullable = false, length = 8)
    private String agreeDt;
}