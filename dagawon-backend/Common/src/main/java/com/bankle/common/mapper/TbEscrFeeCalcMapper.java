package com.bankle.common.mapper;

import com.bankle.common.dto.TbEscrFeeCalcDto;
import com.bankle.common.entity.TbEscrFeeCalc;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbEscrFeeCalcMapper extends DefaultMapper<TbEscrFeeCalcDto, TbEscrFeeCalc> {

    TbEscrFeeCalcMapper INSTANCE = Mappers.getMapper(TbEscrFeeCalcMapper.class);

}