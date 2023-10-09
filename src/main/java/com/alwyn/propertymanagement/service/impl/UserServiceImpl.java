package com.alwyn.propertymanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alwyn.propertymanagement.convertor.UserConvertor;
import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.entity.UserEntity;
import com.alwyn.propertymanagement.repository.UserRepository;
import com.alwyn.propertymanagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConvertor userConvertor;

    @Override
    public UserDTO register(UserDTO userDTO) {
        UserEntity userEntity = userConvertor.convertDTOtoEntity(userDTO);
        userEntity = userRepository.save(userEntity);
        userDTO = userConvertor.convertEntitytoDTO(userEntity);
        return userDTO;        
    }

    @Override
    public UserDTO login(String email, String password) {
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
    
}
