package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "TET_ESCR_NOTI_D")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractEscrowNoti extends BaseTimeEntityByAllDtm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESCR_NOTI_D_KEY")
    private Long escrNotiDKey;

    @Column(name = "NOTI_DTM")
    private String notiDtm;

    @Column(name = "MSG_PRTT_I_KEY")
    private int msgPrttIKey;

    @Column(name = "NOTI_TTL")
    private String notiTtl;

    @Column(name = "NOTI_CNTS")
    private String notiCnts;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;
}
