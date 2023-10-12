package com.alwyn.propertymanagement.service.impl;

import com.alwyn.propertymanagement.convertor.PropertyConvertor;
import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.entity.PropertyEntity;
import com.alwyn.propertymanagement.entity.UserEntity;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.alwyn.propertymanagement.exception.ErrorModel;
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
    private PropertyConvertor propertyConvertor;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) throws BusinessException {

        Optional<UserEntity> optUe = userRepository.findById(propertyDTO.getUserId());
        if (optUe.isPresent()) {
            PropertyEntity pe = propertyConvertor.convertDTOtoEntity(propertyDTO);
            pe.setUserEntity(optUe.get());
            pe = propertyRepository.save(pe);
            propertyDTO = propertyConvertor.convertEntitytoDTO(pe);
        }else{

            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("NO_USER");
            errorModel.setMessage("The give user does not exist");
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);
        }
        return propertyDTO;
        
    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<PropertyEntity> listOFProperties = (List<PropertyEntity>) propertyRepository.findAll();
        List<PropertyDTO> propList = new ArrayList<>();
        for (PropertyEntity pe : listOFProperties) {
            PropertyDTO dto = propertyConvertor.convertEntitytoDTO(pe);
            propList.add(dto);
        }
        return propList;
    }

    
    @Override
    public List<PropertyDTO> getAllPropertiesForUser(Long userId) {
        List<PropertyEntity> listOFProperties = propertyRepository.findAllByUserEntityId(userId);
        List<PropertyDTO> propList = new ArrayList<>();
        for (PropertyEntity pe : listOFProperties) {
            PropertyDTO dto = propertyConvertor.convertEntitytoDTO(pe);
            propList.add(dto);
        }
        return propList;
    }


    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId) {

        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if (optEn.isPresent()) {
            PropertyEntity pe = optEn.get();
            pe.setTitle(propertyDTO.getTitle());
            pe.setDescription(propertyDTO.getDescription());
            pe.setAddress(propertyDTO.getAddress());
            pe.setPrice(propertyDTO.getPrice());

            propertyRepository.save(pe);
            dto = propertyConvertor.convertEntitytoDTO(pe);
        }
        return dto;
        
        
    }

    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if (optEn.isPresent()) {
            PropertyEntity pe = optEn.get();
            pe.setDescription(propertyDTO.getDescription());

            dto = propertyConvertor.convertEntitytoDTO(pe);
            propertyRepository.save(pe);
        }
        return dto;
        
        
    }

    @Override
    public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if (optEn.isPresent()) {
            PropertyEntity pe = optEn.get();
            pe.setPrice(propertyDTO.getPrice());

            dto = propertyConvertor.convertEntitytoDTO(pe);
            propertyRepository.save(pe);
        }
        return dto;
        
        
    }

    @Override
    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }

}
