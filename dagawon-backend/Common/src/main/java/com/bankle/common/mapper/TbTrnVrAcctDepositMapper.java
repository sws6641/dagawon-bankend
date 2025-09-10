package com.bankle.common.mapper;

import com.bankle.common.dto.TbTrnVrAcctDepositDto;
import com.bankle.common.entity.TbTrnVrAcctDeposit;
import org.apache.poi.openxml4j.opc.internal.MemoryPackagePart;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbTrnVrAcctDepositMapper extends DefaultMapper<TbTrnVrAcctDepositDto, TbTrnVrAcctDeposit> {
    TbTrnVrAcctDepositMapper INSTANCE = Mappers.getMapper(TbTrnVrAcctDepositMapper.class);
}