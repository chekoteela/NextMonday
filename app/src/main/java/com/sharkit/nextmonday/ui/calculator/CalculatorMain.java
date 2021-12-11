package com.sharkit.nextmonday.ui.calculator;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.firestore.calculator.FoodInfoFirebase;
import com.sharkit.nextmonday.db.sqlite.calculator.product.ProductPFCDataService;
import com.sharkit.nextmonday.db.sqlite.calculator.ration_list.RationLinkDataService;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.entity.calculator.PFC;
import com.sharkit.nextmonday.service.calculator.calculator_main.AllNutritionService;
import com.sharkit.nextmonday.service.calculator.calculator_main.CalculatorMainService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalculatorMain extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calculator_main, container, false);

        new CalculatorMainService(getAllNutrition())
                .writeAllNeededField()
                .getGeneralNutrition()
                .findById(root)
                .writeToField()
                .activity()
                .setAdaptive();
        synchronizedDB();
        return root;
    }

    @SuppressLint("SimpleDateFormat")
    private PFC getAllNutrition() {
        AllNutritionService nutritionService = new AllNutritionService();
        return nutritionService.getAll(new RationLinkDataService(getContext())
                .findLAllSumByDate(new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis())),
                new RationLinkDataService(getContext())
                        .findGeneralAndFoodPortion(new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis())));
    }

    private void synchronizedDB() {
        new FoodInfoFirebase()
                .findAll()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                        new ProductPFCDataService(getContext()).create(queryDocumentSnapshot.toObject(FoodInfo.class));
                    }
                });
    }
}