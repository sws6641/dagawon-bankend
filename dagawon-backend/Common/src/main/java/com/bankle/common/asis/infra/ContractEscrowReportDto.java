package com.bankle.common.asis.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractEscrowReportDto {
    //에스크로 M 키
    private Long escrMKey;
    //상품 유형코드(기본형/보험형)
    private String prdtTpc;
    private String prdtTpcValue;
    //상품 구분코드(임대/매매)
    private String prdtDsc;
    private String prdtDscValue;
    //에스크로 진행코드
    private String escrPgc;
    private String escrPgcValue;
    //에스크로 진행상세코드
    private String escrDtlPgc;
    //에스크로 잔고 금액
    private Long escrBlncAmt;
    //지번 주소1
    private String alnAddr1;
    //지번 주소2
    private String alnAddr2;
    //대금 구분코드
    private String chrgDsc;
    private String chrgDscValue;
    //입금자명
    private String rommnNm;
    //입금 일자
    private String romDt;
    //입금 금액
    private Long romAmt;
    //입금텍스트
    private String romTxt;
    //지급/입금 구분
    private String gubun;
    //등록일시
    private String regDtm;
}
