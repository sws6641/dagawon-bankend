package com.bankle.common.mapper;

import com.bankle.common.dto.TbMesgTpltDto;
import com.bankle.common.entity.TbMesgTplt;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbMesgTpltMapper extends DefaultMapper<TbMesgTpltDto, TbMesgTplt> {
    TbMesgTpltMapper INSTANCE = Mappers.getMapper(TbMesgTpltMapper.class);
}