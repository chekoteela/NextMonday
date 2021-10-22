package com.sharkit.nextmonday.service.calculator.calculator_main;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.progress.progressview.ProgressView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class CalculatorMainService implements LayoutService {
    private Context context;
    private TextView eatCalorie, percentCalorie, allEatCalorie, allCalorie,
            eatFat, percentFat, allEatFat, allFat,
            eatCarbohydrate, percentCarbohydrate, allEatCarbohydrate, allCarbohydrate,
            eatProtein, percentProtein, allEatProtein, allProtein,
            drinkWater, percentWater, allDrinkWater, allWater;
    private ProgressView calorieProgress, fatProgress, carbohydrateProgress, proteinProgress, waterProgress;
    private ImageView plus;

    @Override
    public LayoutService writeToField() {
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_find_food_by_name);
            }
        });
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        plus = root.findViewById(R.id.plus_xml);

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
