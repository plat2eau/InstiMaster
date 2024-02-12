package com.instimaster.service;

import com.instimaster.dao.UserDao;
import com.instimaster.entity.User;
import com.instimaster.exceptions.user.UserAlreadyExistsException;
import com.instimaster.model.UserRoles;
import com.instimaster.model.request.auth.CreateUserRequest;
import com.instimaster.model.request.auth.DeleteUserRequest;
import com.instimaster.model.response.auth.CreateUserResponse;
import com.instimaster.service.config.DefaultAdminConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final DefaultAdminConfig defaultAdminConfig;

    public Optional<User> getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        insertUser(user);
        CreateUserResponse response = CreateUserResponse.builder().id(user.getId()).build();
        return new ResponseEntity<CreateUserResponse>(response, HttpStatus.CREATED);
    }

    public ResponseEntity deleteUser(DeleteUserRequest request) {
        userDao.deleteByEmail(request.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public void createDefaultAdmin() {
        User user = new User();
        user.setEmail(defaultAdminConfig.getUsername());
        user.setPassword(defaultAdminConfig.getPassword());
        user.setRole(UserRoles.ADMIN);
        try {
            insertUser(user);
        } catch (UserAlreadyExistsException ignored) {
        }
    }

    private User insertUser(User user) throws UserAlreadyExistsException {
        if(userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email: " + user.getEmail() + " already exists");
        }
        userDao.save(user);
        return user;
    }
}
