package com.sharkit.nextmonday.service.calculator.create_food;

import android.view.View;
import android.widget.EditText;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class CreateFoodService implements LayoutService {
    private EditText name, portion, calorie, protein, wheyProtein, soyProtein, aggProtein,
    caseinProtein, carbohydrate, simpleCarbohydrate, complexCarbohydrate, fat,
    saturatedFat, transFat, omega3, omega6, omega9, dha, epa, ala,
    cellulose, water, salt, calcium, potassium;

    public CreateFoodService(String bundle) {
    }

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
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        name = root.findViewById(R.id.name_xml);
        portion = root.findViewById(R.id.portion_xml);
        calorie = root.findViewById(R.id.calorie_xml);
        protein = root.findViewById(R.id.protein_xml);
        wheyProtein = root.findViewById(R.id.whey_protein_xml);
        soyProtein = root.findViewById(R.id.soy_protein_xml);
        aggProtein = root.findViewById(R.id.agg_protein_xml);
        caseinProtein = root.findViewById(R.id.casein_protein_xml);
        carbohydrate = root.findViewById(R.id.carbohydrate_xml);
        simpleCarbohydrate = root.findViewById(R.id.simple_carbohydrates_xml);
        complexCarbohydrate = root.findViewById(R.id.complex_carbohydrate_xml);
        fat = root.findViewById(R.id.fat_xml);
        saturatedFat = root.findViewById(R.id.saturated_fat_xml);
        transFat = root.findViewById(R.id.trans_fat_xml);
        omega3 = root.findViewById(R.id.omega3_xml);
        omega6 = root.findViewById(R.id.omega6_xml);
        omega9 = root.findViewById(R.id.omega9_xml);
        dha = root.findViewById(R.id.dha_xml);
        epa = root.findViewById(R.id.epa_xml);
        ala = root.findViewById(R.id.ala_xml);
        cellulose = root.findViewById(R.id.cellulose_xml);
        water = root.findViewById(R.id.water_xml);
        salt = root.findViewById(R.id.salt_xml);
        calcium = root.findViewById(R.id.calcium_xml);
        potassium = root.findViewById(R.id.potassium_xml);
        return this;
    }
}
