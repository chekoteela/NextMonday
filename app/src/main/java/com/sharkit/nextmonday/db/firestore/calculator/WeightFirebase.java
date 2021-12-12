package com.sharkit.nextmonday.db.firestore.calculator;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;

import android.annotation.SuppressLint;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.configuration.constant.FirebaseCollection;
import com.sharkit.nextmonday.entity.calculator.Weight;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeightFirebase extends FirebaseCollection {
    private final FirebaseFirestore DB = FirebaseFirestore.getInstance();

    @SuppressLint("SimpleDateFormat")
    public Task<Void> create (Weight weight){
        return DB.collection(USER_WEIGHT)
                .document(new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()))
                .set(weight);
    }
}
