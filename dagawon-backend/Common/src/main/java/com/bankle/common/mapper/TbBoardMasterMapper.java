package com.bankle.common.mapper;

import com.bankle.common.dto.TbBoardMasterDto;
import com.bankle.common.entity.TbBoardMaster;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbBoardMasterMapper extends DefaultMapper<TbBoardMasterDto, TbBoardMaster> {
    TbBoardMasterMapper INSTANCE = Mappers.getMapper(TbBoardMasterMapper.class);
}