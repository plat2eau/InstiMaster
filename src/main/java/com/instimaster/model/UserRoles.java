package com.instimaster.model;

import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public enum UserRoles {
    ADMIN, USER;

    public static List<String> getRoles() {
        return Arrays.stream(UserRoles.values())
                .map( value -> value.toString().split("\\.")[1])
                .collect(Collectors.toList());
    }
}
