package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.ContractEscrowRom;
import com.bankle.common.asis.domain.entity.QContractEscrowRom;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContractEscrowRomRepositorySupport extends QuerydslRepositorySupport {
    public ContractEscrowRomRepositorySupport(){
        super(ContractEscrowRom.class);
    }

    public Long getSumRomAmt(Long escrMKey, String chrgDsc){
        QContractEscrowRom rom = QContractEscrowRom.contractEscrowRom;

        JPQLQuery<ContractEscrowRom> query = from(rom);
        return query.select(rom.romAmt.sum().as("romAmtSum"))
                               .where(rom.escrMKey.eq(escrMKey)
                                .and(rom.chrgDsc.eq(chrgDsc))
                               ).fetchOne();
    }
}
