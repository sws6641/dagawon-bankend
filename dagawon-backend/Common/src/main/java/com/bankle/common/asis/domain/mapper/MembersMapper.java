package com.bankle.common.asis.domain.mapper;

import com.bankle.common.asis.domain.dto.MembersDto;
import com.bankle.common.asis.domain.entity.Members;
import com.bankle.common.mapper.DefaultMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MembersMapper extends DefaultMapper<MembersDto, Members> {
    MembersMapper INSTANCE = Mappers.getMapper(MembersMapper.class);
}
