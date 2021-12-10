package com.sharkit.nextmonday.db.firestore.calculator;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.MODERATION_FOOD;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.RATION;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.USER_LINK_RATION;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.USER_MEAL;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.USER_RATION;

import android.annotation.SuppressLint;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;
import com.sharkit.nextmonday.entity.calculator.Meal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class FoodInfoFirebase {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void create(FoodInfo foodInfo) {
        db.collection(MODERATION_FOOD)
                .document(foodInfo.getId())
                .set(foodInfo);
    }

    public Task<DocumentSnapshot> findFoodById(String id) {
        return db.collection(MODERATION_FOOD)
                .document(id)
                .get();
    }

    public Task<QuerySnapshot> findAll() {
        return db.collection(MODERATION_FOOD)
                .get();
    }

    public Task<Void> createMeal(Meal meal) {
        db.collection(USER_MEAL + new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()))
                .document(meal.getName())
                .set(meal);
        return db.collection(USER_RATION)
                .document(meal.getName())
                .set(meal);
    }

    private void createUserRation() {
        db.collection(RATION)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                        createMeal(queryDocumentSnapshot.toObject(Meal.class));
                    }
                    createTodayMeal();
                });
    }

    private void createTodayMeal() {
        getUserRation()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            createTodayMeal(queryDocumentSnapshot.toObject(Meal.class));
                        }
                    } else {
                        createUserRation();
                    }
                });
    }

    private void createTodayMeal(Meal meal) {
        db.collection(USER_MEAL + new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()))
                .document(meal.getName())
                .set(meal);
    }

    public Task<Void> addNewMyFood(LinkFoodDTO linkFoodDTO) {
        return db.collection(USER_LINK_RATION)
                .document()
                .set(linkFoodDTO);
    }

    private Task<QuerySnapshot> getUserRation() {
        return db.collection(USER_RATION)
                .get();
    }

    @SuppressLint("SimpleDateFormat")
    private Task<QuerySnapshot> getTodayMeal(String date) {
        return db.collection(USER_MEAL + date)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        createTodayMeal();
                    }
                });
    }

    public Task<QuerySnapshot> getMealList(String date) {
        return getTodayMeal(date);
    }

    public void deleteMeal(String meal){
        db.collection(USER_RATION)
                .document(meal)
                .delete();
        db.collection(USER_MEAL + new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()))
                .document(meal)
                .delete();
    }
}