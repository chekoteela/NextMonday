package com.sharkit.nextmonday.auth.entity;

import com.sharkit.nextmonday.auth.entity.enums.UserRole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
}
