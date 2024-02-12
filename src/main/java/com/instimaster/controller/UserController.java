package com.instimaster.controller;

import com.instimaster.model.request.auth.CreateUserRequest;
import com.instimaster.model.request.auth.DeleteUserRequest;
import com.instimaster.model.response.auth.CreateUserResponse;
import com.instimaster.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }

    @DeleteMapping("/user")
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest request) {
        return userService.deleteUser(request);
    }
}
