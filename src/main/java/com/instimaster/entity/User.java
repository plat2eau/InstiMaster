package com.instimaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.instimaster.util.IdUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(ROLE_ADMIN|ROLE_USER)$")
    private String role;
    private String firstName;
    private String lastName;

    public User() {
        this.id = IdUtil.generateUserId();
    }
}
