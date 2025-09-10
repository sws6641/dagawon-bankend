package com.bankle.common.asis.infra;

import com.bankle.common.asis.domain.entity.ContractEscrow;
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
public class ContractStatusDto {

    //회원번호
    private String membNo;
    //회원이름
    private String membNm;
    //회원 이해관계자 M 키
    private String membPrtyDsc;
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
    //에스크로 상세 진행코드
    private String escrDtlPgc;
    private String escrDtlPgcValue;
    //계약 작성 일자
    private String cntrtDrwupDt;
    //매도/임대 인원수
    private int sldlvLsNop;
    //매수/임차 인원수
    private int pchsHreNop;
    //수수료 납부 여부
    private String feePayYn;
    //보험 가입 여부
    private String isrnEntrYn;
    //보험 증권 번호
    private String isrnScrtNo;
    //에스크로 잔고 금액
    private Long escrBlncAmt;
    //지번 주소1
    private String alnAddr1;
    //지번 주소2
    private String alnAddr2;
    //대금 구분코드
    private String chrgDsc;
    private String chrgDscValue;
    //에스크로 금액
    private Long escrAmt;
    //입금 예정 일자
    private String romPlnDt;
    //입금 은행 코드
    private String romBnkCd;
    private String romBnkCdValue;
    //입금 계좌 번호
    private String romAcctNo;
    //입금 일자
    private String romDt;
    //입금 금액
    private Long romAmt;
    //실행 일자
    private String exeDt;
    //확정일자
    private String lsFixDt;
    //할당된 가상계좌번호
    private String vrAcctNo;

    public static ContractStatusDto of(ContractEscrow contractEscrow){
        return ContractStatusDto.builder()
                .membNo(contractEscrow.getMembNo())
                .escrMKey(contractEscrow.getEscrMKey())
                .prdtTpc(contractEscrow.getPrdtTpc())
                .prdtDsc(contractEscrow.getPrdtDsc())
                .escrPgc(contractEscrow.getEscrPgc())
                .escrDtlPgc(contractEscrow.getEscrDtlPgc())
                .cntrtDrwupDt(contractEscrow.getCntrtDrwupDt())
                .feePayYn(contractEscrow.getFeePayYn())
                .escrBlncAmt(contractEscrow.getEscrBlncAmt() == null ? 0 : contractEscrow.getEscrBlncAmt())
                .isrnEntrYn(contractEscrow.getIsrnEntrYn())
                .isrnScrtNo(contractEscrow.getIsrnScrtNo())
                .alnAddr1(contractEscrow.getAlnAddr1())
                .alnAddr2(contractEscrow.getAlnAddr2())
                .exeDt(contractEscrow.getExeDt())
                .lsFixDt(contractEscrow.getLsFixDt())
                .build();
    }

}
