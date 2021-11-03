package com.sharkit.nextmonday.service.calculator.setting;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class AutoSettingService implements LayoutService {
    private TabLayout tabLayout;
    private TextView currentWeight, age, desireWeight, height, conclusion;
    private RadioButton male, female, harrison, mifflin;
    private Spinner target, activity;
    private Button calculate, save;


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
        tabLayout = root.findViewById(R.id.tab_layout_xml);
        currentWeight = root.findViewById(R.id.current_weight_xml);
        age = root.findViewById(R.id.age_xml);
        desireWeight = root.findViewById(R.id.desired_weight_xml);
        height = root.findViewById(R.id.height_xml);
        conclusion = root.findViewById(R.id.conclusion_xml);
        male = root.findViewById(R.id.male_xml);
        female = root.findViewById(R.id.female_xml);
        harrison = root.findViewById(R.id.harrison_formula_xml);
        mifflin = root.findViewById(R.id.miffin_formula_xml);
        target = root.findViewById(R.id.spinner_target_xml);
        activity = root.findViewById(R.id.activity_xml);
        calculate = root.findViewById(R.id.calculate_xml);
        save = root.findViewById(R.id.save_xml);
        return this;
    }
}
