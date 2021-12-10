package com.sharkit.nextmonday.service.calculator.find_food;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.FOOD_INFO_S;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_MEAL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.firestore.calculator.FoodInfoFirebase;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ParentItemService implements LayoutService {
    private Context context;
    private final GeneralDataPFCDTO generalDataPFCDTO;
    private TextView name, weight, protein, carbohydrate, fat, calorie;
    private LinearLayout addFood;
    private final String mealName;


    public ParentItemService(GeneralDataPFCDTO generalDataPFCDTO, String mealName) {
        this.generalDataPFCDTO = generalDataPFCDTO;
        this.mealName = mealName;
    }

    @Override
    public LayoutService writeToField() {
        name.setText(String.valueOf(generalDataPFCDTO.getName()));
        weight.setText(String.valueOf(generalDataPFCDTO.getPortion()));
        protein.setText(String.valueOf(generalDataPFCDTO.getProtein()));
        carbohydrate.setText(String.valueOf(generalDataPFCDTO.getCarbohydrate()));
        fat.setText(String.valueOf(generalDataPFCDTO.getFat()));
        calorie.setText(String.valueOf(generalDataPFCDTO.getCalorie()));
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        addFood.setOnClickListener(view -> new FoodInfoFirebase()
                .findFoodById(generalDataPFCDTO.getId())
                .addOnSuccessListener(documentSnapshot -> {
                    Bundle bundle = new Bundle();
                    if (!(mealName == null)){
                        bundle.putString(FRAGMENT_MEAL, mealName);
                    }
                    bundle.putSerializable(FOOD_INFO_S, documentSnapshot.toObject(FoodInfo.class));
                    Navigation.findNavController((Activity) context, R.id.nav_host_fragment)
                            .navigate(R.id.nav_cal_add_my_food, bundle);
                }));
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        addFood = root.findViewById(R.id.up_linear_xml);
        name = root.findViewById(R.id.name_product_xml);
        weight = root.findViewById(R.id.weight_food_xml);
        protein = root.findViewById(R.id.protein_xml);
        carbohydrate = root.findViewById(R.id.carbohydrate_xml);
        fat = root.findViewById(R.id.fat_xml);
        calorie = root.findViewById(R.id.calorie_xml);
        return this;
    }
}
