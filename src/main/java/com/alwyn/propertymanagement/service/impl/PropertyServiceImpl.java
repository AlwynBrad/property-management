package com.alwyn.propertymanagement.service.impl;

import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.entity.PropertyEntity;
import com.alwyn.propertymanagement.entity.UserEntity;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.alwyn.propertymanagement.exception.ErrorModel;
import com.alwyn.propertymanagement.mapper.PropertyMapper;
import com.alwyn.propertymanagement.repository.PropertyRepository;
import com.alwyn.propertymanagement.repository.UserRepository;
import com.alwyn.propertymanagement.service.PropertyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    // This method saves a property and returns its PropertyDTO.
    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) throws BusinessException {

         // Check if the user with the provided ID exists.
        Optional<UserEntity> optUe = userRepository.findById(propertyDTO.getUserId());
        if (optUe.isPresent()) {

            // Convert the PropertyDTO to a PropertyEntity and save it.
            PropertyEntity pe = PropertyMapper.INSTANCE.dtoToEntity(propertyDTO);
            pe = propertyRepository.save(pe);
            propertyDTO = PropertyMapper.INSTANCE.entityToDTO(pe);
        } else {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("NO_USER");
            errorModel.setMessage("The given user does not exist");
            errorModelList.add(errorModel);

            // Throw a BusinessException if the user doesn't exist.

            throw new BusinessException(errorModelList);
        }
        return propertyDTO;
    }


    // This method retrieves all properties and returns a list of PropertyDTO.
    @Override
    public List<PropertyDTO> getAllProperties() {

        // Retrieve all properties from the repository.
        List<PropertyEntity> listOFProperties = (List<PropertyEntity>) propertyRepository.findAll();
        List<PropertyDTO> propList = new ArrayList<>();
        for (PropertyEntity pe : listOFProperties) {

            // Convert PropertyEntity to PropertyDTO and add it to the list.
            PropertyDTO dto = PropertyMapper.INSTANCE.entityToDTO(pe);
            propList.add(dto);
        }
        return propList;
    }

    // This method retrieves all properties for a specific user and returns a list of PropertyDTO.
    
    @Override
    public List<PropertyDTO> getAllPropertiesForUser(Long userId) {

        // Retrieve properties associated with the provided user ID.
        List<PropertyEntity> listOFProperties = propertyRepository.findAllByUserEntityId(userId);
        List<PropertyDTO> propList = new ArrayList<>();
        for (PropertyEntity pe : listOFProperties) {

            // Convert PropertyEntity to PropertyDTO and add it to the list.
            PropertyDTO dto = PropertyMapper.INSTANCE.entityToDTO(pe);
            propList.add(dto);
        }
        return propList;
    }


     // This method updates a property's details and returns its PropertyDTO.
    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId) {

        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if (optEn.isPresent()) {
            PropertyEntity pe = optEn.get();

            // Update property details from the provided PropertyDTO.
            pe.setTitle(propertyDTO.getTitle());
            pe.setDescription(propertyDTO.getDescription());
            pe.setAddress(propertyDTO.getAddress());
            pe.setPrice(propertyDTO.getPrice());

             // Save the updated PropertyEntity.
            propertyRepository.save(pe);
            dto = PropertyMapper.INSTANCE.entityToDTO(pe);
        }
        return dto;
        
        
    }

     // This method updates a property's description and returns its PropertyDTO.
    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if (optEn.isPresent()) {
            PropertyEntity pe = optEn.get();

            // Update the property's description from the provided PropertyDTO.
            pe.setDescription(propertyDTO.getDescription());

             // Save the updated PropertyEntity.
            dto = PropertyMapper.INSTANCE.entityToDTO(pe);
            propertyRepository.save(pe);
        }
        return dto;
        
        
    }

     // This method updates a property's price and returns its PropertyDTO.
    @Override
    public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if (optEn.isPresent()) {
            PropertyEntity pe = optEn.get();

             // Update the property's price from the provided PropertyDTO.
            pe.setPrice(propertyDTO.getPrice());

            // Save the updated PropertyEntity.
            dto = PropertyMapper.INSTANCE.entityToDTO(pe);
            propertyRepository.save(pe);
        }
        return dto;
        
        
    }

     // This method deletes a property by its ID.
    @Override
    public void deleteProperty(Long propertyId) {

        // Delete the property by its ID.
        propertyRepository.deleteById(propertyId);
    }

}
