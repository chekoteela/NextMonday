package com.sharkit.nextmonday.service.calculator.create_food;

import static com.sharkit.nextmonday.configuration.constant.BundleVariable.CREATE_NEW_FOOD;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.MODERATION_FOOD;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class CreateFoodService implements LayoutService {
    private EditText name, portion, calorie, protein, wheyProtein, soyProtein, aggProtein,
    caseinProtein, carbohydrate, simpleCarbohydrate, complexCarbohydrate, fat,
    saturatedFat, transFat, omega3, omega6, omega9, dha, epa, ala,
    cellulose, water, salt, calcium, potassium;
    private Button save;
    private final String bundle;
    private final String id;
    private FoodInfo foodInfo;

    public CreateFoodService(String bundle, String id) {
        this.bundle = bundle;
        this.id = id;
    }
    public CreateFoodService(String bundle, String id, FoodInfo foodInfo) {
        this.id = id;
        this.bundle = bundle;
        this.foodInfo = foodInfo;
    }

    private FoodInfo writeFromField() {
        FoodInfo foodInfo = new FoodInfo();
        foodInfo.setId(id);
        foodInfo.setName(name.getText().toString().trim());
        foodInfo.setPortion(Float.parseFloat(portion.getText().toString().trim()));
        foodInfo.setCalorie(Integer.parseInt(calorie.getText().toString().trim()));
        foodInfo.setProtein(protein.getText().toString().trim());
        foodInfo.setWheyProtein(wheyProtein.getText().toString().trim());
        foodInfo.setSoyProtein(soyProtein.getText().toString().trim());
        foodInfo.setAggProtein(aggProtein.getText().toString().trim());
        foodInfo.setCaseinProtein(caseinProtein.getText().toString().trim());
        foodInfo.setCarbohydrate(carbohydrate.getText().toString().trim());
        foodInfo.setSimpleCarbohydrate(simpleCarbohydrate.getText().toString().trim());
        foodInfo.setComplexCarbohydrate(complexCarbohydrate.getText().toString().trim());
        foodInfo.setFat(fat.getText().toString().trim());
        foodInfo.setSaturatedFat(saturatedFat.getText().toString().trim());
        foodInfo.setTransFat(transFat.getText().toString().trim());
        foodInfo.setOmega3(omega3.getText().toString().trim());
        foodInfo.setOmega6(omega6.getText().toString().trim());
        foodInfo.setOmega9(omega9.getText().toString().trim());
        foodInfo.setDha(dha.getText().toString().trim());
        foodInfo.setEpa(epa.getText().toString().trim());
        foodInfo.setAla(ala.getText().toString().trim());
        foodInfo.setCellulose(cellulose.getText().toString().trim());
        foodInfo.setWater(water.getText().toString().trim());
        foodInfo.setSalt(salt.getText().toString().trim());
        foodInfo.setCalcium(calcium.getText().toString().trim());
        foodInfo.setPotassium(potassium.getText().toString().trim());
        return foodInfo;
    }

    @Override
    public LayoutService writeToField() {
        if (!bundle.equals(CREATE_NEW_FOOD)){
            name.setText(foodInfo.getName());
            portion.setText(String.valueOf(foodInfo.getPortion()));
            calorie.setText(String.valueOf(foodInfo.getCalorie()));
            protein.setText(String.valueOf(foodInfo.getProtein()));
            wheyProtein.setText(String.valueOf(foodInfo.getWheyProtein()));
            soyProtein.setText(String.valueOf(foodInfo.getSoyProtein()));
            aggProtein.setText(String.valueOf(foodInfo.getAggProtein()));
            caseinProtein.setText(String.valueOf(foodInfo.getCaseinProtein()));
            carbohydrate.setText(String.valueOf(foodInfo.getCarbohydrate()));
            simpleCarbohydrate.setText(String.valueOf(foodInfo.getSimpleCarbohydrate()));
            complexCarbohydrate.setText(String.valueOf(foodInfo.getComplexCarbohydrate()));
            fat.setText(String.valueOf(foodInfo.getFat()));
            saturatedFat.setText(String.valueOf(foodInfo.getSaturatedFat()));
            transFat.setText(String.valueOf(foodInfo.getTransFat()));
            omega3.setText(String.valueOf(foodInfo.getOmega3()));
            omega6.setText(String.valueOf(foodInfo.getOmega6()));
            omega9.setText(String.valueOf(foodInfo.getOmega9()));
            dha.setText(String.valueOf(foodInfo.getDha()));
            epa.setText(String.valueOf(foodInfo.getEpa()));
            ala.setText(String.valueOf(foodInfo.getAla()));
            cellulose.setText(String.valueOf(foodInfo.getCellulose()));
            water.setText(String.valueOf(foodInfo.getWater()));
            salt.setText(String.valueOf(foodInfo.getSalt()));
            calcium.setText(String.valueOf(foodInfo.getCalcium()));
            potassium.setText(String.valueOf(foodInfo.getPotassium()));
        }
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance()
                        .collection(MODERATION_FOOD)
                        .document(id)
                        .set(writeFromField());
            }
        });
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

        save = root.findViewById(R.id.save_xml);
        return this;
    }
}
