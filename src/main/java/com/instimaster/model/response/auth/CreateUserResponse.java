package com.instimaster.model.response.auth;

import com.instimaster.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserResponse {
    private Long id;
}
