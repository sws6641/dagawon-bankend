package com.bankle.common.mapper;

import com.bankle.common.dto.TbSequenceDto;
import com.bankle.common.entity.TbSequence;
import com.bankle.common.mapper.DefaultMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbSequenceMapper extends DefaultMapper<TbSequenceDto, TbSequence> {

    TbSequenceMapper INSTANCE = Mappers.getMapper(TbSequenceMapper.class);
}