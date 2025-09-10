package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TEC_NOTI_M")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotiMaster extends BaseTimeEntityByAllDtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTI_M_KEY")
    private Long notiMKey;

    @Column(name = "NOTI_DSC")
    private String notiDsc;

    @Column(name = "NOTI_CTG_DSC")
    private String notiCtgDsc;

    @Column(name = "NOTI_TTL")
    private String notiTtl;

    @Column(name = "NOTI_CNTS")
    private String notiCnts;

    @Column(name = "ATTH_FL_M_KEY")
    private Long atthFlMKey;

    @Column(name = "EMGC_YN")
    private String emgcYn;

    @Column(name = "REG_MNGR_ID")
    private String regMngrId;

    @Column(name = "CHG_MNGR_ID")
    private String chgMngrId;

    @Transient
    private FlMaster files;
}
