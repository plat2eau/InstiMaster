package com.instimaster.model.request.auth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
