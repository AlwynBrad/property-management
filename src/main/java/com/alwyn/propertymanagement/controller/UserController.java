// This code defines a controller class called UserController, which handles HTTP requests related to user operations in the application.
package com.alwyn.propertymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.alwyn.propertymanagement.service.UserService;
import com.nimbusds.jose.JOSEException;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    
    @Autowired
    private UserService userService;

     // Define a POST endpoint for user registration.
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Parameter(
        name = "userDTO", example = "user information", required = true) 
        @Valid @RequestBody UserDTO userDTO) throws BusinessException, JOSEException{
        userDTO = userService.register(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

     // Define a POST endpoint for user login.
    @PostMapping(path = "/login", consumes = {"application/json"}, produces = {"application/json"} )
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO userDTO) throws BusinessException, JOSEException{
        userDTO = userService.login(userDTO.getOwnerEmail(), userDTO.getPassword());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

     // Define a GET endpoint to retrieve a list of all users.
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> usersList = userService.getAllUsers();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
    
}
