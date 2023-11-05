package com.alwyn.propertymanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.entity.PropertyEntity;

@Mapper

// Define a MapStruct mapper interface for converting between PropertyDTO and PropertyEntity.
public interface PropertyMapper{

    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

     // Create a mapper instance using Mappers.getMapper method.

    @Mapping( target = "id", ignore = true)

    // MapStruct annotation: Ignore mapping the 'id' property from PropertyDTO to PropertyEntity.
    @Mapping(source = "userId", target = "userEntity.id")

    // Map 'userId' property from PropertyDTO to 'userEntity.id' in PropertyEntity.
    PropertyEntity dtoToEntity(PropertyDTO propertyDTO);

    @Mapping(source = "userEntity.id", target = "userId")

     // Map 'userEntity.id' property from PropertyEntity to 'userId' in PropertyDTO.
    PropertyDTO  entityToDTO(PropertyEntity propertyEntity);

}
