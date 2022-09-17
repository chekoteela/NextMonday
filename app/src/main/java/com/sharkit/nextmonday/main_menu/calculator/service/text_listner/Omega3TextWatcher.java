package com.sharkit.nextmonday.main_menu.calculator.service.text_listner;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.Omega3;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Omega3TextWatcher implements TextWatcher {

    private final CalculatorWidget.CreateFoodWidget.FatWidget.Omega3Widget widget;
    private final Omega3 omega3;

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count,final int after) {
    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        this.fillOutAnother();
    }

    private Float getValueFromField(final EditText editText) {
        final String value = Optional.of(editText.getText().toString())
                .filter(s -> !s.isEmpty())
                .orElse("0");
        return Float.parseFloat(value);
    }

    private void fillOutAnother() {
        this.omega3.setAla(this.getValueFromField(this.widget.getAla()));
        this.omega3.setDha(this.getValueFromField(this.widget.getDha()));
        this.omega3.setEpa(this.getValueFromField(this.widget.getEpa()));

        this.widget.getOmega3().setText(String.valueOf(this.omega3.getSum()));
    }
}
