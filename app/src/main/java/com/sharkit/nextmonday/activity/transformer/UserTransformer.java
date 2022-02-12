package com.sharkit.nextmonday.activity.transformer;

import com.sharkit.nextmonday.activity.entity.Role;
import com.sharkit.nextmonday.activity.entity.User;

public class UserTransformer {

    public static User toUser(String id, String email, String name){
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .role(Role.USER)
                .build();
    }
}
