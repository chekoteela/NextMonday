package com.sharkit.nextmonday.service.calculator.find_food;

import android.view.View;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.PFC;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.Arrays;
import java.util.List;

public class ChildItemService implements LayoutService {
    private final PFC pfc;
    private TextView calorie, calorieText, protein, proteinText, wheyProtein,
            wheyProteinText, soyProtein, soyProteinText, aggProtein, aggProteinText,
            caseinProtein, caseinProteinText, carbohydrate, carbohydrateText,
            simpleCarbohydrate, simpleCarbohydrateText, complexCarbohydrate,
            complexCarbohydrateText, fat, fatText, saturatedFat, saturatedFatText,
            transFat, transFatText, omega9, omega9Text, omega6, omega6Text, omega3,
            omega3Text, epa, epaText, dha, dhaText, ala, alaText, water, waterText,
            cellulose, celluloseText, salt, saltText, calcium, calciumText, potassium, potassiumText;

    public ChildItemService(PFC pfc) {
        this.pfc = pfc;
    }

    @Override
    public LayoutService writeToField() {


        calorie.setText(String.valueOf(pfc.getCalorie()));
        protein.setText(String.valueOf(pfc.getProtein()));
        wheyProtein.setText(String.valueOf(pfc.getWheyProtein()));
        soyProtein.setText(String.valueOf(pfc.getSoyProtein()));
        aggProtein.setText(String.valueOf(pfc.getAggProtein()));
        caseinProtein.setText(String.valueOf(pfc.getCaseinProtein()));
        carbohydrate.setText(String.valueOf(pfc.getCarbohydrate()));
        simpleCarbohydrate.setText(String.valueOf(pfc.getSimpleCarbohydrate()));
        complexCarbohydrate.setText(String.valueOf(pfc.getComplexCarbohydrate()));
        fat.setText(String.valueOf(pfc.getFat()));
        saturatedFat.setText(String.valueOf(pfc.getSaturatedFat()));
        transFat.setText(String.valueOf(pfc.getTransFat()));
        omega9.setText(String.valueOf(pfc.getOmega9()));
        omega6.setText(String.valueOf(pfc.getOmega6()));
        omega3.setText(String.valueOf(pfc.getOmega3()));
        epa.setText(String.valueOf(pfc.getDha()));
        dha.setText(String.valueOf(pfc.getEpa()));
        ala.setText(String.valueOf(pfc.getAla()));
        water.setText(String.valueOf(pfc.getWater()));
        cellulose.setText(String.valueOf(pfc.getCellulose()));
        salt.setText(String.valueOf(pfc.getSalt()));
        calcium.setText(String.valueOf(pfc.getCalcium()));
        potassium.setText(String.valueOf(pfc.getPotassium()));

        setValue(Arrays.asList(calorieText, proteinText, wheyProteinText, soyProteinText, aggProteinText, carbohydrateText,
                caseinProteinText, simpleCarbohydrateText, complexCarbohydrateText, fatText, saturatedFatText,
                transFatText, omega9Text, omega6Text, omega3Text, epaText, dhaText, alaText, waterText, celluloseText,
                saltText, calciumText, potassiumText),
                Arrays.asList(calorie, protein, wheyProtein, soyProtein, aggProtein, carbohydrate,
                        caseinProtein, simpleCarbohydrate, complexCarbohydrate, fat, saturatedFat, transFat,
                        omega9, omega6, omega3, epa, dha, ala, water, cellulose, salt, calcium, potassium));
        return this;
    }

    private void setValue(List<TextView> names, List<TextView> values) {
        for (int i = 0; i < values.size(); i++) {
            if (Float.parseFloat(values.get(i).getText().toString()) == 0) {
                values.get(i).setVisibility(View.GONE);
                names.get(i).setVisibility(View.GONE);
            }
        }
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
        calorie = root.findViewById(R.id.calorie_xml);
        calorieText = root.findViewById(R.id.calorie_text);
        protein = root.findViewById(R.id.protein_xml);
        proteinText = root.findViewById(R.id.protein_text);
        wheyProtein = root.findViewById(R.id.whey_protein_xml);
        wheyProteinText = root.findViewById(R.id.whey_protein_text);
        soyProtein = root.findViewById(R.id.soy_protein_xml);
        soyProteinText = root.findViewById(R.id.soy_protein_text);
        aggProtein = root.findViewById(R.id.agg_protein_xml);
        aggProteinText = root.findViewById(R.id.agg_protein_text);
        caseinProtein = root.findViewById(R.id.casein_protein_xml);
        caseinProteinText = root.findViewById(R.id.casein_protein_text);
        carbohydrate = root.findViewById(R.id.carbohydrate_xml);
        carbohydrateText = root.findViewById(R.id.carbohydrate_text);
        simpleCarbohydrate = root.findViewById(R.id.simple_carbohydrates_xml);
        simpleCarbohydrateText = root.findViewById(R.id.simple_carbohydrates_text);
        complexCarbohydrate = root.findViewById(R.id.complex_carbohydrate_xml);
        complexCarbohydrateText = root.findViewById(R.id.complex_carbohydrate_text);
        fat = root.findViewById(R.id.fat_xml);
        fatText = root.findViewById(R.id.fat_text);
        saturatedFat = root.findViewById(R.id.saturated_fat_xml);
        saturatedFatText = root.findViewById(R.id.saturated_fat_text);
        transFat = root.findViewById(R.id.trans_fat_xml);
        transFatText = root.findViewById(R.id.trans_fat_text);
        omega9 = root.findViewById(R.id.omega9_xml);
        omega9Text = root.findViewById(R.id.omega9_text);
        omega6 = root.findViewById(R.id.omega6_xml);
        omega6Text = root.findViewById(R.id.omega6_text);
        omega3 = root.findViewById(R.id.omega3_xml);
        omega3Text = root.findViewById(R.id.omega3_text);
        epa = root.findViewById(R.id.epa_xml);
        epaText = root.findViewById(R.id.epa_text);
        dha = root.findViewById(R.id.dha_xml);
        dhaText = root.findViewById(R.id.dha_text);
        ala = root.findViewById(R.id.ala_xml);
        alaText = root.findViewById(R.id.ala_text);
        water = root.findViewById(R.id.water_xml);
        waterText = root.findViewById(R.id.water_text);
        cellulose = root.findViewById(R.id.cellulose_xml);
        celluloseText = root.findViewById(R.id.cellulose_text);
        salt = root.findViewById(R.id.salt_xml);
        saltText = root.findViewById(R.id.salt_text);
        calcium = root.findViewById(R.id.calcium_xml);
        calciumText = root.findViewById(R.id.calcium_text);
        potassium = root.findViewById(R.id.potassium_xml);
        potassiumText = root.findViewById(R.id.potassium_text);
        return this;
    }
}
