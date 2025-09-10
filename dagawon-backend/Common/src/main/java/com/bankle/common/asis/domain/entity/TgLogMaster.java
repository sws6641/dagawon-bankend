package com.bankle.common.asis.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "TEI_TG_LOG_M")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TgLogMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TG_LOG_M_KEY")
    private Long tgLogMKey;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;

    @Column(name = "TG_CD")
    private String tgCd;

    @Column(name = "LK_SND_TG")
    private String lkSndTg;

    @Column(name = "LK_SND_DTM")
    private LocalDateTime lkSndDtm;

    @Column(name = "LK_RCV_TG")
    private String lkRcvTg;

    @Column(name = "LK_RCV_DTM")
    private LocalDateTime lkRcvDtm;

    @Column(name = "SND_RCV_RSLT")
    private String sndRcvRslt;

    @Column(name = "SND_RCV_RSLT_MSG")
    private String sndRcvRsltMsg;

}
