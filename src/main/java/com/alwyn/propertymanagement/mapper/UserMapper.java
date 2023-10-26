package com.alwyn.propertymanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.entity.UserEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    UserEntity dtoToEntity(UserDTO userDTO);

    @Mapping(target = "password", ignore = true)
    UserDTO entityToDTO(UserEntity userEntity);
}
