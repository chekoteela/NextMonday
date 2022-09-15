package com.sharkit.nextmonday.configuration.navigation;

import android.app.Activity;
import android.content.Context;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;

public class CalculatorNavigation {

    private final NavController navController;

    private CalculatorNavigation(final Context context) {
        this.navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);
    }

    public static CalculatorNavigation getInstance(final Context context) {
        return new CalculatorNavigation(context);
    }

    public void toMainMenu() {
        this.navController.navigate(R.id.navigation_calculator_main);
    }

    public void toRationMenu() {
        this.navController.navigate(R.id.navigation_calculator_ration);
    }

    public void toCalendarMenu() {
        this.navController.navigate(R.id.navigation_calculator_calendar);
    }

    public void toWeightMenu() {
        this.navController.navigate(R.id.navigation_calculator_weight);
    }
}
