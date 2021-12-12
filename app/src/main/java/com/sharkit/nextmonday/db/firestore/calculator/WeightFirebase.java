package com.sharkit.nextmonday.db.firestore.calculator;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.configuration.constant.FirebaseCollection;
import com.sharkit.nextmonday.entity.calculator.Weight;

public class WeightFirebase extends FirebaseCollection {
    private final FirebaseFirestore DB = FirebaseFirestore.getInstance();

    public Task<Void> create (Weight weight){
        return DB.collection(USER_WEIGHT)
                .document()
                .set(weight);
    }
}
