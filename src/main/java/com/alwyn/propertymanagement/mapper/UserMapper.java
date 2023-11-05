package com.alwyn.propertymanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.entity.UserEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)

// Define a MapStruct mapper interface for converting between UserDTO and UserEntity.
// The unmappedTargetPolicy set to IGNORE indicates that unmapped properties should be ignored.

public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Create a mapper instance using Mappers.getMapper method.

    @Mapping(target = "id", ignore = true)

    // MapStruct annotation: Ignore mapping the 'id' property from UserDTO to UserEntity.
    UserEntity dtoToEntity(UserDTO userDTO);

    @Mapping(target = "password", ignore = true)

    // MapStruct annotation: Ignore mapping the 'password' property from UserEntity to UserDTO.
    UserDTO entityToDTO(UserEntity userEntity);
}
