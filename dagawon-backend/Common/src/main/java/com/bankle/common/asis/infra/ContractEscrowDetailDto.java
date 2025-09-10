package com.bankle.common.asis.infra;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.entity.ContractEscrowDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractEscrowDetailDto {

    private Long escrDKey;
    private String chrgDsc;
    //에스크로 금액
    private Long escrAmt;
    private Long rlRomAmt;
    //입금 예정 일자
    private String romPlnDt;

    private Long escrMKey;
    private String romFnYn;
    private String pmntFnYn;

    public static List<ContractEscrowDetailDto> of(ContractEscrow escrow){
        List<ContractEscrowDetail> contractEscrowDetails = escrow.getContractEscrowDetails();
        List<ContractEscrowDetailDto> romPlnList = new ArrayList<>();

        contractEscrowDetails.forEach(details -> romPlnList.add(ContractEscrowDetailDto.builder()
                        .escrDKey(details.getEscrDKey())
                        .chrgDsc(details.getChrgDsc())
                        .escrAmt(details.getEscrAmt())
                        .rlRomAmt(details.getRlRomAmt())
                        .romPlnDt(details.getRomPlnDt())
                        .escrMKey(details.getEscrMKey())
                        .romFnYn(details.getRomFnYn())
                        .pmntFnYn(details.getPmntFnYn())
                        .build()));

        return romPlnList;
    }
}
