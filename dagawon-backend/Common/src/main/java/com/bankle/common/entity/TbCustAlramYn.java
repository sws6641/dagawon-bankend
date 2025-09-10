package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_CUST_ALRAM_YN")
public class TbCustAlramYn extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALRAM_YN_SEQ", nullable = false)
    private Long alramYnSeq;

    @Size(max = 50)
    @NotNull
    @Column(name = "MEMB_NO", nullable = false, length = 50)
    private String membNo;

    @Size(max = 1)
    @NotNull
    @Column(name = "ALRAM_YN", nullable = false, length = 1)
    private String alramYn;

    @Size(max = 2)
    @NotNull
    @Column(name = "ALRAM_GB_CD", nullable = false, length = 2)
    private String alramGbCd;

}