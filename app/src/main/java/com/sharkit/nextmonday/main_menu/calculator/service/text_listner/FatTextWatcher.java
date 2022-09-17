package com.sharkit.nextmonday.main_menu.calculator.service.text_listner;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.Fat;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FatTextWatcher implements TextWatcher {

    private final CalculatorWidget.CreateFoodWidget.FatWidget widget;
    private final Fat fat;

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
        this.fat.setSaturatedFat(this.getValueFromField(this.widget.getSaturatedFat()));
        this.fat.setTransFat(this.getValueFromField(this.widget.getTransFat()));
        this.fat.setOmega9(this.getValueFromField(this.widget.getOmega9()));
        this.fat.setOmega6(this.getValueFromField(this.widget.getOmega6()));
        this.fat.getOmega3().setOmega3(this.getValueFromField(this.widget.getOmega3Widget().getOmega3()));

        this.widget.getFat().setText(String.valueOf(this.fat.getSum()));
    }

}
