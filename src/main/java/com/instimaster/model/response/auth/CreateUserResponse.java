package com.instimaster.model.response.auth;

import com.instimaster.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateUserResponse {
    private Long id;
}
