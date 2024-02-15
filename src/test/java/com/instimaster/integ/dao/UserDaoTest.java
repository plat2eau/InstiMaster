package com.instimaster.integ.dao;

import com.instimaster.InstiMasterApplication;
import com.instimaster.dao.UserDao;
import com.instimaster.entity.User;
import com.instimaster.service.UserService;
import com.instimaster.service.config.DefaultAdminConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("local")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ContextConfiguration(classes = {InstiMasterApplication.class, DefaultAdminConfig.class, UserService.class, UserDao.class,})
public class UserDaoTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.0");

    @Autowired
    private UserDao userDao;

    @Value("${users.default.admin.username}")
    private String adminUsername;

    @Value("${users.default.admin.password}")
    private String adminPassword;

    private final String ADMIN_ROLE = "ROLE_ADMIN";

    @Test
    public void userDaoTest() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        Optional<User> user = userDao.findByEmail(adminUsername);
        if(user.isPresent()) {
            assertEquals(user.get().getEmail(), adminUsername);
            assertEquals(user.get().getPassword(), adminPassword);
            assertEquals(user.get().getRole(), ADMIN_ROLE);
        } else {
            throw new UsernameNotFoundException("User " + adminUsername + " does not exist");
        }

        userDao.deleteByEmail(adminUsername);
        user = userDao.findByEmail(adminUsername);
        assertThat(user).isEmpty();
    }
}
