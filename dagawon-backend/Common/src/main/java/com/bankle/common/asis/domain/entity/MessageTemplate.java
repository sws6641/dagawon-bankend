package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "TEC_MSG_PRTT_I")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageTemplate extends BaseTimeEntityByAllDtm {

    @Id
    @Column(name = "MSG_PRTT_I_KEY")
    private Integer msgPrttIKey;

    @Column(name = "KAKAO_MSG")
    private String kakaoMsg;

    @Column(name = "PUSH_MSG")
    private String pushMsg;

    @Column(name = "TRNS_STP")
    private String trnsStp;

    @Column(name = "TRNS_TRGT")
    private String trnsTrgt;

    @Column(name = "TRNS_MMT")
    private String trnsMmt;

    @Column(name = "RMK")
    private String rmk;

    @Column(name = "REG_MNGR_ID")
    private String regMngrId;

    @Column(name = "CHG_MNGR_ID")
    private String chgMngrId;

}
