package com.alwyn.propertymanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class BusinessExceptionTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link BusinessException#BusinessException(List)}
     *   <li>{@link BusinessException#setErrors(List)}
     *   <li>{@link BusinessException#getErrors()}
     * </ul>
     */
    @Test
    void testConstructor() {
        ArrayList<ErrorModel> errors = new ArrayList<>();
        BusinessException actualBusinessException = new BusinessException(errors);
        ArrayList<ErrorModel> errors2 = new ArrayList<>();
        actualBusinessException.setErrors(errors2);
        List<ErrorModel> actualErrors = actualBusinessException.getErrors();
        assertEquals(errors, actualErrors);
        assertSame(errors2, actualErrors);
    }
}

