package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrPaymentDto;
import com.bankle.common.entity.TbEscrPayment;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbEscrPaymentMapper extends DefaultMapper<TbEscrPaymentDto, TbEscrPayment> {
    TbEscrPaymentMapper INSTANCE = Mappers.getMapper(TbEscrPaymentMapper.class);
}
