package com.sharkit.nextmonday.service.calculator.find_food;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD_ID;
import static com.sharkit.nextmonday.configuration.constant.BundleVariable.CREATE_NEW_FOOD;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.UUID;

public class FindFoodService implements LayoutService {
    private Context context;
    private TabLayout tabLayout;
    private EditText findFood;
    private ExpandableListView listView;
    private Button create;
    private AdView adView;

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
        Configuration.showAdView(adView);
        create.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(FRAGMENT_CREATE_FOOD, CREATE_NEW_FOOD);
            bundle.putString(FRAGMENT_CREATE_FOOD_ID, UUID.randomUUID().toString());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_create_food, bundle);
        });
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        tabLayout = root.findViewById(R.id.tab_layout_xml);
        findFood = root.findViewById(R.id.find_food_xml);
        listView = root.findViewById(R.id.list_of_item_xml);
        create = root.findViewById(R.id.create_new_food_xml);
        adView = root.findViewById(R.id.adView_xml);
        return this;
    }
}
