package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrMasterHistDto;
import com.bankle.common.entity.TbEscrMasterHist;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbEscrMasterHistMapper extends DefaultMapper<TbEscrMasterHistDto, TbEscrMasterHist> {
    TbEscrMasterHistMapper INSTANCE = Mappers.getMapper(TbEscrMasterHistMapper.class);
}