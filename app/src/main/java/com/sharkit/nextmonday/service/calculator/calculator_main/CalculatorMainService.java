package com.sharkit.nextmonday.service.calculator.calculator_main;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.progress.progressview.ProgressView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.firestore.calculator.SettingFirebase;
import com.sharkit.nextmonday.entity.calculator.GeneralNutrition;
import com.sharkit.nextmonday.entity.calculator.PFC;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.Objects;

public class CalculatorMainService implements LayoutService {
    private Context context;
    private TextView eatCalorie, percentCalorie, allEatCalorie, allCalorie,
            eatFat, percentFat, allEatFat, allFat,
            eatCarbohydrate, percentCarbohydrate, allEatCarbohydrate, allCarbohydrate,
            eatProtein, percentProtein, allEatProtein, allProtein,
            drinkWater, percentWater, allDrinkWater, allWater;
    private ProgressView calorieProgress, fatProgress, carbohydrateProgress, proteinProgress, waterProgress;
    private ImageView plus, addWater, addFood, addWeight;
    private final PFC allNutrition;
    private boolean isVisible = true;

    public CalculatorMainService(PFC allNutrition) {
        this.allNutrition = allNutrition;
    }

    @Override
    public LayoutService writeToField() {
        eatCalorie.setText(String.valueOf(allNutrition.getCalorie()));
        allEatCalorie.setText(String.valueOf(allNutrition.getCalorie()));
        eatProtein.setText(String.valueOf(allNutrition.getProtein()));
        allEatProtein.setText(String.valueOf(allNutrition.getProtein()));
        eatCarbohydrate.setText(String.valueOf(allNutrition.getCarbohydrate()));
        allEatCarbohydrate.setText(String.valueOf(allNutrition.getCarbohydrate()));
        eatFat.setText(String.valueOf(allNutrition.getFat()));
        allEatFat.setText(String.valueOf(allNutrition.getFat()));
        drinkWater.setText(String.valueOf(allNutrition.getWater()));
        allDrinkWater.setText(String.valueOf(allNutrition.getWater()));
        return this;
    }

    public LayoutService getGeneralNutrition() {
        new SettingFirebase()
                .getGeneralNutrition()
                .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            writeToField(Objects.requireNonNull(documentSnapshot.toObject(GeneralNutrition.class)));
                            calculatePercent(Objects.requireNonNull(documentSnapshot.toObject(GeneralNutrition.class)));
                        }else {
                            writeToField(new GeneralNutrition());
                        }
                });
        return this;
    }

    private void calculatePercent(GeneralNutrition generalNutrition) {
        percentCalorie.setText(String.valueOf((int) getPercent(generalNutrition.getCalorie(), (float) allNutrition.getCalorie())));
        calorieProgress.setProgress(getPercent(generalNutrition.getCalorie(), allNutrition.getCalorie()) / 100);
        percentFat.setText(String.valueOf((int) getPercent(generalNutrition.getFat(), allNutrition.getFat())));
        fatProgress.setProgress(getPercent(generalNutrition.getFat(), allNutrition.getFat()) / 100);
        percentCarbohydrate.setText(String.valueOf((int) getPercent(generalNutrition.getCarbohydrate(), allNutrition.getCarbohydrate())));
        carbohydrateProgress.setProgress(getPercent(generalNutrition.getCarbohydrate(), allNutrition.getCarbohydrate()) / 100);
        percentProtein.setText(String.valueOf((int) getPercent(generalNutrition.getProtein(), allNutrition.getProtein())));
        proteinProgress.setProgress(getPercent(generalNutrition.getProtein(), allNutrition.getProtein()) / 100);
        percentWater.setText(String.valueOf((int) getPercent(generalNutrition.getWater(), allNutrition.getWater())));
        waterProgress.setProgress(getPercent(generalNutrition.getWater(), allNutrition.getWater()) / 100);
    }

    private float getPercent(int i, float j) {
        return (j * 100 / i);
    }

    private void writeToField(GeneralNutrition generalNutrition) {
        allCalorie.setText(String.valueOf(generalNutrition.getCalorie()));
        allProtein.setText(String.valueOf(generalNutrition.getProtein()));
        allCarbohydrate.setText(String.valueOf(generalNutrition.getCarbohydrate()));
        allFat.setText(String.valueOf(generalNutrition.getFat()));
        allWater.setText(String.valueOf(generalNutrition.getWater()));
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        plus.setOnClickListener(v -> {
            if (isVisible) {
                addFood.setVisibility(View.VISIBLE);
                addWater.setVisibility(View.VISIBLE);
                addWeight.setVisibility(View.VISIBLE);
                isVisible = false;
            } else {
                addFood.setVisibility(View.GONE);
                addWater.setVisibility(View.GONE);
                addWeight.setVisibility(View.GONE);
                isVisible = true;
            }
        });
        addFood.setOnClickListener(v -> Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_find_food_by_name));
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        plus = root.findViewById(R.id.plus_xml);
        addFood = root.findViewById(R.id.add_food_xml);
        addWater = root.findViewById(R.id.add_watter_xml);
        addWeight = root.findViewById(R.id.add_weight_xml);

        drinkWater = root.findViewById(R.id.drink_water_xml);
        percentWater = root.findViewById(R.id.percent_water_xml);
        allDrinkWater = root.findViewById(R.id.all_drink_water_xml);
        allWater = root.findViewById(R.id.all_water_xml);

        eatProtein = root.findViewById(R.id.eat_protein_xml);
        percentProtein = root.findViewById(R.id.percent_protein_xml);
        allEatProtein = root.findViewById(R.id.all_eat_protein_xml);
        allProtein = root.findViewById(R.id.all_protein_xml);

        eatCarbohydrate = root.findViewById(R.id.eat_carbohydrate_xml);
        percentCarbohydrate = root.findViewById(R.id.percent_carbohydrate_xml);
        allEatCarbohydrate = root.findViewById(R.id.all_eat_carbohydrate_xml);
        allCarbohydrate = root.findViewById(R.id.all_carbohydrate_xml);

        eatCalorie = root.findViewById(R.id.eat_calorie_xml);
        percentCalorie = root.findViewById(R.id.percent_calorie_xml);
        allEatCalorie = root.findViewById(R.id.all_eat_calorie_xml);
        allCalorie = root.findViewById(R.id.all_calorie_xml);

        eatFat = root.findViewById(R.id.eat_fat_xml);
        percentFat = root.findViewById(R.id.percent_fat_xml);
        allEatFat = root.findViewById(R.id.all_eat_fat_xml);
        allFat = root.findViewById(R.id.all_fat_xml);

        waterProgress = root.findViewById(R.id.water_xml);
        proteinProgress = root.findViewById(R.id.protein_xml);
        carbohydrateProgress = root.findViewById(R.id.carbohydrate_xml);
        calorieProgress = root.findViewById(R.id.calorie_xml);
        fatProgress = root.findViewById(R.id.fat_xml);
        return this;
    }
}
