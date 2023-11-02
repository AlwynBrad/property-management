package com.alwyn.propertymanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ContextConfiguration(classes = {CustomBusinessHandler.class})
@ExtendWith(SpringExtension.class)
class CustomBusinessHandlerTest {
    @Autowired
    private CustomBusinessHandler customBusinessHandler;

    /**
     * Method under test: {@link CustomBusinessHandler#handleBusinessException(BusinessException)}
     */
    @Test
    void testHandleBusinessException() {
        ResponseEntity<List<ErrorModel>> actualHandleBusinessExceptionResult = customBusinessHandler
                .handleBusinessException(new BusinessException(new ArrayList<>()));
        assertEquals(400, actualHandleBusinessExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleBusinessExceptionResult.hasBody());
        assertTrue(actualHandleBusinessExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomBusinessHandler#handleBusinessException(BusinessException)}
     */
    @Test
    void testHandleBusinessExceptionWithSingleError() {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("Code");
        errorModel.setMessage("Not all who wander are lost");

        ArrayList<ErrorModel> errors = new ArrayList<>();
        errors.add(errorModel);
        ResponseEntity<List<ErrorModel>> actualHandleBusinessExceptionResult = customBusinessHandler
                .handleBusinessException(new BusinessException(errors));
        assertEquals(400, actualHandleBusinessExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleBusinessExceptionResult.hasBody());
        assertTrue(actualHandleBusinessExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomBusinessHandler#handleBusinessException(BusinessException)}
     */
    @Test
    void testHandleEmptyBusinessException() {
        BusinessException bex = mock(BusinessException.class);
        when(bex.getErrors()).thenReturn(new ArrayList<>());
        ResponseEntity<List<ErrorModel>> actualHandleBusinessExceptionResult = customBusinessHandler
                .handleBusinessException(bex);
        verify(bex, atLeast(1)).getErrors();
        assertEquals(400, actualHandleBusinessExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleBusinessExceptionResult.hasBody());
        assertTrue(actualHandleBusinessExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomBusinessHandler#handleFieldValidationException(MethodArgumentNotValidException)}
     */
    @Test
    void testHandleFieldValidationExceptionWithNoFieldErrors() {
        ResponseEntity<List<ErrorModel>> actualHandleFieldValidationExceptionResult = customBusinessHandler
                .handleFieldValidationException(
                        new MethodArgumentNotValidException((Executable) null, new BindException("Target", "Object Name")));
        assertEquals(400, actualHandleFieldValidationExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleFieldValidationExceptionResult.hasBody());
        assertTrue(actualHandleFieldValidationExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomBusinessHandler#handleFieldValidationException(MethodArgumentNotValidException)}
     */
    @Test
    void testHandleEmptyFieldValidationException() {
        BeanPropertyBindingResult bindingResult = mock(BeanPropertyBindingResult.class);
        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrorList);
        ResponseEntity<List<ErrorModel>> actualHandleFieldValidationExceptionResult = customBusinessHandler
                .handleFieldValidationException(new MethodArgumentNotValidException((Executable) null, bindingResult));
        verify(bindingResult).getFieldErrors();
        assertEquals(400, actualHandleFieldValidationExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleFieldValidationExceptionResult.getHeaders().isEmpty());
        assertEquals(fieldErrorList, actualHandleFieldValidationExceptionResult.getBody());
    }

    /**
     * Method under test: {@link CustomBusinessHandler#handleFieldValidationException(MethodArgumentNotValidException)}
     */
    @Test
    void testHandleSingleFieldValidationException() {
        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        BeanPropertyBindingResult bindingResult = mock(BeanPropertyBindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrorList);
        ResponseEntity<List<ErrorModel>> actualHandleFieldValidationExceptionResult = customBusinessHandler
                .handleFieldValidationException(new MethodArgumentNotValidException((Executable) null, bindingResult));
        verify(bindingResult).getFieldErrors();
        List<ErrorModel> body = actualHandleFieldValidationExceptionResult.getBody();
        ErrorModel getResult = body.get(0);
        assertEquals("Default Message", getResult.getMessage());
        assertNull(getResult.getCode());
        assertEquals(1, body.size());
        assertEquals(400, actualHandleFieldValidationExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleFieldValidationExceptionResult.hasBody());
        assertTrue(actualHandleFieldValidationExceptionResult.getHeaders().isEmpty());
    }
}

