package com.sharkit.nextmonday.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.calculator.calculator_main.CalculatorMainService;

public class CalculatorMain extends Fragment{
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calculator_main, container, false);
        new CalculatorMainService()
                .findById(root)
                .writeToField()
                .activity()
                .setAdaptive();
        return root;
    }
}