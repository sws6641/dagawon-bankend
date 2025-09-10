package com.bankle.common.asis.infra;

import com.bankle.common.asis.domain.entity.FaResearchResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaResearchResultDto {
    private Long faRschRsltIKey;
    private Double rgstChgRschEpsd;
    private String rgstChgDsc;
    private String rgstChgDscValue;
    private Double rgstChgBrkdnRno;
    private String rgstChgCnfmCnts;
    private String rgstChgCnfmDt;
    private String rmkFct;
    private String strnEaneCd;
    private String strnEaneCdValue;
    private String strnEaneRsn;
    private Long escrMKey;
    private LocalDateTime regDtm;
    private LocalDateTime chgDtm;

    public static FaResearchResultDto of(FaResearchResult faResearchResult){
        return FaResearchResultDto.builder()
                .faRschRsltIKey(faResearchResult.getFaRschRsltIKey())
                .rgstChgRschEpsd(faResearchResult.getRgstChgRschEpsd())
                .rgstChgDsc(faResearchResult.getRgstChgDsc())
                .rgstChgBrkdnRno(faResearchResult.getRgstChgBrkdnRno())
                .rgstChgCnfmCnts(faResearchResult.getRgstChgCnfmCnts())
                .rgstChgCnfmDt(faResearchResult.getRgstChgCnfmDt())
                .rmkFct(faResearchResult.getRmkFct())
                .strnEaneCd(faResearchResult.getStrnEaneCd())
                .strnEaneRsn(faResearchResult.getStrnEaneRsn())
                .escrMKey(faResearchResult.getEscrMKey())
                .regDtm(faResearchResult.getCrtDtm())
                .chgDtm(faResearchResult.getChgDtm())
                .build();
    }
}
