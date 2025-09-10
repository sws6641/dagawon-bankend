package com.bankle.common.mapper;

import com.bankle.common.dto.TbCustCouponHistDto;
import com.bankle.common.entity.TbCustCouponHist;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbCustCouponHistMapper extends DefaultMapper<TbCustCouponHistDto, TbCustCouponHist> {
    TbCustCouponHistMapper INSTANCE = Mappers.getMapper(TbCustCouponHistMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TbCustCouponHist partialUpdate(TbCustCouponHistDto tbCustCouponHistDto, @MappingTarget TbCustCouponHist tbCustCouponHist);
}