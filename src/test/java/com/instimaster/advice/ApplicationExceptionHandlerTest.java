package com.instimaster.advice;

import com.instimaster.exceptions.institute.InstituteNotFoundException;
import com.instimaster.exceptions.user.UserAlreadyExistsException;
import com.instimaster.model.response.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ApplicationExceptionHandlerTest {

    @Mock
    MethodArgumentNotValidException methodArgumentNotValidException;
    @Mock
    InstituteNotFoundException instituteNotFoundException;
    @Mock
    UserAlreadyExistsException userAlreadyExistsException;
    @Mock
    RuntimeException runtimeException;

    @Mock
    BindingResult bindingResult;

    private final ApplicationExceptionHandler applicationExceptionHandler = new ApplicationExceptionHandler();

    @Test
    void handleInvalidArguments() {
        List<FieldError> fieldErrors = List.of(
                new FieldError("object1", "field1", "message1"),
                new FieldError("object2", "field2", "message2")
        );
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("field1", "message1");
        resultMap.put("field2", "message2");
        ExceptionResponse result = ExceptionResponse.builder()
                .exception(IllegalArgumentException.class.getSimpleName())
                .message(resultMap.toString())
                .build();
        assertThat(applicationExceptionHandler.handleInvalidArguments(methodArgumentNotValidException), samePropertyValuesAs(result));
    }

    @Test
    void handleInvalidUserRole() {
        when(instituteNotFoundException.getMessage()).thenReturn("message2");
        when(userAlreadyExistsException.getMessage()).thenReturn("message3");

        ExceptionResponse instituteNotFoundExceptionResponse = ExceptionResponse.builder()
                .exception(InstituteNotFoundException.class.getSimpleName())
                .message("message2")
                .build();

        ExceptionResponse userAlreadyExistsExceptionResponse = ExceptionResponse.builder()
                .exception(UserAlreadyExistsException.class.getSimpleName())
                .message("message3")
                .build();

        assertThat(applicationExceptionHandler.handleInternalErrors(instituteNotFoundException), samePropertyValuesAs(instituteNotFoundExceptionResponse));
        assertThat(applicationExceptionHandler.handleInternalErrors(userAlreadyExistsException), samePropertyValuesAs(userAlreadyExistsExceptionResponse));
    }

    @Test
    void handleGenericError() {
        when(runtimeException.getMessage()).thenReturn("message1");

        ExceptionResponse runtimeExceptionResponse = ExceptionResponse.builder()
                .exception(RuntimeException.class.getSimpleName())
                .message("message1")
                .build();

        assertThat(applicationExceptionHandler.handleGenericError(runtimeException), samePropertyValuesAs(runtimeExceptionResponse));
    }
}