package com.bankle.common.mapper;

import com.bankle.common.dto.TbTrnRshRsltMasterDto;
import com.bankle.common.entity.TbTrnRshRsltMaster;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbTrnRshRsltMasterMapper extends DefaultMapper<TbTrnRshRsltMasterDto, TbTrnRshRsltMaster> {
    TbTrnRshRsltMasterMapper INSTANCE = Mappers.getMapper(TbTrnRshRsltMasterMapper.class);
}