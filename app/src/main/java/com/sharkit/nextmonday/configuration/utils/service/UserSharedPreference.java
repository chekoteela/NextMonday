package com.sharkit.nextmonday.configuration.utils.service;

import android.content.Context;

import com.google.gson.Gson;
import com.sharkit.nextmonday.auth.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSharedPreference {

    private final Context context;

    private static final String USER_PREFERENCE = "user_shared_preference";

    public void set(final User user) {
        final Gson gson = new Gson();
        new SharedPreference(this.context, USER_PREFERENCE).setJSON(gson.toJson(user));
    }

    public User get() {
        final Gson gson = new Gson();
        return gson.fromJson(new SharedPreference(this.context, USER_PREFERENCE).getJSON(), User.class);
    }
}
