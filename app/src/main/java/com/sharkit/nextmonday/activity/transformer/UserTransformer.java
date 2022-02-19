package com.sharkit.nextmonday.activity.transformer;

import com.google.firebase.auth.FirebaseUser;
import com.sharkit.nextmonday.activity.entity.Role;
import com.sharkit.nextmonday.activity.entity.User;

import lombok.Data;

@Data
public class UserTransformer {

    public static User toUser(FirebaseUser user){
        return User.builder()
                .id(user.getUid())
                .email(user.getEmail())
                .name(user.getDisplayName())
                .role(Role.USER)
                .build();
    }
}
