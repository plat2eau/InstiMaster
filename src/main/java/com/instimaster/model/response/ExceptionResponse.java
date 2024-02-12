package com.instimaster.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {
    private String exception;
    private String message;
}
