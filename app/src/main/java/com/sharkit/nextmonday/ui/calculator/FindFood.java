package com.sharkit.nextmonday.ui.calculator;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_MEAL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.calculator.find_food.FindFoodService;


public class FindFood extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_find_food_by_name, container, false);
        FindFoodService foodService;
        try {
            foodService = new FindFoodService(requireArguments().getString(FRAGMENT_MEAL));
        }catch (IllegalStateException e){
            foodService = new FindFoodService();
        }
           foodService.findById(root)
                   .writeToField()
                   .activity()
                   .setAdaptive();
        return root;
    }
}