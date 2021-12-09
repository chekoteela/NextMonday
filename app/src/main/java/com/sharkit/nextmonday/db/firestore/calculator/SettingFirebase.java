package com.sharkit.nextmonday.db.firestore.calculator;

import static com.sharkit.nextmonday.configuration.constant.FirebaseDocument.CALCULATOR_GENERAL_NUTRITION;
import static com.sharkit.nextmonday.configuration.constant.FirebaseDocument.CALCULATOR_SETTING;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.configuration.constant.FirebaseCollection;
import com.sharkit.nextmonday.entity.calculator.GeneralNutrition;
import com.sharkit.nextmonday.entity.calculator.Metabolism;

public class SettingFirebase extends FirebaseCollection {
    private final FirebaseFirestore DB = FirebaseFirestore.getInstance();

    public Task<Void> create (Metabolism dto){
       return DB.collection(USER_METABOLISM)
               .document(CALCULATOR_SETTING)
                .set(dto);
    }

    public Task<DocumentSnapshot> getGeneralNutrition() {
        return DB.collection(USER_GENERAL_NUTRITION)
                .document(CALCULATOR_GENERAL_NUTRITION)
                .get();
    }

    public Task<Void> create(GeneralNutrition generalNutrition){
        return DB.collection(USER_GENERAL_NUTRITION)
                .document(CALCULATOR_GENERAL_NUTRITION)
                .set(generalNutrition);
    }
}
