package com.sharkit.nextmonday.service.calculator.find_food;

import android.view.View;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ParentItemService implements LayoutService {
    private final GeneralDataPFCDTO generalDataPFCDTO;
    private TextView name, weight, protein, carbohydrate, fat, calorie;

    public ParentItemService(GeneralDataPFCDTO generalDataPFCDTO) {
        this.generalDataPFCDTO = generalDataPFCDTO;
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
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        name = root.findViewById(R.id.name_product_xml);
        weight = root.findViewById(R.id.weight_food_xml);
        protein = root.findViewById(R.id.protein_xml);
        carbohydrate = root.findViewById(R.id.carbohydrate_xml);
        fat = root.findViewById(R.id.fat_xml);
        calorie = root.findViewById(R.id.calorie_xml);
        return this;
    }
}
