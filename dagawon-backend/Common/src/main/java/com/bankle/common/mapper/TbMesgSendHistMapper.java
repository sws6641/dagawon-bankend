package com.bankle.common.mapper;

import com.bankle.common.dto.TbMesgSendHistDto;
import com.bankle.common.entity.TbMesgSendHist;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbMesgSendHistMapper extends DefaultMapper<TbMesgSendHistDto, TbMesgSendHist> {
    TbMesgSendHistMapper INSTANCE = Mappers.getMapper(TbMesgSendHistMapper.class);
}