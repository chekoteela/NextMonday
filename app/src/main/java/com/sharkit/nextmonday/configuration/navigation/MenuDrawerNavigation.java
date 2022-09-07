package com.sharkit.nextmonday.configuration.navigation;

import android.app.Activity;
import android.content.Context;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;

public class MenuDrawerNavigation {

    private final Context context;
    private final NavController navController;

    private MenuDrawerNavigation(Context context){
        this.context = context;
        this.navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);
    }

    public static MenuDrawerNavigation getInstance(Context context) {
        return new MenuDrawerNavigation(context);
    }

    public void moveToFeedback(){

    }
}
