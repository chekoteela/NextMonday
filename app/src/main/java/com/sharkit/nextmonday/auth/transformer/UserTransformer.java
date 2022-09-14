package com.sharkit.nextmonday.auth.transformer;

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
    public static User toUser(final String email, final String name, final String lastName, final String password) {
        final CryptoAES aes = CryptoAES.getInstance();

        return User.builder()
                .email(email.trim())
                .name(name.trim())
                .lastName(lastName.trim())
                .password(aes.encrypt(password.trim()))
                .role(UserRole.USER)
                .build();
    }

    public static User toUser(final String uId, final String email, final String name) {
        return User.builder()
                .id(uId.trim())
                .email(email.trim())
                .name(name.trim())
                .role(UserRole.USER)
                .build();
    }
}
