package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrDepositDto;
import com.bankle.common.entity.TbEscrDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbEscrDepositMapper extends DefaultMapper<TbEscrDepositDto, TbEscrDeposit> {

    TbEscrDepositMapper INSTANCE = Mappers.getMapper(TbEscrDepositMapper.class);
}