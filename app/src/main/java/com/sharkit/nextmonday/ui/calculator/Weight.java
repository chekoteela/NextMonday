package com.sharkit.nextmonday.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.calculator.weight.WeightService;


public class Weight extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calculator_weight, container, false);
        new WeightService()
                .findById(root)
                .writeToField()
                .activity()
                .setAdaptive();
        return root;
    }
}