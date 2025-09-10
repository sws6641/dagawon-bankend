package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrFeeDto;
import com.bankle.common.entity.TbEscrFee;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbEscrFeeMapper extends DefaultMapper<TbEscrFeeDto, TbEscrFee> {
    TbEscrFeeMapper INSTANCE = Mappers.getMapper(TbEscrFeeMapper.class);
}