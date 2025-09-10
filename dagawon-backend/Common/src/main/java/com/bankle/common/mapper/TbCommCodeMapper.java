package com.bankle.common.mapper;

import com.bankle.common.dto.TbCommCodeDto;
import com.bankle.common.entity.TbCommCode;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbCommCodeMapper extends DefaultMapper<TbCommCodeDto, TbCommCode> {

    TbCommCodeMapper INSTANCE = Mappers.getMapper(TbCommCodeMapper.class);
}