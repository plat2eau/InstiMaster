package com.instimaster.model.request.auth;

import com.instimaster.model.UserRoles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateUserRequest {

    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRoles role;
    private String firstName;
    private String lastName;
}
