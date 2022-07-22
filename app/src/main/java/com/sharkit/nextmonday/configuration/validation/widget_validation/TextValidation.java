package com.sharkit.nextmonday.configuration.validation.widget_validation;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidation {

    private final String textFromField;
    private final List<Boolean> result;

    public TextValidation(String textFromField) {
        result = new ArrayList<>();
        this.textFromField = textFromField;

    }

    public TextValidation notEmpty() {
        result.add(!TextUtils.isEmpty(textFromField));
        return this;
    }

    public TextValidation isValidEmail() {
        result.add(Patterns.EMAIL_ADDRESS.matcher(textFromField).matches());
        return this;
    }

    public TextValidation tooLongValue(Integer size) {
        result.add(textFromField.length() < size);
        return this;
    }

    public TextValidation tooSmallValue(Integer size) {
        result.add(textFromField.length() > size);
        return this;
    }

    public TextValidation hasNoSymbols() {
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,°•○●□■♤♡◇♧☆▪¤《》¡¿.`]");
        Matcher hasSign = sign.matcher(textFromField);
        result.add(!hasSign.find());
        return this;
    }

    public TextValidation hasNotCyrillic() {
        Pattern cyrillic = Pattern.compile("[а-яА-Я]");
        Matcher hasCyrillic = cyrillic.matcher(textFromField);
        result.add(!hasCyrillic.find());
        return this;
    }

    public TextValidation hasNoNumber() {
        Pattern num = Pattern.compile("[0-9]");
        Matcher hasNum = num.matcher(textFromField);
        result.add(!hasNum.find());
        return this;
    }

    public TextValidation hasNoSpace() {
        Pattern space = Pattern.compile(" ");
        Matcher hasSpace = space.matcher(textFromField);
        result.add(!hasSpace.find());
        return this;
    }

    public Boolean build() {
        return !result.contains(Boolean.FALSE);
    }

}
