package com.alwyn.propertymanagement.service;

import com.alwyn.propertymanagement.dto.UserDTO;
import java.util.List;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.nimbusds.jose.JOSEException;

public interface UserService {
    
    UserDTO register(UserDTO userDTO) throws BusinessException, JOSEException;
    UserDTO login(String email, String password) throws BusinessException, JOSEException;
    List<UserDTO> getAllUsers();
}
