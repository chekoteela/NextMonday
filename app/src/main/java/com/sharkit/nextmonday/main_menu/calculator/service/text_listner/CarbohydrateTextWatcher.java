package com.sharkit.nextmonday.main_menu.calculator.service.text_listner;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.Carbohydrate;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarbohydrateTextWatcher implements TextWatcher {

    private final CalculatorWidget.CreateFoodWidget.CarbohydrateWidget widget;
    private final Carbohydrate carbohydrate;

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        this.fillOut();
    }

    private Float getValueFromField(final EditText editText) {
        final String value = Optional.of(editText.getText().toString())
                .filter(s -> !s.isEmpty())
                .orElse("0");
        return Float.parseFloat(value);
    }

    private void fillOut() {
        this.carbohydrate.setComplexCarbohydrate(this.getValueFromField(this.widget.getComplexCarbohydrate()));
        this.carbohydrate.setSimpleCarbohydrate(this.getValueFromField(this.widget.getSimpleCarbohydrate()));

        this.widget.getCarbohydrate().setText(String.valueOf(this.carbohydrate.getSum()));
    }

}
