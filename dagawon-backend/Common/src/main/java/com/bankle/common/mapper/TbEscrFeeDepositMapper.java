package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrFeeDepositDto;
import com.bankle.common.entity.TbEscrFeeDeposit;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbEscrFeeDepositMapper extends DefaultMapper<TbEscrFeeDepositDto, TbEscrFeeDeposit> {

    TbEscrFeeDepositMapper INSTANCE = Mappers.getMapper(TbEscrFeeDepositMapper.class);

}