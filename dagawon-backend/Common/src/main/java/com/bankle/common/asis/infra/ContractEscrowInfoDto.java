package com.bankle.common.asis.infra;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.entity.ContractEscrowParty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractEscrowInfoDto {

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
    //에스크로 진행상세코드
    private String escrDtlPgc;
    //계약작성일자
    private String cntrtDrwupDt;
    //우편번호
    private String pstNo;
    //지번 주소1
    private String alnAddr1;
    //지번 주소2
    private String alnAddr2;
    //도로명 주소1
    private String rdAddr1;
    //도로명 주소2
    private String rdAddr2;
    //매수인 이름(회원명)
    private String membNm;
    //매수인 생년월일
    private String membBirth;
    //지급은행코드
    private String pmntBnkNo;
    //지급은행코드명
    private String pmntBnkNoTxt;
    //지급계좌번호
    private String pmntAcctNo;
    //지급예정일자
    private String pmntPlnDt;
    //지급승인일자
    private String pmntAprvDt;
    //실행일자
    private String exeDt;
    //에스크로 대상 금액
    private Long escrTrgtAmt;
    //매매금액
    private Long slAmt;
    //채권최고금액
    private Long bndMaxAmt;
    //에스크로잔고금액
    private Long escrBlncAmt;
    //보증금액
    private Long grntAmt;
    //차임금액
    private Long rntAmt;
    //입주일
    private String lsStDt;
    //퇴거일
    private String lsEndDt;
    //임대확정일자
    private String lsFixDt;
    //할당된 가상계좌번호
    private String vrActNo;
    //카드 구분코드명
    private String crdDscTxt;
    //수수료결제구분코드명
    private String feeStmtDscTxt;
    //수수료 납입 가상계좌번호
    private String feeVrAcctNo;
    //수수료 입금 은행코드
    private String feeRomBnkCdTxt;
    //수수료 입금 계좌번호
    private String feeRomAcctNo;
    //수수료 납입자명
    private String feeRommnNm;
    //수수료금액
    private Long feeAmt;
    //수수료납부일자
    private String feeDt;
    //에스크로 상세 정보
    private List<ContractEscrowDetailDto> detailDto;
    //이해관계자 정보
    private List<ContractEscrowParty> contractEscrowParties;

    public static ContractEscrowInfoDto of(ContractEscrow contractEscrow){
        return ContractEscrowInfoDto.builder()
                .escrMKey(contractEscrow.getEscrMKey())
                .prdtTpc(contractEscrow.getPrdtTpc())
                .prdtDsc(contractEscrow.getPrdtDsc())
                .escrPgc(contractEscrow.getEscrPgc())
                .escrDtlPgc(contractEscrow.getEscrDtlPgc())
                .cntrtDrwupDt(contractEscrow.getCntrtDrwupDt())
                .pstNo(contractEscrow.getPstNo())
                .alnAddr1(contractEscrow.getAlnAddr1())
                .alnAddr2(contractEscrow.getAlnAddr2())
                .rdAddr1(contractEscrow.getRdAddr1())
                .rdAddr2(contractEscrow.getRdAddr2())
                .pmntBnkNo(contractEscrow.getPmntBnkCd())
                .pmntAcctNo(contractEscrow.getPmntAcctNo())
                .pmntPlnDt(contractEscrow.getPmntPlnDt())
                .pmntAprvDt(contractEscrow.getPmntAprvDt())
                .exeDt(contractEscrow.getExeDt())
                .bndMaxAmt(contractEscrow.getBndMaxAmt())
                .escrTrgtAmt(contractEscrow.getEscrTrgtAmt())
                .escrBlncAmt(contractEscrow.getEscrBlncAmt())
                .slAmt(contractEscrow.getSlAmt())
                .grntAmt(contractEscrow.getGrntAmt())
                .rntAmt(contractEscrow.getRntAmt())
                .lsStDt(contractEscrow.getLsStDt())
                .lsEndDt(contractEscrow.getLsEndDt())
                .lsFixDt(contractEscrow.getLsFixDt())
                .build();
    }
}
