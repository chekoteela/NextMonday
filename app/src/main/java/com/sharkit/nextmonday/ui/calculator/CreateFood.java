package com.sharkit.nextmonday.ui.calculator;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.FOOD_INFO_S;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.service.calculator.create_food.CreateFoodService;


public class CreateFood extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_change_food, container, false);

        new CreateFoodService(requireArguments().getString(FRAGMENT_CREATE_FOOD),
                requireArguments().getString(FRAGMENT_CREATE_FOOD_ID),
                (FoodInfo) requireArguments().getSerializable(FOOD_INFO_S))
                .findById(root)
                .writeToField()
                .activity()
                .setAdaptive();

        return root;
    }
}