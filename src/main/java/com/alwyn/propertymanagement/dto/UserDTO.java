package com.alwyn.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;

    private String ownerName;

    // Validation annotations for the user's email.
    @NotNull(message = "Email is mandatory")
    @NotEmpty(message = "Email can't be empty")
    @Size(min = 5, max = 50, message = "Email length should be between 5 and 50 characters ")
    private String ownerEmail;
     // The phone number of the user.
    private String phone;
    // Validation annotations for the user's password.
    @NotNull(message = "Password is mandatory")
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 8, message = "Your password should be at least 8 characters")
    private String password;
    // A token associated with the user.
    private String token;

 // Address-related fields.
    private Integer houseNo;   
    private String street;    
    private String city;  
    private String state;     
    private Integer postalCode;   
    private String country;   
}
