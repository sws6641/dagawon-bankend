package com.bankle.common.mapper;

import com.bankle.common.dto.TbCustAgreementHistDto;
import com.bankle.common.entity.TbCustAgreementHist;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbCustAgreementHistMapper extends DefaultMapper<TbCustAgreementHistDto, TbCustAgreementHist> {

    TbCustAgreementHistMapper INSTANCE = Mappers.getMapper(TbCustAgreementHistMapper.class);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TbCustAgreementHist partialUpdate(TbCustAgreementHistDto tbCustAgreementHistDto, @MappingTarget TbCustAgreementHist tbCustAgreementHist);
}