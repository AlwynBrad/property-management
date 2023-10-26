package com.alwyn.propertymanagement.service;


import java.util.List;

import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.exception.BusinessException;

public interface PropertyService {
    PropertyDTO saveProperty(PropertyDTO propertyDTO) throws BusinessException;
    List<PropertyDTO> getAllProperties(); 
    List<PropertyDTO> getAllPropertiesForUser(Long userId);  
    PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId);  
    PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId);
    PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId);
    void deleteProperty(Long propertyId);
}
