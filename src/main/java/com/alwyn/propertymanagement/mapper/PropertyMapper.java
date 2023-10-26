package com.alwyn.propertymanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.entity.PropertyEntity;

@Mapper
public interface PropertyMapper{

    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

    @Mapping( target = "id", ignore = true)
    @Mapping(source = "userId", target = "userEntity.id")
    PropertyEntity dtoToEntity(PropertyDTO propertyDTO);

    @Mapping(source = "userEntity.id", target = "userId")
    PropertyDTO  entityToDTO(PropertyEntity propertyEntity);

}
