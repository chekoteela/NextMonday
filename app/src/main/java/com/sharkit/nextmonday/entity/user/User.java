package com.sharkit.nextmonday.entity.user;

import com.sharkit.nextmonday.entity.enums.Role;
import com.sharkit.nextmonday.entity.transformer.UserTransformer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User extends UserTransformer {
    private String name;
    private String email;
    private Enum<Role> role;
    private String id;
    private String password;
    private String lastName;
}
