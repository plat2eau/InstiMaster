package com.instimaster.model.request.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DeleteUserRequest {
    private String email;
}
