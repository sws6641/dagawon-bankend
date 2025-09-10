package com.bankle.common.mapper;

import com.bankle.common.dto.TbCustLoginHistDto;
import com.bankle.common.entity.TbCustLoginHist;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbCustLoginHistMapper extends DefaultMapper<TbCustLoginHistDto, TbCustLoginHist> {
    TbCustLoginHistMapper INSTANCE = Mappers.getMapper(TbCustLoginHistMapper.class);
}