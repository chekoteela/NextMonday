package com.sharkit.nextmonday.configuration.constant;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FirebaseCollection {
    public final static String USERS = "Users";
    public final static String FEEDBACK = "Feedback";
    public static final String TARGETS = USERS + "/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "/Targets";
    public static final String MODERATION_FOOD = "DB/Food/Moderation";
    public static final String RELEASE_FOO = "DB/Food/Release";
}
