package com.sharkit.nextmonday.configuration.navigation;

import android.app.Activity;
import android.content.Context;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;

public class MenuDrawerNavigation {

    private final NavController navController;

    private MenuDrawerNavigation(final Context context) {
        this.navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);
    }

    public static MenuDrawerNavigation getInstance(final Context context) {
        return new MenuDrawerNavigation(context);
    }

    public void moveToDiary() {
        this.navController.navigate(R.id.navigation_diary_main);
    }

    public void moveToCalculator() {
        this.navController.navigate(R.id.navigation_calculator_main);
    }

    public void moveToFeedback() {

    }
}
