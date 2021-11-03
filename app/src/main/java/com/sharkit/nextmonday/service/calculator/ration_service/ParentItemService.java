package com.sharkit.nextmonday.service.calculator.ration_service;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.RationNutrition;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ParentItemService implements LayoutService {

    public ParentItemService(RationNutrition rationNutrition) {
        this.rationNutrition = rationNutrition;
    }

    private final RationNutrition rationNutrition;
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

    @Override
    public LayoutService activity() {
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        addFood = root.findViewById(R.id.create_my_food_xml);
        name = root.findViewById(R.id.meal_name_xml);
        protein = root.findViewById(R.id.all_protein_xml);
        carbohydrate = root.findViewById(R.id.all_carbohydrate_xml);
        fat = root.findViewById(R.id.all_fat_xml);
        calorie = root.findViewById(R.id.all_calorie_xml);
        return this;
    }
}
