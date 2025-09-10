package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrFeePaymentDto;
import com.bankle.common.entity.TbEscrFeePayment;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbEscrFeePaymentMapper extends DefaultMapper<TbEscrFeePaymentDto, TbEscrFeePayment> {
    TbEscrFeePaymentMapper INSTANCE = Mappers.getMapper(TbEscrFeePaymentMapper.class);
}