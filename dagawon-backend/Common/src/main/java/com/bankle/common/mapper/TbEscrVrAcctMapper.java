package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrVrAcctDto;
import com.bankle.common.entity.TbEscrVrAcct;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbEscrVrAcctMapper extends DefaultMapper<TbEscrVrAcctDto, TbEscrVrAcct> {
    TbEscrVrAcctMapper INSTANCE = Mappers.getMapper(TbEscrVrAcctMapper.class);
}