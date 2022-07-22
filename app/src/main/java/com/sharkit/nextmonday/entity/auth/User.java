package com.sharkit.nextmonday.entity.auth;

import com.sharkit.nextmonday.entity.auth.enums.UserRole;

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
