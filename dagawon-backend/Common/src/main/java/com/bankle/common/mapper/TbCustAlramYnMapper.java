package com.bankle.common.mapper;

import com.bankle.common.dto.TbCustAlramYnDto;
import com.bankle.common.entity.TbCustAlramYn;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbCustAlramYnMapper extends DefaultMapper<TbCustAlramYnDto, TbCustAlramYn> {

    TbCustAlramYnMapper INSTANCE = Mappers.getMapper(TbCustAlramYnMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TbCustAlramYn partialUpdate(TbCustAlramYnDto tbCustAlramYnDto, @MappingTarget TbCustAlramYn tbCustAlramYn);
}