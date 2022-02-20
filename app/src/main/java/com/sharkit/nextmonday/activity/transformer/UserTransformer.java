package com.sharkit.nextmonday.activity.transformer;

import com.google.firebase.auth.FirebaseUser;
import com.sharkit.nextmonday.activity.entity.Role;
import com.sharkit.nextmonday.activity.entity.User;
import com.sharkit.nextmonday.configuration.widget_finder.widget.EditTextWidget;

import java.util.Objects;

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

    public static User toUser(EditTextWidget widget, String userId){
        return User.builder()
                .id(Objects.requireNonNull(userId))
                .email(widget.getEmail().getText().toString().trim())
                .name(widget.getUserName().getText().toString().trim())
                .lastName(widget.getUserLastName().getText().toString().trim())
                .password(widget.getPassword().getText().toString().trim())
                .role(Role.USER)
                .build();

    }

}
