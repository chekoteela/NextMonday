package com.sharkit.nextmonday.ui.calculator;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.FOOD_INFO_S;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.service.calculator.add_my_food.AddNewFoodService;


public class AddNewFood extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calculator_find_my_food, container, false);
        new AddNewFoodService((FoodInfo) requireArguments().getSerializable(FOOD_INFO_S))
                .findById(root)
                .writeToField()
                .setAdaptive()
                .activity();
        return root;
    }
}