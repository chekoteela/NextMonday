package com.sharkit.nextmonday.service.calculator.setting;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField.isValidNutritionValue;
import static com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField.isValidWaterValue;
import static com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField.isValidWeight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.firestore.calculator.SettingFirebase;
import com.sharkit.nextmonday.db.firestore.calculator.WeightFirebase;
import com.sharkit.nextmonday.db.sqlite.calculator.weight.WeightDataService;
import com.sharkit.nextmonday.entity.calculator.GeneralNutrition;
import com.sharkit.nextmonday.entity.calculator.Weight;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@SuppressLint("SimpleDateFormat")
public class ManualSettingService implements LayoutService {
    private TabLayout tabLayout;
    private Context context;
    private EditText currentWeight, protein, carbohydrate, fat, water;
    private TextView conclusion;
    private Button calculate, save;

    @Override
    public LayoutService writeToField() {
        Objects.requireNonNull(tabLayout.getTabAt(1)).select();
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_auto_calculate_calorie);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        calculate.setOnClickListener(v -> {
            if (!isValidField()) {
                return;
            }
            conclusion.setText(String.valueOf(calculateCalorie(Float.parseFloat(currentWeight.getText().toString()))));
        });
        save.setOnClickListener(v -> {
            if (!isValidField()) {
                return;
            }
            GeneralNutrition generalNutrition = new GeneralNutrition();
            generalNutrition.setCalorie(calculateCalorie(Float.parseFloat(currentWeight.getText().toString())));
            generalNutrition.setWater((int) (Float.parseFloat(water.getText().toString()) * Float.parseFloat(currentWeight.getText().toString())));
            generalNutrition.setProtein((int) (Float.parseFloat(protein.getText().toString()) * Float.parseFloat(currentWeight.getText().toString())));
            generalNutrition.setCarbohydrate((int) (Float.parseFloat(carbohydrate.getText().toString()) * Float.parseFloat(currentWeight.getText().toString())));
            generalNutrition.setFat((int) (Float.parseFloat(fat.getText().toString()) * Float.parseFloat(currentWeight.getText().toString())));

            Weight weight = new Weight(Float.parseFloat(currentWeight.getText().toString()),
                    new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()));
            new SettingFirebase()
                    .create(generalNutrition)
                    .addOnSuccessListener(unused -> Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_calculator_main));
            new WeightFirebase().create(weight);
            new WeightDataService(context).create(weight);
        });
        return this;
    }

    private int calculateCalorie(float weight) {
        return (int) (Float.parseFloat(protein.getText().toString()) * weight * 4 +
                Float.parseFloat(carbohydrate.getText().toString()) * weight * 4 +
                Float.parseFloat(fat.getText().toString()) * weight * 8);
    }

    private boolean isValidField() {
        return (isValidWeight(currentWeight, context) &&
                isValidNutritionValue(protein, context) &&
                isValidNutritionValue(carbohydrate, context) &&
                isValidNutritionValue(fat, context) &&
                isValidWaterValue(water, context));
    }


    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        tabLayout = root.findViewById(R.id.tab_layout_xml);
        currentWeight = root.findViewById(R.id.weight_xml);
        protein = root.findViewById(R.id.protein_xml);
        fat = root.findViewById(R.id.fat_xml);
        carbohydrate = root.findViewById(R.id.carbohydrate_xml);
        water = root.findViewById(R.id.water_xml);
        conclusion = root.findViewById(R.id.conclusion_xml);
        calculate = root.findViewById(R.id.calculate_xml);
        save = root.findViewById(R.id.save_xml);
        return this;
    }
}
