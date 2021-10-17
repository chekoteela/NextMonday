package com.sharkit.nextmonday.service.general;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class SettingListService implements LayoutService {
    private Button profile, calculator;
    private AdView adView;
    private Context context;

    @Override
    public LayoutService writeToField() {
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        adView = root.findViewById(R.id.adView);
        profile = root.findViewById(R.id.profile_xml);
        calculator = root.findViewById(R.id.calculator_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        Configuration.showAdView(adView);
        NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);

        profile.setOnClickListener(v -> navController.navigate(R.id.nav_profile));
        calculator.setOnClickListener(v -> navController.navigate(R.id.nav_cal_auto_calculate_calorie));
        return this;
    }
}
