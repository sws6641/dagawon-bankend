package com.bankle.common.asis.infra;

import com.bankle.common.asis.domain.entity.FlMaster;
import com.bankle.common.asis.domain.entity.NotiMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDto {
    private Long notiMKey;
    private String notiDsc;
    private String notiCtgDsc;
    private String notiTtl;
    private String notiCnts;
    private String emgcYn;
    private String regMngrId;
    private String chgMngrId;
    private LocalDateTime regDtm;
    private LocalDateTime chgDtm;

    private FlMaster files;

    public static BoardResponseDto of(NotiMaster noti){
        return BoardResponseDto.builder()
                .notiMKey(noti.getNotiMKey())
                .notiDsc(noti.getNotiDsc())
                .notiCtgDsc(noti.getNotiCtgDsc())
                .notiTtl(noti.getNotiTtl())
                .notiCnts(noti.getNotiCnts())
                .emgcYn(noti.getEmgcYn())
                .regMngrId(noti.getRegMngrId())
                .regDtm(noti.getCrtDtm())
                .chgMngrId(noti.getChgMngrId())
                .chgDtm(noti.getChgDtm())
                .build();
    }
}
