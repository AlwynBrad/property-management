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
public class CustomBusinessHandler{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<List<ErrorModel>> handleBusinessException(BusinessException bex){

        for (ErrorModel em : bex.getErrors()) {
            logger.debug("Business Exception is thown - level - debug: {} - {}", em.getCode(), em.getMessage());
            logger.info("Business Exception is thown - level - info: {} - {}", em.getCode(), em.getMessage());
            logger.warn("Business Exception is thown - level - warn: {} - {}", em.getCode(), em.getMessage());
            logger.error("Business Exception is thown - level - error: {} - {}", em.getCode(), em.getMessage());
        }
        
        return new ResponseEntity<>(bex.getErrors(), HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorModel>> handleFieldValidationException(MethodArgumentNotValidException manve){

        List<ErrorModel> errorModelsList = new ArrayList<>();
        ErrorModel errorModel = null;
        List<FieldError> fieldErrorsList = manve.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrorsList) {

            logger.debug("Inside field validation - level - debug: {} - {}", fieldError.getField(), fieldError.getDefaultMessage());
            logger.info("Inside field validation - level - info: {} - {}", fieldError.getField(), fieldError.getDefaultMessage());

            errorModel = new ErrorModel();
            errorModel.setCode(fieldError.getCode());
            errorModel.setMessage(fieldError.getDefaultMessage());
            errorModelsList.add(errorModel);
        }

        return new ResponseEntity<>(errorModelsList, HttpStatus.BAD_REQUEST);

    } 
}
