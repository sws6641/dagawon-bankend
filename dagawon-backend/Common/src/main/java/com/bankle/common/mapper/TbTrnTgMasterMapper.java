package com.bankle.common.mapper;

import com.bankle.common.dto.TbTrnTgMasterDto;
import com.bankle.common.entity.TbTrnTgMaster;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbTrnTgMasterMapper extends DefaultMapper<TbTrnTgMasterDto, TbTrnTgMaster> {
    TbTrnTgMasterMapper INSTANCE = Mappers.getMapper(TbTrnTgMasterMapper.class);
}