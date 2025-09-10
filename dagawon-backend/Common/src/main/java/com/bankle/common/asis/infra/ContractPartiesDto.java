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
public class ContractPartiesDto {
    //에스크로 M 키
    private Long escrMKey;
    //상품 유형코드
    private String prdtTpc;
    private String prdtTpcValue;
    //상품 구분코드
    private String prdtDsc;
    private String prdtDscValue;
    //지번주소1
    private String alnAddr1;
    //지번주소2
    private String alnAddr2;
    //매수인 이름(회원명)
    private String membNm;
    //지급계좌번호
    private String pmntAcctNo;
    //매도|임대 인원수
    private Integer sldlvLsNop;
    //매수|임차 인원수
    private Integer pchsHreNop;

    private List<ContractEscrowParty> contractEscrowParties;

    public static ContractPartiesDto of(ContractEscrow contractEscrow){
        return ContractPartiesDto.builder()
                .escrMKey(contractEscrow.getEscrMKey())
                .prdtTpc(contractEscrow.getPrdtTpc())
                .prdtDsc(contractEscrow.getPrdtDsc())
                .alnAddr1(contractEscrow.getAlnAddr1())
                .alnAddr2(contractEscrow.getAlnAddr2())
                .pmntAcctNo(contractEscrow.getPmntAcctNo())
                .sldlvLsNop(contractEscrow.getSldlvLsNop() == null ? 0 : contractEscrow.getSldlvLsNop())
                .pchsHreNop(contractEscrow.getPchsHreNop() == null ? 0 : contractEscrow.getPchsHreNop())
                .contractEscrowParties(contractEscrow.getContractEscrowParties())
                .build();
    }
}
