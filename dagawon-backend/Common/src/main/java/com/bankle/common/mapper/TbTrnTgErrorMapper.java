package com.bankle.common.mapper;

import com.bankle.common.dto.TbTrnTgErrorDto;
import com.bankle.common.entity.TbTrnTgError;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbTrnTgErrorMapper extends DefaultMapper<TbTrnTgErrorDto, TbTrnTgError> {
    TbTrnTgErrorMapper INSTANCE = Mappers.getMapper(TbTrnTgErrorMapper.class);
}