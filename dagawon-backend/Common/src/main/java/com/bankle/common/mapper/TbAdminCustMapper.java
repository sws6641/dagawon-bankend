package com.bankle.common.mapper;

import com.bankle.common.dto.TbAdminCustDto;
import com.bankle.common.entity.TbAdminCust;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbAdminCustMapper extends DefaultMapper<TbAdminCustDto, TbAdminCust> {
    TbAdminCustMapper INSTANCE = Mappers.getMapper(TbAdminCustMapper.class);
}