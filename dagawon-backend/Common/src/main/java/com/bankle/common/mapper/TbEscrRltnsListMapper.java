package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrRltnsListDto;
import com.bankle.common.entity.TbEscrRltnsList;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbEscrRltnsListMapper extends DefaultMapper<TbEscrRltnsListDto, TbEscrRltnsList> {
    TbEscrRltnsListMapper INSTANCE = Mappers.getMapper(TbEscrRltnsListMapper.class);
}