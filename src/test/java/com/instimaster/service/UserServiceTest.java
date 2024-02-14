package com.instimaster.service;

import com.instimaster.dao.UserDao;
import com.instimaster.entity.User;
import com.instimaster.exceptions.user.UserAlreadyExistsException;
import com.instimaster.model.request.auth.CreateUserRequest;
import com.instimaster.model.request.auth.DeleteUserRequest;
import com.instimaster.model.response.auth.CreateUserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserDao userDao;

    @Mock
    User user;

    @Mock
    CreateUserRequest createUserRequest;

    @Mock
    DeleteUserRequest deleteUserRequest;

    @Test
    void getUserByEmail() {
        when(userDao.findByEmail("email")).thenReturn(Optional.ofNullable(user));
        Optional<User> response = userService.getUserByEmail("email");
        assertEquals(response.get(), user);
    }

    @Test
    void createUser() {
        when(userDao.findByEmail("email")).thenReturn(Optional.empty());
        when(createUserRequest.getPassword()).thenReturn("pass");
        when(createUserRequest.getEmail()).thenReturn("email");
        ResponseEntity<CreateUserResponse> response = userService.createUser(createUserRequest);
        verify(userDao, times(1)).findByEmail(any());
        verify(userDao, times(1)).save(any());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void createUserUserAlreadyExists() {
        when(userDao.findByEmail("email")).thenReturn(Optional.of(user));
        when(createUserRequest.getPassword()).thenReturn("pass");
        when(createUserRequest.getEmail()).thenReturn("email");
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserRequest));
        verify(userDao, times(1)).findByEmail(any());
    }

    @Test
    void deleteUser() {
        ResponseEntity response = userService.deleteUser(deleteUserRequest);
        verify(userDao, times(1)).deleteByEmail(any());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}