package com.sharkit.nextmonday.configuration.validation.widget_validation;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.entity.Validator;
import com.sharkit.nextmonday.configuration.animation.YoYoAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidation {

    private final View inputLayout;
    private final Context context;
    private final String textFromField;
    private final List<Validator> result;
    private final String size;

    public TextValidation(final TextInputLayout inputLayout, final Context context) {
        this.result = new ArrayList<>();
        this.inputLayout = inputLayout;
        this.context = context;
        this.size = context.getText(R.string.variable_size).toString();
        this.textFromField = Objects.requireNonNull(inputLayout.getEditText()).getText().toString().trim();
    }

    public TextValidation(final EditText inputText, final Context context) {
        this.result = new ArrayList<>();
        this.inputLayout = inputText;
        this.context = context;
        this.size = context.getText(R.string.variable_size).toString();
        this.textFromField = Objects.requireNonNull(inputText).getText().toString().trim();
    }

    public TextValidation notZero() {
        final Integer currentValue = this.getValueFromField(this.textFromField);
        final Boolean isZero = !currentValue.equals(0);
        this.toValidatorList(this.context.getString(R.string.toast_field_must_be_not_zero), isZero);
        return this;
    }

    public TextValidation notEmpty() {
        this.toValidatorList(this.context.getString(R.string.toast_field_must_be_not_empty), !TextUtils.isEmpty(this.textFromField));
        return this;
    }

    public TextValidation isValidEmail() {
        this.toValidatorList(this.context.getString(R.string.toast_email_is_not_valid), Patterns.EMAIL_ADDRESS.matcher(this.textFromField).matches());
        return this;
    }

    public TextValidation tooLongValue(final Integer size) {
        this.toValidatorList(this.context.getString(R.string.toast_field_value_to_long).replace(this.size, size.toString()), this.textFromField.length() < size);
        return this;
    }

    public TextValidation tooShortValue(final Integer size) {
        this.toValidatorList(this.context.getString(R.string.toast_field_value_to_short).replace(this.size, size.toString()), this.textFromField.length() > size);
        return this;
    }

    public TextValidation hasNoSymbols() {
        final Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,°•○●□■♤♡◇♧☆▪¤《》¡¿.`]");
        final Matcher hasSign = sign.matcher(this.textFromField);
        this.toValidatorList(this.context.getString(R.string.toast_field_has_symbols), !hasSign.find());
        return this;
    }

    public TextValidation hasNotCyrillic() {
        final Pattern cyrillic = Pattern.compile("[а-яА-Я]");
        final Matcher hasCyrillic = cyrillic.matcher(this.textFromField);
        this.toValidatorList(this.context.getString(R.string.toast_field_has_cyrillic), !hasCyrillic.find());
        return this;
    }

    public TextValidation hasNoNumber() {
        final Pattern num = Pattern.compile("[0-9]");
        final Matcher hasNum = num.matcher(this.textFromField);
        this.toValidatorList(this.context.getString(R.string.toast_field_has_number), !hasNum.find());
        return this;
    }

    public TextValidation hasNoSpace() {
        final Pattern space = Pattern.compile(" ");
        final Matcher hasSpace = space.matcher(this.textFromField);
        this.toValidatorList(this.context.getString(R.string.toast_field_has_space), !hasSpace.find());
        return this;
    }

    public Boolean build() {
        return this.result.stream()
                .filter(res -> res.getValid().equals(Boolean.FALSE))
                .findFirst()
                .map(res -> {
                    res.throwToastMessage(this.context);
                    YoYoAnimation.getInstance().setRubberBandAnimation(this.inputLayout);
                    return Boolean.FALSE;
                })
                .orElse(Boolean.TRUE);
    }

    private void toValidatorList(final String message, final Boolean isValid) {
        this.result.add(new Validator(message, isValid));
    }

    private Integer getValueFromField(final String inputValue) {
        final String value = Optional.of(inputValue)
                .filter(s -> !s.isEmpty())
                .orElse("0");
        return Integer.parseInt(value);
    }

}
