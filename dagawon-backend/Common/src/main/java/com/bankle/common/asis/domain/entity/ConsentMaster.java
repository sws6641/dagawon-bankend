package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "TEC_ENTR_ASNT_M")
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ConsentMaster extends BaseTimeEntityByAllDtm {

    @Id
    @Column(name = "ENTR_ASNT_CD")
    private String entrAsntCd;

    @Column(name = "ENTR_ASNT_NM")
    private String entrAsntNm;

    @Column(name = "ESNTL_YN")
    private String esntlYn;

    @Column(name = "USE_YN")
    private String useYn;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ENTR_ASNT_CD")
    private List<ConsentDetail> consentDetails;

}
