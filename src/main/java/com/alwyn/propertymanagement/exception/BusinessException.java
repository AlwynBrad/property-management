package com.alwyn.propertymanagement.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends Exception{
    
    private List<ErrorModel> errors;


    public BusinessException(List<ErrorModel> errors){
        this.errors =  errors;
    }
    
}
