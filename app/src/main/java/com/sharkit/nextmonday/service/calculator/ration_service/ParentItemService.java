package com.sharkit.nextmonday.service.calculator.ration_service;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_MEAL;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_RATION_DATE;
import static com.sharkit.nextmonday.configuration.constant.MenuItemName.DELETE;
import static com.sharkit.nextmonday.configuration.validation.Configuration.unwrap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.firestore.calculator.FoodInfoFirebase;
import com.sharkit.nextmonday.entity.calculator.RationNutrition;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ParentItemService implements LayoutService {

    public ParentItemService(RationNutrition rationNutrition) {
        this.rationNutrition = rationNutrition;
    }
    private final RationNutrition rationNutrition;
    private LinearLayout parentItem;
    private Activity activity;
    private TextView name, protein, carbohydrate, fat, calorie;
    private ImageView addFood;

    @Override
    public LayoutService writeToField() {
        name.setText(rationNutrition.getNameMeal());
        protein.setText(String.valueOf(rationNutrition.getAllProtein()));
        carbohydrate.setText(String.valueOf(rationNutrition.getAllCarbohydrate()));
        fat.setText(String.valueOf(rationNutrition.getAllFat()));
        calorie.setText(String.valueOf(rationNutrition.getAllCalorie()));
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService activity() {
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
        addFood.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(FRAGMENT_MEAL, rationNutrition.getNameMeal());
            navController.navigate(R.id.nav_cal_find_food_by_name, bundle);
        });
        parentItem.setOnCreateContextMenuListener((menu, v, menuInfo) -> menu.add(DELETE)
                .setOnMenuItemClickListener(item -> {
                    Bundle bundle = new Bundle();

                    bundle.putString(FRAGMENT_RATION_DATE, new SimpleDateFormat(SHOW_DATE_FORMAT)
                            .format(Calendar.getInstance().getTimeInMillis()));
                    new FoodInfoFirebase()
                            .deleteMeal(rationNutrition.getNameMeal());
                            navController.navigate(R.id.nav_cal_ration, bundle);
                    return true;
                }));
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        activity = unwrap(root.getContext());
        parentItem = root.findViewById(R.id.parent_item_xml);
        addFood = root.findViewById(R.id.create_my_food_xml);
        name = root.findViewById(R.id.meal_name_xml);
        protein = root.findViewById(R.id.all_protein_xml);
        carbohydrate = root.findViewById(R.id.all_carbohydrate_xml);
        fat = root.findViewById(R.id.all_fat_xml);
        calorie = root.findViewById(R.id.all_calorie_xml);
        return this;
    }

}
