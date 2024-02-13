package com.instimaster.advice;

import com.instimaster.exceptions.institute.InstituteAlreadyExistsException;
import com.instimaster.exceptions.institute.InstituteNotFoundException;
import com.instimaster.exceptions.user.UserAlreadyExistsException;
import com.instimaster.model.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleInvalidArguments(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((error) -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ExceptionResponse response = ExceptionResponse.builder()
                .exception(IllegalArgumentException.class.getSimpleName())
                .message(errorMap.toString())
                .build();

        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InstituteNotFoundException.class, InstituteAlreadyExistsException.class, UserAlreadyExistsException.class})
    public ExceptionResponse handleInvalidUserRole(RuntimeException e) {
        return ExceptionResponse.builder()
                .exception(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class})
    public ExceptionResponse handleGenericError(RuntimeException e){
        return ExceptionResponse.builder()
                .exception(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }
}
