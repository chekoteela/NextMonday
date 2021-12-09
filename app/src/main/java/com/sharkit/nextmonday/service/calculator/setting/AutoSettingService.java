package com.sharkit.nextmonday.service.calculator.setting;

import static com.sharkit.nextmonday.configuration.constant.UserSetting.PERCENT_CARBOHYDRATE;
import static com.sharkit.nextmonday.configuration.constant.UserSetting.PERCENT_FAT;
import static com.sharkit.nextmonday.configuration.constant.UserSetting.PERCENT_PROTEIN;
import static com.sharkit.nextmonday.configuration.constant.UserSetting.WATER_ON_KG;
import static com.sharkit.nextmonday.configuration.constant.ValidationMessage.EMPTY_CONCLUSION;
import static com.sharkit.nextmonday.entity.transformer.TransformerFood.transform;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.db.firestore.calculator.SettingFirebase;
import com.sharkit.nextmonday.entity.calculator.GeneralNutrition;
import com.sharkit.nextmonday.entity.calculator.Metabolism;
import com.sharkit.nextmonday.entity.calculator.MetabolismDTO;
import com.sharkit.nextmonday.entity.enums.Sex;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.Objects;

public class AutoSettingService implements LayoutService {
    private TabLayout tabLayout;
    private EditText currentWeight, age, desireWeight, height;
    private TextView conclusion;
    private RadioButton male, female;
    private Spinner target, activity;
    private Button calculate, save;
    private Context context;
    private int changeWeight = 0;
    private float changeActivity = 0;
    private static final Metabolism dto = new Metabolism();

    @Override
    public LayoutService writeToField() {
        target.setAdapter(ArrayAdapter.createFromResource(context, R.array.spinner_target, R.layout.spinner_item));
        activity.setAdapter(ArrayAdapter.createFromResource(context, R.array.spinner_activity, R.layout.spinner_item));
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        Objects.requireNonNull(tab).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_settings_manual_calculate);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        changeActivity = 1.2f;
                        break;
                    case 1:
                        changeActivity = 1.4f;
                        break;
                    case 2:
                        changeActivity = 1.6f;
                        break;
                    case 3:
                        changeActivity = 1.75f;
                        break;
                    case 4:
                        changeActivity = 2f;
                        break;
                    case 5:
                        changeActivity = 2.2f;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        target.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        changeWeight = -300;
                        break;
                    case 1:
                        changeWeight = 0;
                        break;
                    case 2:
                        changeWeight = 300;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        calculate.setOnClickListener(view -> {
            if (!isValidField()) {
                return;
            }
            if (male.isChecked()) {
                dto.setSex(Sex.MALE.toString());
                conclusion.setText(String.valueOf(mifflinFormula(5, changeWeight, changeActivity)));
            } else if (female.isChecked()) {
                dto.setSex(Sex.FEMALE.toString());
                conclusion.setText(String.valueOf(mifflinFormula(-161, changeWeight, changeActivity)));
            }

        });
        save.setOnClickListener(v -> {
            if (TextUtils.isEmpty(conclusion.getText())) {
                Toast.makeText(context, EMPTY_CONCLUSION, Toast.LENGTH_SHORT).show();
            }
            if (!isValidField()) {
                return;
            }
            if (!Configuration.hasConnection(context)){
                return;
            }

            dto.setActivity(changeActivity);
            dto.setTarget(changeWeight);
            dto.setCalorie(Integer.parseInt(conclusion.getText().toString()));
            dto.setCurrentWeight(Float.parseFloat(currentWeight.getText().toString()));
            dto.setDesireWeight(Float.parseFloat(desireWeight.getText().toString()));
            dto.setHeight(Integer.parseInt(height.getText().toString()));
            new SettingFirebase()
                    .create(dto)
                    .addOnSuccessListener(unused -> Navigation
                            .findNavController((Activity) context, R.id.nav_host_fragment)
                            .navigate(R.id.nav_calculator_main));

            anotherSetting();
        });
        return this;
    }

    private void anotherSetting() {
        calculateGeneralNutrition(transform(dto));
    }

    private boolean isValidField() {
        return (!ValidationField.isValidWeight(currentWeight, context) ||
                !ValidationField.isValidAge(age, context) ||
                !ValidationField.isValidHeight(height, context) ||
                ValidationField.isEmptySpinner(target, context) ||
                ValidationField.isEmptySpinner(activity, context) ||
                ValidationField.isValidDesiredWeight(Float.parseFloat(desireWeight.getText().toString()),
                        Float.parseFloat(currentWeight.getText().toString()),
                        changeWeight, context));
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        tabLayout = root.findViewById(R.id.tab_layout_xml);
        currentWeight = root.findViewById(R.id.current_weight_xml);
        age = root.findViewById(R.id.age_xml);
        desireWeight = root.findViewById(R.id.desired_weight_xml);
        height = root.findViewById(R.id.height_xml);
        conclusion = root.findViewById(R.id.conclusion_xml);
        male = root.findViewById(R.id.male_xml);
        female = root.findViewById(R.id.female_xml);
        target = root.findViewById(R.id.spinner_target_xml);
        activity = root.findViewById(R.id.activity_xml);
        calculate = root.findViewById(R.id.calculate_xml);
        save = root.findViewById(R.id.save_xml);
        return this;
    }

    private int mifflinFormula(int var, int target, float activity) {
        return (int) ((10 * Float.parseFloat(currentWeight.getText().toString()) +
                6.25 * Float.parseFloat(height.getText().toString()) -
                5 * Integer.parseInt(age.getText().toString()) + var + target) * activity);
    }

    private void calculateGeneralNutrition(MetabolismDTO metabolismDTO) {
        GeneralNutrition generalNutrition = new GeneralNutrition();
        generalNutrition.setCalorie(metabolismDTO.getCalorie());
        generalNutrition.setCarbohydrate(getNutritionByPercent(metabolismDTO.getCalorie(),PERCENT_CARBOHYDRATE, 4));
        generalNutrition.setProtein(getNutritionByPercent(metabolismDTO.getCalorie(), PERCENT_PROTEIN, 4));
        generalNutrition.setFat(getNutritionByPercent(metabolismDTO.getCalorie(),PERCENT_FAT,8));
        generalNutrition.setWater((int)(metabolismDTO.getWeight() * WATER_ON_KG));
        new SettingFirebase()
                .create(generalNutrition);
    }

    private int getNutritionByPercent(int calorie, int percent, int calorieOnGram) {
        return (int)(calorie / 100 * percent / calorieOnGram);
    }
}
