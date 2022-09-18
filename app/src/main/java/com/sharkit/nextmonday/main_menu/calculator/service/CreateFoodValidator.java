package com.sharkit.nextmonday.main_menu.calculator.service;

import android.content.Context;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sharkit.nextmonday.configuration.animation.YoYoAnimation;
import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.domain.FoodInfo;
import com.sharkit.nextmonday.main_menu.calculator.enums.FoodStatus;
import com.sharkit.nextmonday.main_menu.calculator.service.text_listner.CarbohydrateTextWatcher;
import com.sharkit.nextmonday.main_menu.calculator.service.text_listner.FatTextWatcher;
import com.sharkit.nextmonday.main_menu.calculator.service.text_listner.Omega3TextWatcher;
import com.sharkit.nextmonday.main_menu.calculator.service.text_listner.ProteinTextWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateFoodValidator {

    private final CalculatorWidget.CreateFoodWidget widget;
    private final FoodInfo foodInfo;
    private final Context context;

    private ArrayList<Boolean> valid;

    public void execute() {
        this.calculateProtein();
        this.calculateCarbohydrate();
        this.calculateFat();
        this.validateGeneralFields();
    }

    private void checkLeastFields() {
        this.foodInfo.setFoodStatus(FoodStatus.ACTIVE);
        this.foodInfo.setName(this.widget.getNameOfFood().getText().toString().trim());
        this.foodInfo.setPortion(this.getValueFromField(this.widget.getPortion()).intValue());
        this.foodInfo.setCalorie(this.getValueFromField(this.widget.getCalorie()).intValue());
        this.foodInfo.setCalcium(this.getValueFromField(this.widget.getCalcium()));
        this.foodInfo.setCellulose(this.getValueFromField(this.widget.getCellulose()));
        this.foodInfo.setSalt(this.getValueFromField(this.widget.getSalt()));
        this.foodInfo.setPotassium(this.getValueFromField(this.widget.getPotassium()));
        this.foodInfo.setWater(this.getValueFromField(this.widget.getWater()));

        this.checkCalorie(this.widget.getCalorie());
        this.checkValidText(this.widget.getNameOfFood());
        this.checkSum(this.widget.getFatWidget().getFat(), this.foodInfo.getFat().getSum());
        this.checkSum(this.widget.getProteinWidget().getProtein(), this.foodInfo.getProtein().getSum());
        this.checkSum(this.widget.getCarbohydrateWidget().getCarbohydrate(), this.foodInfo.getCarbohydrate().getSum());
        this.checkSum(this.widget.getFatWidget().getOmega3Widget().getOmega3(), this.foodInfo.getFat().getOmega3().getSum());
    }

    private void checkSum(final EditText editText, final Float sum) {
        if (this.getValueFromField(editText) < sum) {
            this.valid.add(Boolean.FALSE);
            this.setAnimationToInvalidFields(editText);
        }
    }

    public boolean isValidField() {
        this.valid = new ArrayList<>();
        this.checkLeastFields();
        return !this.valid.contains(Boolean.FALSE);
    }

    private void validateGeneralFields() {
        this.widget.getProteinWidget().getProtein()
                .setOnFocusChangeListener((v, hasFocus) ->
                        CreateFoodValidator.this.animateNotValidField(hasFocus,
                                CreateFoodValidator.this.widget.getProteinWidget().getProtein(),
                                CreateFoodValidator.this.foodInfo.getProtein().getSum()));
        this.widget.getCarbohydrateWidget().getCarbohydrate()
                .setOnFocusChangeListener((v, hasFocus) ->
                        CreateFoodValidator.this.animateNotValidField(hasFocus,
                                CreateFoodValidator.this.widget.getCarbohydrateWidget().getCarbohydrate(),
                                CreateFoodValidator.this.foodInfo.getCarbohydrate().getSum()));
        this.widget.getFatWidget().getFat()
                .setOnFocusChangeListener((v, hasFocus) ->
                        CreateFoodValidator.this.animateNotValidField(hasFocus,
                                CreateFoodValidator.this.widget.getFatWidget().getFat(),
                                CreateFoodValidator.this.foodInfo.getFat().getSum()));
        this.widget.getFatWidget().getOmega3Widget().getOmega3()
                .setOnFocusChangeListener((v, hasFocus) ->
                        CreateFoodValidator.this.animateNotValidField(hasFocus,
                                CreateFoodValidator.this.widget.getFatWidget().getOmega3Widget().getOmega3(),
                                CreateFoodValidator.this.foodInfo.getFat().getOmega3().getSum()));
    }

    private void animateNotValidField(final Boolean hasFocus, final EditText focusableWidget, final Float value) {
        if (Boolean.FALSE.equals(hasFocus)) {
            this.checkSum(focusableWidget, value);
        }
    }

    private void calculateCarbohydrate() {

        final CalculatorWidget.CreateFoodWidget.CarbohydrateWidget carbohydrateWidget = this.widget.getCarbohydrateWidget();
        final TextWatcher textWatcher = new CarbohydrateTextWatcher(carbohydrateWidget, this.foodInfo.getCarbohydrate());

        carbohydrateWidget.getSimpleCarbohydrate().addTextChangedListener(textWatcher);
        carbohydrateWidget.getComplexCarbohydrate().addTextChangedListener(textWatcher);
    }

    private void calculateProtein() {

        final CalculatorWidget.CreateFoodWidget.ProteinWidget proteinWidget = this.widget.getProteinWidget();
        final TextWatcher textWatcher = new ProteinTextWatcher(proteinWidget, this.foodInfo.getProtein());

        proteinWidget.getWheyProtein().addTextChangedListener(textWatcher);
        proteinWidget.getAggProtein().addTextChangedListener(textWatcher);
        proteinWidget.getCaseinProtein().addTextChangedListener(textWatcher);
        proteinWidget.getSoyProtein().addTextChangedListener(textWatcher);
    }

    private void calculateFat() {

        final CalculatorWidget.CreateFoodWidget.FatWidget fatWidget = this.widget.getFatWidget();
        final TextWatcher textWatcher = new FatTextWatcher(fatWidget, this.foodInfo.getFat());

        fatWidget.getSaturatedFat().addTextChangedListener(textWatcher);
        fatWidget.getTransFat().addTextChangedListener(textWatcher);
        fatWidget.getOmega6().addTextChangedListener(textWatcher);
        fatWidget.getOmega9().addTextChangedListener(textWatcher);
        fatWidget.getOmega3Widget().getOmega3().addTextChangedListener(textWatcher);

        this.calculateOmega3();
    }

    private void calculateOmega3() {

        final CalculatorWidget.CreateFoodWidget.FatWidget.Omega3Widget omega3Widget = this.widget.getFatWidget().getOmega3Widget();
        final TextWatcher textWatcher = new Omega3TextWatcher(omega3Widget, this.foodInfo.getFat().getOmega3());

        omega3Widget.getAla().addTextChangedListener(textWatcher);
        omega3Widget.getDha().addTextChangedListener(textWatcher);
        omega3Widget.getEpa().addTextChangedListener(textWatcher);
    }

    private Float getValueFromField(final EditText editText) {
        final String value = Optional.of(editText.getText().toString())
                .filter(s -> !s.isEmpty())
                .orElse("0");
        return Float.parseFloat(value);
    }

    private void checkValidText(final EditText textField) {
        final boolean isValidText = new TextValidation(textField, this.context)
                .notEmpty()
                .build();

        this.valid.add(isValidText);
    }

    private void setAnimationToInvalidFields(final EditText... fields) {
        Arrays.stream(fields)
                .forEach(field -> YoYoAnimation.getInstance().setRubberBandAnimation(field));
    }

    private void checkCalorie(final EditText textField) {
        if (this.getValueFromField(textField).intValue() == 0) {
            this.setAnimationToInvalidFields(this.widget.getCalcium(),
                    this.widget.getCellulose(),
                    this.widget.getWater(),
                    this.widget.getPotassium(),
                    this.widget.getSalt());
        } else {
            this.setAnimationToInvalidFields(this.widget.getProteinWidget().getProtein(),
                    this.widget.getCarbohydrateWidget().getCarbohydrate(),
                    this.widget.getFatWidget().getFat());
        }

    }

}
