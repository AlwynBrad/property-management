package com.alwyn.propertymanagement.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.entity.PropertyEntity;
import com.alwyn.propertymanagement.entity.UserEntity;
import com.alwyn.propertymanagement.enums.Role;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.alwyn.propertymanagement.repository.PropertyRepository;
import com.alwyn.propertymanagement.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PropertyServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PropertyServiceImplTest {
    @MockBean
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyServiceImpl propertyServiceImpl;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link PropertyServiceImpl#saveProperty(PropertyDTO)}
     */
    @Test
    void testSaveProperty() throws BusinessException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setAddress("42 Main St");
        propertyEntity.setDescription("The characteristics of someone or something");
        propertyEntity.setId(1L);
        propertyEntity.setPrice(10.0d);
        propertyEntity.setTitle("Dr");
        propertyEntity.setUserEntity(userEntity);
        when(propertyRepository.save(Mockito.<PropertyEntity>any())).thenReturn(propertyEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setOwnerEmail("jane.doe@example.org");
        userEntity2.setOwnerName("Owner Name");
        userEntity2.setPassword("iloveyou");
        userEntity2.setPhone("6625550144");
        userEntity2.setRole(Role.USER);
        Optional<UserEntity> ofResult = Optional.of(userEntity2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("The characteristics of someone or something");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        PropertyDTO actualSavePropertyResult = propertyServiceImpl.saveProperty(propertyDTO);
        verify(userRepository).findById(Mockito.<Long>any());
        verify(propertyRepository).save(Mockito.<PropertyEntity>any());
        assertEquals("42 Main St", actualSavePropertyResult.getAddress());
        assertEquals("Dr", actualSavePropertyResult.getTitle());
        assertEquals("The characteristics of someone or something", actualSavePropertyResult.getDescription());
        assertEquals(10.0d, actualSavePropertyResult.getPrice().doubleValue());
        assertEquals(1L, actualSavePropertyResult.getId());
        assertEquals(1L, actualSavePropertyResult.getUserId().longValue());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#saveProperty(PropertyDTO)}
     */
    @Test
    void testSavePropertyWithNonExistentUser() throws BusinessException {
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("The characteristics of someone or something");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        assertThrows(BusinessException.class, () -> propertyServiceImpl.saveProperty(propertyDTO));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#getAllProperties()}
     */
    @Test
    void testGetAllPropertiesWithEmptyList() {
        when(propertyRepository.findAll()).thenReturn(new ArrayList<>());
        List<PropertyDTO> actualAllProperties = propertyServiceImpl.getAllProperties();
        verify(propertyRepository).findAll();
        assertTrue(actualAllProperties.isEmpty());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#getAllProperties()}
     */
    @Test
    void testGetAllPropertiesWithOneProperty() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setAddress("42 Main St");
        propertyEntity.setDescription("The characteristics of someone or something");
        propertyEntity.setId(1L);
        propertyEntity.setPrice(10.0d);
        propertyEntity.setTitle("Dr");
        propertyEntity.setUserEntity(userEntity);

        ArrayList<PropertyEntity> propertyEntityList = new ArrayList<>();
        propertyEntityList.add(propertyEntity);
        when(propertyRepository.findAll()).thenReturn(propertyEntityList);
        List<PropertyDTO> actualAllProperties = propertyServiceImpl.getAllProperties();
        verify(propertyRepository).findAll();
        assertEquals(1, actualAllProperties.size());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#getAllProperties()}
     */
    @Test
    void testGetAllPropertiesWithMultipleProperties() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setAddress("42 Main St");
        propertyEntity.setDescription("The characteristics of someone or something");
        propertyEntity.setId(1L);
        propertyEntity.setPrice(10.0d);
        propertyEntity.setTitle("Dr");
        propertyEntity.setUserEntity(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setOwnerEmail("john.smith@example.org");
        userEntity2.setOwnerName("42");
        userEntity2.setPassword("Password");
        userEntity2.setPhone("8605550118");
        userEntity2.setRole(Role.ADMIN);

        PropertyEntity propertyEntity2 = new PropertyEntity();
        propertyEntity2.setAddress("17 High St");
        propertyEntity2.setDescription("Description");
        propertyEntity2.setId(2L);
        propertyEntity2.setPrice(0.5d);
        propertyEntity2.setTitle("Mr");
        propertyEntity2.setUserEntity(userEntity2);

        ArrayList<PropertyEntity> propertyEntityList = new ArrayList<>();
        propertyEntityList.add(propertyEntity2);
        propertyEntityList.add(propertyEntity);
        when(propertyRepository.findAll()).thenReturn(propertyEntityList);
        List<PropertyDTO> actualAllProperties = propertyServiceImpl.getAllProperties();
        verify(propertyRepository).findAll();
        assertEquals(2, actualAllProperties.size());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#getAllPropertiesForUser(Long)}
     */
    @Test
    void testGetAllPropertiesForUserWithNoProperties() {
        when(propertyRepository.findAllByUserEntityId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        List<PropertyDTO> actualAllPropertiesForUser = propertyServiceImpl.getAllPropertiesForUser(1L);
        verify(propertyRepository).findAllByUserEntityId(Mockito.<Long>any());
        assertTrue(actualAllPropertiesForUser.isEmpty());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#getAllPropertiesForUser(Long)}
     */
    @Test
    void testGetAllPropertiesForUserWithProperty() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setAddress("42 Main St");
        propertyEntity.setDescription("The characteristics of someone or something");
        propertyEntity.setId(1L);
        propertyEntity.setPrice(10.0d);
        propertyEntity.setTitle("Dr");
        propertyEntity.setUserEntity(userEntity);

        ArrayList<PropertyEntity> propertyEntityList = new ArrayList<>();
        propertyEntityList.add(propertyEntity);
        when(propertyRepository.findAllByUserEntityId(Mockito.<Long>any())).thenReturn(propertyEntityList);
        List<PropertyDTO> actualAllPropertiesForUser = propertyServiceImpl.getAllPropertiesForUser(1L);
        verify(propertyRepository).findAllByUserEntityId(Mockito.<Long>any());
        assertEquals(1, actualAllPropertiesForUser.size());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#updateProperty(PropertyDTO, Long)}
     */
    @Test
    void testUpdatePropertyWithValidData() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setAddress("42 Main St");
        propertyEntity.setDescription("The characteristics of someone or something");
        propertyEntity.setId(1L);
        propertyEntity.setPrice(10.0d);
        propertyEntity.setTitle("Dr");
        propertyEntity.setUserEntity(userEntity);
        Optional<PropertyEntity> ofResult = Optional.of(propertyEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setOwnerEmail("jane.doe@example.org");
        userEntity2.setOwnerName("Owner Name");
        userEntity2.setPassword("iloveyou");
        userEntity2.setPhone("6625550144");
        userEntity2.setRole(Role.USER);

        PropertyEntity propertyEntity2 = new PropertyEntity();
        propertyEntity2.setAddress("42 Main St");
        propertyEntity2.setDescription("The characteristics of someone or something");
        propertyEntity2.setId(1L);
        propertyEntity2.setPrice(10.0d);
        propertyEntity2.setTitle("Dr");
        propertyEntity2.setUserEntity(userEntity2);
        when(propertyRepository.save(Mockito.<PropertyEntity>any())).thenReturn(propertyEntity2);
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("The characteristics of someone or something");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        PropertyDTO actualUpdatePropertyResult = propertyServiceImpl.updateProperty(propertyDTO, 1L);
        verify(propertyRepository).findById(Mockito.<Long>any());
        verify(propertyRepository).save(Mockito.<PropertyEntity>any());
        assertEquals("42 Main St", actualUpdatePropertyResult.getAddress());
        assertEquals("Dr", actualUpdatePropertyResult.getTitle());
        assertEquals("The characteristics of someone or something", actualUpdatePropertyResult.getDescription());
        assertEquals(10.0d, actualUpdatePropertyResult.getPrice().doubleValue());
        assertEquals(1L, actualUpdatePropertyResult.getId());
        assertEquals(1L, actualUpdatePropertyResult.getUserId().longValue());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#updateProperty(PropertyDTO, Long)}
     */
    @Test
    void testUpdatePropertyWithNonExistentProperty() {
        Optional<PropertyEntity> emptyResult = Optional.empty();
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("The characteristics of someone or something");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        PropertyDTO actualUpdatePropertyResult = propertyServiceImpl.updateProperty(propertyDTO, 1L);
        verify(propertyRepository).findById(Mockito.<Long>any());
        assertNull(actualUpdatePropertyResult);
    }

    /**
     * Method under test: {@link PropertyServiceImpl#updatePropertyDescription(PropertyDTO, Long)}
     */
    @Test
    void testUpdatePropertyDescription() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setAddress("42 Main St");
        propertyEntity.setDescription("The characteristics of someone or something");
        propertyEntity.setId(1L);
        propertyEntity.setPrice(10.0d);
        propertyEntity.setTitle("Dr");
        propertyEntity.setUserEntity(userEntity);
        Optional<PropertyEntity> ofResult = Optional.of(propertyEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setOwnerEmail("jane.doe@example.org");
        userEntity2.setOwnerName("Owner Name");
        userEntity2.setPassword("iloveyou");
        userEntity2.setPhone("6625550144");
        userEntity2.setRole(Role.USER);

        PropertyEntity propertyEntity2 = new PropertyEntity();
        propertyEntity2.setAddress("42 Main St");
        propertyEntity2.setDescription("The characteristics of someone or something");
        propertyEntity2.setId(1L);
        propertyEntity2.setPrice(10.0d);
        propertyEntity2.setTitle("Dr");
        propertyEntity2.setUserEntity(userEntity2);
        when(propertyRepository.save(Mockito.<PropertyEntity>any())).thenReturn(propertyEntity2);
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("The characteristics of someone or something");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        PropertyDTO actualUpdatePropertyDescriptionResult = propertyServiceImpl.updatePropertyDescription(propertyDTO,
                1L);
        verify(propertyRepository).findById(Mockito.<Long>any());
        verify(propertyRepository).save(Mockito.<PropertyEntity>any());
        assertEquals("42 Main St", actualUpdatePropertyDescriptionResult.getAddress());
        assertEquals("Dr", actualUpdatePropertyDescriptionResult.getTitle());
        assertEquals("The characteristics of someone or something",
                actualUpdatePropertyDescriptionResult.getDescription());
        assertEquals(10.0d, actualUpdatePropertyDescriptionResult.getPrice().doubleValue());
        assertEquals(1L, actualUpdatePropertyDescriptionResult.getId());
        assertEquals(1L, actualUpdatePropertyDescriptionResult.getUserId().longValue());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#updatePropertyDescription(PropertyDTO, Long)}
     */
    @Test
    void testUpdatePropertyDescriptionWithoutExistingProperty() {
        Optional<PropertyEntity> emptyResult = Optional.empty();
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("The characteristics of someone or something");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        PropertyDTO actualUpdatePropertyDescriptionResult = propertyServiceImpl.updatePropertyDescription(propertyDTO,
                1L);
        verify(propertyRepository).findById(Mockito.<Long>any());
        assertNull(actualUpdatePropertyDescriptionResult);
    }

    /**
     * Method under test: {@link PropertyServiceImpl#updatePropertyPrice(PropertyDTO, Long)}
     */
    @Test
    void testUpdatePropertyPrice() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setAddress("42 Main St");
        propertyEntity.setDescription("The characteristics of someone or something");
        propertyEntity.setId(1L);
        propertyEntity.setPrice(10.0d);
        propertyEntity.setTitle("Dr");
        propertyEntity.setUserEntity(userEntity);
        Optional<PropertyEntity> ofResult = Optional.of(propertyEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setOwnerEmail("jane.doe@example.org");
        userEntity2.setOwnerName("Owner Name");
        userEntity2.setPassword("iloveyou");
        userEntity2.setPhone("6625550144");
        userEntity2.setRole(Role.USER);

        PropertyEntity propertyEntity2 = new PropertyEntity();
        propertyEntity2.setAddress("42 Main St");
        propertyEntity2.setDescription("The characteristics of someone or something");
        propertyEntity2.setId(1L);
        propertyEntity2.setPrice(10.0d);
        propertyEntity2.setTitle("Dr");
        propertyEntity2.setUserEntity(userEntity2);
        when(propertyRepository.save(Mockito.<PropertyEntity>any())).thenReturn(propertyEntity2);
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("The characteristics of someone or something");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        PropertyDTO actualUpdatePropertyPriceResult = propertyServiceImpl.updatePropertyPrice(propertyDTO, 1L);
        verify(propertyRepository).findById(Mockito.<Long>any());
        verify(propertyRepository).save(Mockito.<PropertyEntity>any());
        assertEquals("42 Main St", actualUpdatePropertyPriceResult.getAddress());
        assertEquals("Dr", actualUpdatePropertyPriceResult.getTitle());
        assertEquals("The characteristics of someone or something", actualUpdatePropertyPriceResult.getDescription());
        assertEquals(10.0d, actualUpdatePropertyPriceResult.getPrice().doubleValue());
        assertEquals(1L, actualUpdatePropertyPriceResult.getId());
        assertEquals(1L, actualUpdatePropertyPriceResult.getUserId().longValue());
    }

    /**
     * Method under test: {@link PropertyServiceImpl#updatePropertyPrice(PropertyDTO, Long)}
     */
    @Test
    void testUpdatePropertyPriceForNonExistingProperty() {
        Optional<PropertyEntity> emptyResult = Optional.empty();
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("The characteristics of someone or something");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        PropertyDTO actualUpdatePropertyPriceResult = propertyServiceImpl.updatePropertyPrice(propertyDTO, 1L);
        verify(propertyRepository).findById(Mockito.<Long>any());
        assertNull(actualUpdatePropertyPriceResult);
    }

    /**
     * Method under test: {@link PropertyServiceImpl#deleteProperty(Long)}
     */
    @Test
    void testDeleteProperty() {
        doNothing().when(propertyRepository).deleteById(Mockito.<Long>any());
        propertyServiceImpl.deleteProperty(1L);
        verify(propertyRepository).deleteById(Mockito.<Long>any());
        assertTrue(propertyServiceImpl.getAllProperties().isEmpty());
    }
}

