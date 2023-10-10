package com.alwyn.propertymanagement.service;

import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.exception.BusinessException;

public interface UserService {
    
    UserDTO register(UserDTO userDTO) throws BusinessException;
    UserDTO login(String email, String password) throws BusinessException;
}
