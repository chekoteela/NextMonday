package com.sharkit.nextmonday.auth.fragment;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.sharkit.nextmonday.configuration.utils.CryptoAES;
import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.auth.entity.enums.UserRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTransformer {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static User toUser(String uId, String email, String name, String lastName, String password) {
        final CryptoAES aes = CryptoAES.getInstance();

        return User.builder()
                .id(uId)
                .email(email.trim())
                .name(name.trim())
                .lastName(lastName.trim())
                .password(aes.encrypt(password.trim()))
                .role(UserRole.USER)
                .build();
    }

    public static User toUser(String uId, String email, String name) {
        return User.builder()
                .id(uId.trim())
                .email(email.trim())
                .name(name.trim())
                .role(UserRole.USER)
                .build();
    }
}
