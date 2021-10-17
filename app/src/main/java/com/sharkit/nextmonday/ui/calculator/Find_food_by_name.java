package com.sharkit.nextmonday.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;


public class Find_food_by_name extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_find_food_by_name, container, false);
        return root;
    }
}