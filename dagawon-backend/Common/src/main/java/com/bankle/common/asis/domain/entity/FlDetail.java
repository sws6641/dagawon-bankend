package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "TEC_FL_D")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlDetail extends BaseTimeEntityByAllDtm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FL_D_KEY")
    @JsonIgnore
    private Long flDKey;

    @Column(name = "FL_NM")
    private String flNm;

    @Column(name = "FL_OGN_NM")
    private String flOgnNm;

    @Column(name = "FL_EXT")
    private String flExt;

    @Column(name = "FL_PTH")
    private String flPth;

    @Column(name = "FL_SZ")
    private Long flSz;

    @Column(name = "FL_M_KEY")
    private Long flMKey;
}
