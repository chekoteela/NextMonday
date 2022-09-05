package com.sharkit.nextmonday.auth.entity;

import com.sharkit.nextmonday.auth.entity.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
}
