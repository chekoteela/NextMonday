package com.sharkit.nextmonday.main_menu.calculator.fragmant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;

public class FoodFinderFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calculator_food_finder, container, false);
        final CalculatorWidget.FoodFinderWidget widget = CalculatorWidget.getInstance(view).getFoodFinderWidget();

        return view;
    }
}
