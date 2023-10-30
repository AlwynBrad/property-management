package com.alwyn.propertymanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.entity.AddressEntity;
import com.alwyn.propertymanagement.entity.UserEntity;
import com.alwyn.propertymanagement.enums.Role;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.alwyn.propertymanagement.exception.ErrorModel;
import com.alwyn.propertymanagement.mapper.UserMapper;
import com.alwyn.propertymanagement.repository.AddressRepository;
import com.alwyn.propertymanagement.repository.UserRepository;
import com.alwyn.propertymanagement.security.JwtService;
import com.alwyn.propertymanagement.service.UserService;
import com.nimbusds.jose.JOSEException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserDTO register(UserDTO userDTO) throws BusinessException, JOSEException {

        Optional<UserEntity> optUe = userRepository.findByOwnerEmail(userDTO.getOwnerEmail());
        if (optUe.isPresent()) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("EMAIL_EXIST");
            errorModel.setMessage("The email you entered already exist");
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);
        }
        UserEntity userEntity = UserMapper.INSTANCE.dtoToEntity(userDTO);

        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRole(Role.USER);
        userEntity = userRepository.save(userEntity);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setHouseNo(userDTO.getHouseNo());
        addressEntity.setStreet(userDTO.getStreet());
        addressEntity.setCity(userDTO.getCity());
        addressEntity.setState(userDTO.getState());
        addressEntity.setPostalCode(userDTO.getPostalCode());
        addressEntity.setCountry(userDTO.getCountry());
        addressEntity.setUserEntity(userEntity);

        addressRepository.save(addressEntity);

        userDTO = UserMapper.INSTANCE.entityToDTO(userEntity);

        String jwtToken = jwtService.generateJwtToken(userDTO.getOwnerEmail());
        userDTO.setToken(jwtToken);

        return userDTO;        
    }

    @Override
    public UserDTO login(String email, String password) throws BusinessException, JOSEException {
        UserDTO userDTO = null;
        Optional<UserEntity> optionalUserEntity = userRepository.findByOwnerEmail(email);
        if (optionalUserEntity.isPresent() && optionalUserEntity.get().getOwnerEmail().equals(email) && passwordEncoder.matches(password, optionalUserEntity.get().getPassword())) {
            userDTO = UserMapper.INSTANCE.entityToDTO(optionalUserEntity.get());
            
            String jwtToken = jwtService.generateJwtToken(email);
            userDTO.setToken(jwtToken);            
        }
        else{

            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_LOGIN");
            errorModel.setMessage("Incorrect email or Password");
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);
        }
    return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> listOfUsers = userRepository.findAll();
        List<UserDTO> userList = new ArrayList<>(); 
        for (UserEntity ue : listOfUsers) {
            UserDTO dto = UserMapper.INSTANCE.entityToDTO(ue);
            userList.add(dto);
        }
        return userList;
    }
    
}
