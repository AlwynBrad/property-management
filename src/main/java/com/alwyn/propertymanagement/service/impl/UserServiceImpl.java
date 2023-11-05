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

    // This method registers a new user and returns their UserDTO.
    @Override
    public UserDTO register(UserDTO userDTO) throws BusinessException, JOSEException {

        // Check if the user with the provided email already exists.
        Optional<UserEntity> optUe = userRepository.findByOwnerEmail(userDTO.getOwnerEmail());
        if (optUe.isPresent()) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("EMAIL_EXIST");
            errorModel.setMessage("The email you entered already exist");
            errorModelList.add(errorModel);

            // Throw a BusinessException if the email already exists.
            throw new BusinessException(errorModelList);
        }

        // Create a new UserEntity and set its properties from the provided UserDTO.
        UserEntity userEntity = UserMapper.INSTANCE.dtoToEntity(userDTO);

        // Encode the user's password and set their role to "USER."
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRole(Role.USER);

        // Save the userEntity to the repository.
        userEntity = userRepository.save(userEntity);

        // Create an AddressEntity associated with the user and save it.
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setHouseNo(userDTO.getHouseNo());
        addressEntity.setStreet(userDTO.getStreet());
        addressEntity.setCity(userDTO.getCity());
        addressEntity.setState(userDTO.getState());
        addressEntity.setPostalCode(userDTO.getPostalCode());
        addressEntity.setCountry(userDTO.getCountry());
        addressEntity.setUserEntity(userEntity);

        addressRepository.save(addressEntity);

        // Convert the saved UserEntity back to a UserDTO.
        userDTO = UserMapper.INSTANCE.entityToDTO(userEntity);

         // Generate a JWT token and set it in the UserDTO.
        String jwtToken = jwtService.generateJwtToken(userDTO.getOwnerEmail());
        userDTO.setToken(jwtToken);

        return userDTO;        
    }

      // This method performs user login and returns a UserDTO.
    @Override
    public UserDTO login(String email, String password) throws BusinessException, JOSEException {
        UserDTO userDTO = null;
        Optional<UserEntity> optionalUserEntity = userRepository.findByOwnerEmail(email);

        // Check if the user with the provided email exists and if the password matches.
        if (optionalUserEntity.isPresent() && optionalUserEntity.get().getOwnerEmail().equals(email) && passwordEncoder.matches(password, optionalUserEntity.get().getPassword())) {
            userDTO = UserMapper.INSTANCE.entityToDTO(optionalUserEntity.get());
            
            // Generate a JWT token and set it in the UserDTO.
            String jwtToken = jwtService.generateJwtToken(email);
            userDTO.setToken(jwtToken);            
        }
        else{

            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_LOGIN");
            errorModel.setMessage("Incorrect email or Password");
            errorModelList.add(errorModel);

             // Throw a BusinessException if login is invalid.
            throw new BusinessException(errorModelList);
        }
    return userDTO;
    }

    // This method retrieves all users and returns a list of UserDTO.
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
