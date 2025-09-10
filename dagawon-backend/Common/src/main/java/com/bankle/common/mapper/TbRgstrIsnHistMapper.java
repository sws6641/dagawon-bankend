package com.bankle.common.mapper;

import com.bankle.common.dto.TbRgstrIsnHistDto;
import com.bankle.common.entity.TbRgstrIsnHist;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbRgstrIsnHistMapper extends DefaultMapper<TbRgstrIsnHistDto, TbRgstrIsnHist> {
    TbRgstrIsnHistMapper INSTANCE = Mappers.getMapper(TbRgstrIsnHistMapper.class);
}