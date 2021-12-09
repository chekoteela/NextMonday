package com.sharkit.nextmonday.configuration.constant;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@SuppressLint("SimpleDateFormat")
public class FirebaseCollection {
    private final static String ID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private static final String DATE = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTimeInMillis());
    public final static String USERS = "Users";
    public final static String FEEDBACK = "Feedback";
    public static final String TARGETS = USERS + "/" + ID + "/Targets";
    public static final String MODERATION_FOOD = "DB/Food/Moderation";
    public static final String RELEASE_FOOD = "DB/Food/Release";
    public static final String USER_RATION = USERS + "/" + ID + "/Settings/Meal/Ration";
    public static final String USER_MEAL = USERS + "/" + ID + "/Settings/Meal/";
    public static final String USER_LINK_RATION = USERS + "/" + ID + "/Calculator/Meal/" + DATE;
    public static final String RATION = "DB/Calculator/Ration";
    public static final String USER_METABOLISM = USERS + "/" + ID + "/Setting/";
    public static final String USER_GENERAL_NUTRITION = USERS + "/" + ID + "/Calculator/";
}
