package com.bankle.common.asis.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "TEC_CD_M")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CodeMaster{

    @Id
    @Column(name = "GRP_CD")
    private String grpCd;

    @Column(name = "GRP_NM")
    private String grpNm;

    @Column(name = "GRP_DESC")
    private String grpDesc;

    @Column(name = "USE_YN")
    private String useYn;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "GRP_CD")
    private List<CodeDetail> codeDetails;
}
