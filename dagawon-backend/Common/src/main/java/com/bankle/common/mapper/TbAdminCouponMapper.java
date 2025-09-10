package com.bankle.common.mapper;

import com.bankle.common.dto.TbAdminCouponDto;
import com.bankle.common.entity.TbAdminCoupon;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TbAdminCouponMapper extends DefaultMapper<TbAdminCouponDto, TbAdminCoupon> {

    TbAdminCouponMapper INSTANCE = Mappers.getMapper(TbAdminCouponMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TbAdminCoupon partialUpdate(TbAdminCouponDto tbAdminCouponDto, @MappingTarget TbAdminCoupon tbAdminCoupon);
}