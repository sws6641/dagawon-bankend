package com.bankle.common.mapper;

import com.bankle.common.dto.TbTrnTgLogDto;
import com.bankle.common.entity.TbTrnTgLog;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbTrnTgLogMapper extends DefaultMapper<TbTrnTgLogDto, TbTrnTgLog> {
    TbTrnTgLogMapper INSTANCE = Mappers.getMapper(TbTrnTgLogMapper.class);
}