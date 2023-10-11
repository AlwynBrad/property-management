package com.alwyn.propertymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.alwyn.propertymanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    @Autowired
    private UserService userService;

      @PostMapping("/register")
      public ResponseEntity<UserDTO> register(@Parameter(
        name = "userDTO", example = "user information", required = true) 
        @Valid @RequestBody UserDTO userDTO) throws BusinessException{
        userDTO = userService.register(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login", consumes = {"application/json"}, produces = {"application/json"} )
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO userDTO) throws BusinessException{
        userDTO = userService.login(userDTO.getOwnerEmail(), userDTO.getPassword());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
