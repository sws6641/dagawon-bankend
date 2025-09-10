package com.bankle.common.mapper;

import com.bankle.common.dto.TbCommAppVrHistDto;
import com.bankle.common.entity.TbCommAppVrHist;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;



@Mapper
public interface TbCommAppVrHistMapper extends DefaultMapper<TbCommAppVrHistDto, TbCommAppVrHist> {
    TbCommAppVrHistMapper INSTANCE = Mappers.getMapper(TbCommAppVrHistMapper.class);
}
