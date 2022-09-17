package com.sharkit.nextmonday.main_menu.calculator.service;

import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.domain.FoodInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateFoodValidator {

    private final CalculatorWidget.CreateFoodWidget widget;
    private final FoodInfo foodInfo;

    public void execute() {
        this.getProteinSum();
    }

    private void getProteinSum() {

    }
}
