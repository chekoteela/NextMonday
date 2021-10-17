package com.sharkit.nextmonday.configuration.constant;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CollectionUser {
    public final static String USERS = "Users";
    public final static String FEEDBACK = "Feedback";
    public static final String TARGETS = USERS + "/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "/Targets";
}
