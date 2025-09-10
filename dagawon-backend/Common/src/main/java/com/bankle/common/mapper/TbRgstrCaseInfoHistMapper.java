package com.bankle.common.mapper;

import com.bankle.common.dto.TbRgstrCaseInfoHistDto;
import com.bankle.common.entity.TbRgstrCaseInfoHist;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;





@Mapper
public interface TbRgstrCaseInfoHistMapper extends DefaultMapper<TbRgstrCaseInfoHistDto, TbRgstrCaseInfoHist> {
    TbRgstrCaseInfoHistMapper INSTANCE = Mappers.getMapper(TbRgstrCaseInfoHistMapper.class);
}