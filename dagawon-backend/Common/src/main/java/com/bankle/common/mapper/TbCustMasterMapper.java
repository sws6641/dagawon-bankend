package com.bankle.common.mapper;

import com.bankle.common.dto.TbCustMasterDto;
import com.bankle.common.entity.TbCustMaster;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbCustMasterMapper extends DefaultMapper<TbCustMasterDto, TbCustMaster> {
    TbCustMasterMapper INSTANCE = Mappers.getMapper(TbCustMasterMapper.class);
}