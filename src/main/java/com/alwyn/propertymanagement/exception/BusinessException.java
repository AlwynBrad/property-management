package com.alwyn.propertymanagement.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

// Lombok annotations to automatically generate getter methods, setter methods, and a no-argument constructor.

public class BusinessException extends Exception{
    // Custom exception class for handling business-related exceptions.
    
    private List<ErrorModel> errors;

    // A list to store error models representing details of the exception.

    public BusinessException(List<ErrorModel> errors){
        
        // Constructor that accepts a list of error models as a parameter.
        this.errors =  errors;
    }
    
}
