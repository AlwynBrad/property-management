package com.alwyn.propertymanagement.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.entity.AddressEntity;
import com.alwyn.propertymanagement.entity.UserEntity;
import com.alwyn.propertymanagement.enums.Role;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.alwyn.propertymanagement.repository.AddressRepository;
import com.alwyn.propertymanagement.repository.UserRepository;
import com.alwyn.propertymanagement.security.JwtService;
import com.nimbusds.jose.JOSEException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties
class UserServiceImplTest {
    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#register(UserDTO)}
     */
    @Test
    void testRegister() throws BusinessException, JOSEException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(ofResult);

        UserDTO userDTO = new UserDTO();
        userDTO.setCity("Oxford");
        userDTO.setCountry("GB");
        userDTO.setHouseNo(62);
        userDTO.setId(1L);
        userDTO.setOwnerEmail("jane.doe@example.org");
        userDTO.setOwnerName("Owner Name");
        userDTO.setPassword("iloveyou");
        userDTO.setPhone("6625550144");
        userDTO.setPostalCode(600024);
        userDTO.setState("MD");
        userDTO.setStreet("Street");
        userDTO.setToken("ABC123");
        assertThrows(BusinessException.class, () -> userServiceImpl.register(userDTO));
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#register(UserDTO)}
     */
    @Test
    void testRegister2() throws BusinessException, JOSEException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("Oxford");
        addressEntity.setCountry("GB");
        addressEntity.setHouseNo(21);
        addressEntity.setId(1L);
        addressEntity.setPostalCode(613005);
        addressEntity.setState("MD");
        addressEntity.setStreet("Street");
        addressEntity.setUserEntity(userEntity);
        when(addressRepository.save(Mockito.<AddressEntity>any())).thenReturn(addressEntity);
        when(jwtService.generateJwtToken(Mockito.<String>any())).thenReturn("ABC123");

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setOwnerEmail("jane.doe@example.org");
        userEntity2.setOwnerName("Owner Name");
        userEntity2.setPassword("iloveyou");
        userEntity2.setPhone("6625550144");
        userEntity2.setRole(Role.USER);
        when(userRepository.save(Mockito.<UserEntity>any())).thenReturn(userEntity2);
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(emptyResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        UserDTO userDTO = new UserDTO();
        userDTO.setCity("Oxford");
        userDTO.setCountry("GB");
        userDTO.setHouseNo(12);
        userDTO.setId(1L);
        userDTO.setOwnerEmail("jane.doe@example.org");
        userDTO.setOwnerName("Owner Name");
        userDTO.setPassword("iloveyou");
        userDTO.setPhone("6625550144");
        userDTO.setPostalCode(613007);
        userDTO.setState("MD");
        userDTO.setStreet("Street");
        userDTO.setToken("ABC123");
        UserDTO actualRegisterResult = userServiceImpl.register(userDTO);
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
        verify(jwtService).generateJwtToken(Mockito.<String>any());
        verify(addressRepository).save(Mockito.<AddressEntity>any());
        verify(userRepository).save(Mockito.<UserEntity>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals("6625550144", actualRegisterResult.getPhone());
        assertEquals("ABC123", actualRegisterResult.getToken());
        assertEquals("Owner Name", actualRegisterResult.getOwnerName());
        assertEquals("jane.doe@example.org", actualRegisterResult.getOwnerEmail());
        assertEquals(1L, actualRegisterResult.getId().longValue());
    }

    /**
     * Method under test: {@link UserServiceImpl#login(String, String)}
     */
    @Test
    void testLoginWithValidCredentials() throws BusinessException, JOSEException {
        when(jwtService.generateJwtToken(Mockito.<String>any())).thenReturn("ABC123");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(true);
        UserDTO actualLoginResult = userServiceImpl.login("jane.doe@example.org", "iloveyou");
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
        verify(jwtService).generateJwtToken(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
        assertEquals("6625550144", actualLoginResult.getPhone());
        assertEquals("ABC123", actualLoginResult.getToken());
        assertEquals("Owner Name", actualLoginResult.getOwnerName());
        assertEquals("jane.doe@example.org", actualLoginResult.getOwnerEmail());
        assertEquals(1L, actualLoginResult.getId().longValue());
    }

    /**
     * Method under test: {@link UserServiceImpl#login(String, String)}
     */
    @Test
    void testLoginWithJOSEException() throws BusinessException, JOSEException {
        when(jwtService.generateJwtToken(Mockito.<String>any())).thenThrow(new JOSEException("An error occurred"));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(true);
        assertThrows(JOSEException.class, () -> userServiceImpl.login("jane.doe@example.org", "iloveyou"));
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
        verify(jwtService).generateJwtToken(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#login(String, String)}
     */
    @Test
    void testLoginWithBusinessException() throws BusinessException, JOSEException {
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getOwnerEmail()).thenReturn("foo");
        doNothing().when(userEntity).setId(Mockito.<Long>any());
        doNothing().when(userEntity).setOwnerEmail(Mockito.<String>any());
        doNothing().when(userEntity).setOwnerName(Mockito.<String>any());
        doNothing().when(userEntity).setPassword(Mockito.<String>any());
        doNothing().when(userEntity).setPhone(Mockito.<String>any());
        doNothing().when(userEntity).setRole(Mockito.<Role>any());
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(BusinessException.class, () -> userServiceImpl.login("jane.doe@example.org", "iloveyou"));
        verify(userEntity).getOwnerEmail();
        verify(userEntity).setId(Mockito.<Long>any());
        verify(userEntity).setOwnerEmail(Mockito.<String>any());
        verify(userEntity).setOwnerName(Mockito.<String>any());
        verify(userEntity).setPassword(Mockito.<String>any());
        verify(userEntity).setPhone(Mockito.<String>any());
        verify(userEntity).setRole(Mockito.<Role>any());
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#login(String, String)}
     */
    @Test
    void testLoginWithNoUserFound() throws BusinessException, JOSEException {
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(BusinessException.class, () -> userServiceImpl.login("jane.doe@example.org", "iloveyou"));
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#login(String, String)}
     */
    @Test
    void testLoginWithInvalidCredentials() throws BusinessException, JOSEException {
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getPassword()).thenReturn("iloveyou");
        when(userEntity.getOwnerEmail()).thenReturn("jane.doe@example.org");
        doNothing().when(userEntity).setId(Mockito.<Long>any());
        doNothing().when(userEntity).setOwnerEmail(Mockito.<String>any());
        doNothing().when(userEntity).setOwnerName(Mockito.<String>any());
        doNothing().when(userEntity).setPassword(Mockito.<String>any());
        doNothing().when(userEntity).setPhone(Mockito.<String>any());
        doNothing().when(userEntity).setRole(Mockito.<Role>any());
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(false);
        assertThrows(BusinessException.class, () -> userServiceImpl.login("jane.doe@example.org", "iloveyou"));
        verify(userEntity).getOwnerEmail();
        verify(userEntity).getPassword();
        verify(userEntity).setId(Mockito.<Long>any());
        verify(userEntity).setOwnerEmail(Mockito.<String>any());
        verify(userEntity).setOwnerName(Mockito.<String>any());
        verify(userEntity).setPassword(Mockito.<String>any());
        verify(userEntity).setPhone(Mockito.<String>any());
        verify(userEntity).setRole(Mockito.<Role>any());
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsersWhenEmpty() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        List<UserDTO> actualAllUsers = userServiceImpl.getAllUsers();
        verify(userRepository).findAll();
        assertTrue(actualAllUsers.isEmpty());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsersWithSingleUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity);
        when(userRepository.findAll()).thenReturn(userEntityList);
        List<UserDTO> actualAllUsers = userServiceImpl.getAllUsers();
        verify(userRepository).findAll();
        assertEquals(1, actualAllUsers.size());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsersWithMultipleUsers() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setOwnerEmail("john.smith@example.org");
        userEntity2.setOwnerName("42");
        userEntity2.setPassword("Password");
        userEntity2.setPhone("8605550118");
        userEntity2.setRole(Role.ADMIN);

        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity2);
        userEntityList.add(userEntity);
        when(userRepository.findAll()).thenReturn(userEntityList);
        List<UserDTO> actualAllUsers = userServiceImpl.getAllUsers();
        verify(userRepository).findAll();
        assertEquals(2, actualAllUsers.size());
    }
}

