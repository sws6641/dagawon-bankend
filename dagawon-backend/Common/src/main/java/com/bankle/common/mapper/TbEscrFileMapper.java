package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrFileDto;
import com.bankle.common.entity.TbEscrFile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbEscrFileMapper extends DefaultMapper<TbEscrFileDto, TbEscrFile> {
    TbEscrFileMapper INSTANCE = Mappers.getMapper(TbEscrFileMapper.class);
}