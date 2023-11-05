package com.alwyn.propertymanagement.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

// Annotation indicating that this class provides exception handling for controllers.
public class CustomBusinessHandler{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Logger for logging exception details.
    
    @ExceptionHandler(BusinessException.class)

    // Exception handler for BusinessException.
    public ResponseEntity<List<ErrorModel>> handleBusinessException(BusinessException bex){

        // Handles exceptions of type BusinessException.

        for (ErrorModel em : bex.getErrors()) {

             // Iterate through the error models in the BusinessException.
            logger.debug("Business Exception is thown - level - debug: {} - {}", em.getCode(), em.getMessage());
            logger.info("Business Exception is thown - level - info: {} - {}", em.getCode(), em.getMessage());
            logger.warn("Business Exception is thown - level - warn: {} - {}", em.getCode(), em.getMessage());
            logger.error("Business Exception is thown - level - error: {} - {}", em.getCode(), em.getMessage());
        }
        
        return new ResponseEntity<>(bex.getErrors(), HttpStatus.BAD_REQUEST);

        // Return a response entity with error details and a status of Bad Request.
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)

     // Exception handler for MethodArgumentNotValidException.
    public ResponseEntity<List<ErrorModel>> handleFieldValidationException(MethodArgumentNotValidException manve){

        // Handles exceptions related to method argument validation.

        List<ErrorModel> errorModelsList = new ArrayList<>();
        ErrorModel errorModel = null;

          // Lists to hold error details and individual error models.
        List<FieldError> fieldErrorsList = manve.getBindingResult().getFieldErrors();

         // Retrieve field validation errors from the exception.
        for (FieldError fieldError : fieldErrorsList) {

             // Iterate through field validation errors.

            logger.debug("Inside field validation - level - debug: {} - {}", fieldError.getField(), fieldError.getDefaultMessage());
            logger.info("Inside field validation - level - info: {} - {}", fieldError.getField(), fieldError.getDefaultMessage());

            errorModel = new ErrorModel();
            errorModel.setCode(fieldError.getCode());
            errorModel.setMessage(fieldError.getDefaultMessage());
            errorModelsList.add(errorModel);

            // Create error models and add them to the list.
        }

        return new ResponseEntity<>(errorModelsList, HttpStatus.BAD_REQUEST);

        // Return a response entity with field validation errors and a status of Bad Request.

    } 
}
