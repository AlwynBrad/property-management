package com.alwyn.propertymanagement.convertor;

import org.springframework.stereotype.Component;

import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.entity.PropertyEntity;

@Component
public class PropertyConvertor {
    
    public PropertyEntity convertDTOtoEntity(PropertyDTO propertyDTO){

        PropertyEntity pe = new PropertyEntity();
        pe.setTitle(propertyDTO.getTitle());
        pe.setDescription(propertyDTO.getDescription());
        pe.setAddress(propertyDTO.getAddress());
        pe.setOwnerName(propertyDTO.getOwnerName());
        pe.setOwnerEmail(propertyDTO.getOwnerEmail());
        pe.setPrice(propertyDTO.getPrice());

        return pe;
    }

     public PropertyDTO convertEntitytoDTO(PropertyEntity propertyEntity){

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(propertyEntity.getId());
        propertyDTO.setTitle(propertyEntity.getTitle());
        propertyDTO.setDescription(propertyEntity.getDescription());
        propertyDTO.setAddress(propertyEntity.getAddress());
        propertyDTO.setOwnerName(propertyEntity.getOwnerName());
        propertyDTO.setOwnerEmail(propertyEntity.getOwnerEmail());
        propertyDTO.setPrice(propertyEntity.getPrice());

        return propertyDTO;
    }
}
