package com.bankle.common.mapper;

import java.util.List;

public interface DefaultMapper<Dto, Entity>  {

    Dto toDto(Entity entity);

    Entity toEntity(Dto dto);

    List<Dto> toDtoList(List<Entity> entityList);

    List<Entity> toEntityList(List<Dto> dtoList);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    Entity partialUpdate(Dto dto, @MappingTarget Entity entity);
}
