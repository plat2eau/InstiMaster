package com.instimaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.instimaster.model.UserRoles;
import com.instimaster.util.IdUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
public class User {

    @Id
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRoles role;
    private String firstName;
    private String lastName;

    public User() {
        this.id = IdUtil.generateUserId();
    }
}
