package com.sharkit.nextmonday.main_menu.calculator.fragmant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.configuration.navigation.CalculatorNavigation;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.enums.SearchType;
import com.sharkit.nextmonday.main_menu.calculator.service.text_listner.SearchTextListener;

public class FoodSearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calculator_food_finder, container, false);
        final CalculatorWidget.FoodSearchWidget widget = CalculatorWidget.getInstance(view).getFoodSearchWidget();

        final CalculatorNavigation navigation = CalculatorNavigation.getInstance(this.getContext());

        widget.getCreate().setOnClickListener(v -> navigation.moveToCreateFood());
        widget.getFindFood().addTextChangedListener(new SearchTextListener(widget.getListOfFood(), this.getContext(), SearchType.GENERAL));

        return view;
    }
}
