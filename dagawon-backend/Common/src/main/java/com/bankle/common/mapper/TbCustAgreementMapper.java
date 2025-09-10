package com.bankle.common.mapper;

import com.bankle.common.dto.TbCustAgreementDto;
import com.bankle.common.entity.TbCustAgreement;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbCustAgreementMapper extends DefaultMapper<TbCustAgreementDto, TbCustAgreement> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TbCustAgreement partialUpdate(TbCustAgreementDto tbCustAgreementDto, @MappingTarget TbCustAgreement tbCustAgreement);

    TbCustAgreementMapper INSTANCE = Mappers.getMapper(TbCustAgreementMapper.class);
}