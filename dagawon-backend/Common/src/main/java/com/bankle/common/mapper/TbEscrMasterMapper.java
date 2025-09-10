package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrMasterDto;
import com.bankle.common.entity.TbEscrMaster;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TbEscrMasterMapper extends DefaultMapper<TbEscrMasterDto, TbEscrMaster> {
    TbEscrMasterMapper INSTANCE = Mappers.getMapper(TbEscrMasterMapper.class);
}