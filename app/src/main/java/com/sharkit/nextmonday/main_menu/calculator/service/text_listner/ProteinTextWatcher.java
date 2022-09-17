package com.sharkit.nextmonday.main_menu.calculator.service.text_listner;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.Protein;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProteinTextWatcher implements TextWatcher {

    private final CalculatorWidget.CreateFoodWidget.ProteinWidget widget;
    private final Protein protein;

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count,final int after) {
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
        this.protein.setWheyProtein(this.getValueFromField(this.widget.getWheyProtein()));
        this.protein.setAggProtein(this.getValueFromField(this.widget.getAggProtein()));
        this.protein.setCaseinProtein(this.getValueFromField(this.widget.getCaseinProtein()));
        this.protein.setSoyProtein(this.getValueFromField(this.widget.getSoyProtein()));

        this.widget.getProtein().setText(String.valueOf(this.protein.getSum()));
    }
}
