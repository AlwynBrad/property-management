package com.alwyn.propertymanagement.service;

import com.alwyn.propertymanagement.dto.UserDTO;

public interface UserService {
    
    UserDTO register(UserDTO userDTO);
    UserDTO login(String email, String password);
}
