package com.sharkit.nextmonday.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.sharkit.nextmonday.R;

public class Manual_calculate_calorie extends Fragment {
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calculator_manual_calculate_calorie, container, false);

        FindView(root);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        navController.navigate(R.id.nav_cal_auto_calculate_calorie);
                        break;
                    case 1:
                        navController.navigate(R.id.nav_settings_manual_calculate);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return root;
    }

    private void FindView(View root) {
        tabLayout = root.findViewById(R.id.tab);
    }
}
