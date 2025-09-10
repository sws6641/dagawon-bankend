package com.bankle.common.mapper;

import com.bankle.common.dto.TbCommVrAcctMasterDto;
import com.bankle.common.entity.TbCommVrAcctMaster;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbCommVrAcctMasterMapper extends DefaultMapper<TbCommVrAcctMasterDto, TbCommVrAcctMaster> {
    TbCommVrAcctMasterMapper INSTANCE = Mappers.getMapper(TbCommVrAcctMasterMapper.class);
}