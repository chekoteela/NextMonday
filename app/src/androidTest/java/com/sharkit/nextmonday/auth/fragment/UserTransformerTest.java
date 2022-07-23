package com.sharkit.nextmonday.auth.fragment;

import static org.junit.Assert.*;

import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.auth.entity.enums.UserRole;

import org.junit.Test;

public class UserTransformerTest {


    @Test
    public void testToUser() {
        assertEquals(buildUser(), UserTransformer.toUser("uId", "google@gmail.com", "Name", "LastName", "qwerty"));
    }

    @Test
    public void testToGoogleUser() {
        assertEquals(buildGoogleUser(), UserTransformer.toUser("uId", "google@gmail.com", "Name"));
    }

    private User buildGoogleUser() {
        return User.builder()
                .id("uId")
                .name("Name")
                .email("google@gmail.com")
                .role(UserRole.USER)
                .build();
    }
    private User buildUser() {
        return User.builder()
                .id("uId")
                .name("Name")
                .lastName("LastName")
                .password("vkpnOOJcIAvYr9guLrYAvA==")
                .email("google@gmail.com")
                .role(UserRole.USER)
                .build();
    }
}