package com.sharkit.nextmonday.configuration.validation.widget_validation;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.google.android.material.textfield.TextInputEditText;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.entity.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidation {

    private final TextInputEditText inputEditText;
    private final Context context;
    private final String textFromField;
    private final List<Validator> result;
    private final String size;

    public TextValidation(TextInputEditText inputEditText, Context context) {
        result = new ArrayList<>();
        this.inputEditText = inputEditText;
        this.context = context;
        this.size = context.getText(R.string.variable_size).toString();
        this.textFromField = Objects.requireNonNull(inputEditText.getText()).toString().trim();
    }

    public TextValidation notEmpty() {
        toValidatorList(context.getString(R.string.toast_field_must_be_not_empty), !TextUtils.isEmpty(textFromField));
        return this;
    }

    public TextValidation isValidEmail() {
        toValidatorList(context.getString(R.string.toast_email_is_not_valid), Patterns.EMAIL_ADDRESS.matcher(textFromField).matches());
        return this;
    }

    public TextValidation tooLongValue(Integer size) {
        toValidatorList(context.getString(R.string.toast_field_value_to_long).replace(this.size, size.toString()), textFromField.length() < size);
        return this;
    }

    public TextValidation tooShortValue(Integer size) {
        toValidatorList(context.getString(R.string.toast_field_value_to_short).replace(this.size, size.toString()), textFromField.length() > size);
        return this;
    }

    public TextValidation hasNoSymbols() {
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,°•○●□■♤♡◇♧☆▪¤《》¡¿.`]");
        Matcher hasSign = sign.matcher(textFromField);
        toValidatorList(context.getString(R.string.toast_field_has_symbols), !hasSign.find());
        return this;
    }

    public TextValidation hasNotCyrillic() {
        Pattern cyrillic = Pattern.compile("[а-яА-Я]");
        Matcher hasCyrillic = cyrillic.matcher(textFromField);
        toValidatorList(context.getString(R.string.toast_field_has_cyrillic), !hasCyrillic.find());
        return this;
    }

    public TextValidation hasNoNumber() {
        Pattern num = Pattern.compile("[0-9]");
        Matcher hasNum = num.matcher(textFromField);
        toValidatorList(context.getString(R.string.toast_field_has_number), !hasNum.find());
        return this;
    }

    public TextValidation hasNoSpace() {
        Pattern space = Pattern.compile(" ");
        Matcher hasSpace = space.matcher(textFromField);
        toValidatorList(context.getString(R.string.toast_field_has_space), !hasSpace.find());
        return this;
    }

    public Boolean build() {
        return result.stream()
                .filter(res -> res.getValid().equals(Boolean.FALSE))
                .findFirst()
                .map(res -> {
                    res.throwToastMessage(context);
                    //TODO set some animation for text field
                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);
    }

    private void toValidatorList(String message, Boolean isValid) {
        result.add(new Validator(message, isValid));
    }

}
