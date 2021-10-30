package com.sharkit.nextmonday.service.calculator.add_my_food;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FOOD_INFO_S;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD_ID;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_RATION_DATE;
import static com.sharkit.nextmonday.configuration.constant.BundleVariable.UPDATE_FOOD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.firestore.calculator.FoodInfoFirebase;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;
import com.sharkit.nextmonday.entity.calculator.Meal;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class AddNewFoodService implements LayoutService {
    private Context context;
    private final FoodInfo foodInfo;
    private TextView name, meal, calorie, gram, protein, whey_protein, soyProtein,
            aggProtein, caseinProtein, carbohydrate, simpleCarbohydrate, complexCarbohydrate,
            fat, saturatedFat, transFat, omega9, omega6, omega3, ala, dha, epa,
            water, cellulose, salt, calcium, potassium;
    private ImageView favoriteFood;
    private EditText portions;
    private SwitchCompat weight;
    private Spinner mealSpinner;
    private Button save, update;

    public AddNewFoodService(FoodInfo findFood) {
        this.foodInfo = findFood;
    }

    @Override
    public LayoutService writeToField() {
        setSpinnerAdapter();
        name.setText(foodInfo.getName());
        calorie.setText(String.valueOf(foodInfo.getCalorie()));
        protein.setText(String.valueOf(foodInfo.getProtein()));
        whey_protein.setText(String.valueOf(foodInfo.getWheyProtein()));
        soyProtein.setText(String.valueOf(foodInfo.getSoyProtein()));
        aggProtein.setText(String.valueOf(foodInfo.getAggProtein()));
        caseinProtein.setText(String.valueOf(foodInfo.getCaseinProtein()));
        carbohydrate.setText(String.valueOf(foodInfo.getCarbohydrate()));
        simpleCarbohydrate.setText(String.valueOf(foodInfo.getSimpleCarbohydrate()));
        complexCarbohydrate.setText(String.valueOf(foodInfo.getComplexCarbohydrate()));
        fat.setText(String.valueOf(foodInfo.getFat()));
        saturatedFat.setText(String.valueOf(foodInfo.getSaturatedFat()));
        transFat.setText(String.valueOf(foodInfo.getTransFat()));
        omega9.setText(String.valueOf(foodInfo.getOmega9()));
        omega6.setText(String.valueOf(foodInfo.getOmega6()));
        omega3.setText(String.valueOf(foodInfo.getOmega3()));
        ala.setText(String.valueOf(foodInfo.getAla()));
        dha.setText(String.valueOf(foodInfo.getDha()));
        epa.setText(String.valueOf(foodInfo.getEpa()));
        water.setText(String.valueOf(foodInfo.getWater()));
        cellulose.setText(String.valueOf(foodInfo.getCellulose()));
        salt.setText(String.valueOf(foodInfo.getSalt()));
        calcium.setText(String.valueOf(foodInfo.getCalcium()));
        potassium.setText(String.valueOf(foodInfo.getPotassium()));
        return this;
    }

    private void setSpinnerAdapter() {
        new FoodInfoFirebase()
                .getMealList()
                .addOnSuccessListener(querySnapshots -> {
                    ArrayList<String> meals = new ArrayList<>();
                    for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshots) {
                        meals.add(queryDocumentSnapshot.toObject(Meal.class).getName());
                    }
                    mealSpinner.setAdapter(new ArrayAdapter<>(context, R.layout.spinner_item, meals));
                });
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService activity() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkFoodDTO linkFoodDTO = new LinkFoodDTO();
                linkFoodDTO.setVisible(true);
                linkFoodDTO.setLink(foodInfo.getId());
                linkFoodDTO.setPortion(Float.parseFloat(portions.getText().toString()));
                linkFoodDTO.setMeal(mealSpinner.getSelectedItem().toString());

                new FoodInfoFirebase()
                        .addNewMyFood(linkFoodDTO)
                        .addOnSuccessListener(unused -> {
                            Bundle bundle = new Bundle();
                            bundle.putString(FRAGMENT_RATION_DATE, new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()));
                            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_ration, bundle);
                        });
            }
        });

        update.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(FOOD_INFO_S, foodInfo);
            bundle.putString(FRAGMENT_CREATE_FOOD_ID, foodInfo.getId());
            bundle.putString(FRAGMENT_CREATE_FOOD, UPDATE_FOOD);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_create_food, bundle);
        });
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        name = root.findViewById(R.id.name_xml);
        meal = root.findViewById(R.id.meal_xml);
        calorie = root.findViewById(R.id.calorie_xml);
        gram = root.findViewById(R.id.gram_xml);
        protein = root.findViewById(R.id.protein_xml);
        whey_protein = root.findViewById(R.id.whey_protein_xml);
        soyProtein = root.findViewById(R.id.soy_protein_xml);
        aggProtein = root.findViewById(R.id.agg_protein_xml);
        caseinProtein = root.findViewById(R.id.casein_protein_xml);
        carbohydrate = root.findViewById(R.id.carbohydrate_xml);
        simpleCarbohydrate = root.findViewById(R.id.simple_carbohydrates_xml);
        complexCarbohydrate = root.findViewById(R.id.complex_carbohydrate_xml);
        fat = root.findViewById(R.id.fat_xml);
        saturatedFat = root.findViewById(R.id.saturated_fat_xml);
        transFat = root.findViewById(R.id.trans_fat_xml);
        omega9 = root.findViewById(R.id.omega9_xml);
        omega6 = root.findViewById(R.id.omega6_xml);
        omega3 = root.findViewById(R.id.omega3_xml);
        ala = root.findViewById(R.id.ala_xml);
        dha = root.findViewById(R.id.dha_xml);
        epa = root.findViewById(R.id.epa_xml);
        water = root.findViewById(R.id.water_xml);
        cellulose = root.findViewById(R.id.cellulose_xml);
        salt = root.findViewById(R.id.salt_xml);
        calcium = root.findViewById(R.id.calcium_xml);
        potassium = root.findViewById(R.id.potassium_xml);
        update = root.findViewById(R.id.update_food_xml);

        favoriteFood = root.findViewById(R.id.favorite_xml);
        portions = root.findViewById(R.id.number_xml);
        weight = root.findViewById(R.id.weight_xml);
        mealSpinner = root.findViewById(R.id.meal_spinner_xml);
        save = root.findViewById(R.id.save_xml);
        return this;
    }
}
