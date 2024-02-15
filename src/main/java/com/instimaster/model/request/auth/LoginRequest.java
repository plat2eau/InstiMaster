package com.instimaster.model.request.auth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
