package com.sharkit.nextmonday.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sharkit.nextmonday.R;


public class Settings extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings, container, false);

        Button profile = root.findViewById(R.id.profile);
        Button calculator = root.findViewById(R.id.calculator);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.nav_profile);
            }
        });
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.nav_cal_auto_calculate_calorie);
            }
        });

        return root;

    }
}