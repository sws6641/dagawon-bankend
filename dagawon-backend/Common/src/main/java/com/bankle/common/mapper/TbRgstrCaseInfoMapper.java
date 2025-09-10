package com.bankle.common.mapper;

import com.bankle.common.dto.TbRgstrCaseInfoDto;
import com.bankle.common.entity.TbRgstrCaseInfo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TbRgstrCaseInfoMapper extends DefaultMapper<TbRgstrCaseInfoDto, TbRgstrCaseInfo> {
    TbRgstrCaseInfoMapper INSTANCE = Mappers.getMapper(TbRgstrCaseInfoMapper.class);
}