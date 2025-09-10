package com.bankle.common.asis.domain.entity;

import com.bankle.common.asis.domain.entity.ids.MembersHistoryId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "TEC_MEMB_H")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MembersHistory {

    @EmbeddedId
    private MembersHistoryId membersHistoryId;

    @Column(length = 100, nullable = false)
    private String pwd;

    @Column(name = "MEMB_DSC")
    private String membDsc;

    @Column(name = "MEMB_NM")
    private String membNm;

    @Column(name = "TELOP_CD")
    private String telopCd;

    @Column(name = "HP_NO")
    private String hpNo;

    @Column(name = "BIRTH_DT")
    private String birthDt;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "NTV_FRNR_DSC")
    private String ntvFrnrDsc;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ENTR_DT")
    private String entrDt;

    @Column(name = "FNL_CNCT_DT")
    private String fnlCnctDt;

    @Column(name = "FCM_ID")
    private String fcmId;

    private String udid;

    private String ci;

    private String di;

    @Column(name = "DVC_KND")
    private String dvcKnd;

    @Column(name = "ENTR_STC")
    private String entrStc;

    @Column(name = "ESCR_NOTI_YN")
    private String escrNotiYn;

    @Column(name = "PUSH_NOTI_YN")
    private String pushNotiYn;

    @Column(name = "KATOK_NOTI_YN")
    private String katokNotiYn;

    @Column(name = "MKT_NOTI_YN")
    private String mktNotiYn;

    @CreationTimestamp
    @Column(name = "REG_DTM", updatable = false)
    private LocalDateTime regDtm;

    public static MembersHistory of(Members members) {

        return MembersHistory.builder()
                .membersHistoryId(new MembersHistoryId(members.getMembNo(), LocalDateTime.now()))
                .pwd(members.getPwd())
                .membDsc(members.getMembDsc())
                .membNm(members.getMembNm())
                .telopCd(members.getTelopCd())
                .hpNo(members.getHpNo())
                .birthDt(members.getBirthDt())
                .sex(members.getSex())
                .email(members.getEmail())
                .entrDt(members.getEntrDt())
                .fnlCnctDt(members.getFnlCnctDt())
                .fcmId(members.getFcmId())
                .udid(members.getUdid())
                .ci(members.getCi())
                .di(members.getDi())
                .dvcKnd(members.getDvcKnd())
                .entrStc(members.getEntrStc())
                .entrDt(members.getEntrDt())
                .escrNotiYn("Y")
                .pushNotiYn("Y")
                .katokNotiYn("Y")
                .mktNotiYn("Y")
                .build();
    }
}
