package com.sharkit.nextmonday.configuration.validation.validation_field;

import android.content.Context;
import android.widget.EditText;

import com.sharkit.nextmonday.configuration.constant.ValidationMessage;
import com.sharkit.nextmonday.configuration.validation.build_validation.BuildValidation;

public class ValidationField extends ValidationMessage {

    public static boolean isValidName(EditText editText, Context context){
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY + editText.getHint().toString())
                .hasNoNumber(HAS_NOT_NUMBER + editText.getHint().toString())
                .largerSizeFor(1, TOO_SHORT + editText.getHint().toString())
                .lessSizeFor(30, TOO_LONG + editText.getHint().toString())
                .hasNoSymbols(HAS_NOT_SYMBOLS + editText.getHint().toString())
                .hasNoSpace(HAS_NOT_SPACE + editText.getHint().toString())
                .build();
    }
    public static boolean isValidEmail(EditText editText, Context context){
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY + editText.getHint().toString())
                .isValidEmail(NOT_VALID_EMAIL)
                .build();
    }
    public static boolean isValidPassword(EditText editText, Context context){
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY + editText.getHint().toString())
                .hasNotCyrillic(HAS_NOT_CYRILLIC + editText.getHint().toString())
                .largerSizeFor(5, TOO_SHORT + editText.getHint().toString())
                .lessSizeFor(25,TOO_LONG + editText.getHint().toString())
                .hasNoSymbols(HAS_NOT_SYMBOLS + editText.getHint().toString())
                .hasNoSpace(HAS_NOT_SPACE + editText.getHint().toString())
                .build();
    }
    public static boolean isValidField(EditText editText, Context context){
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY)
                .build();
    }
    public static boolean isValidCreateNewTargetField(EditText editText, Context context){
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY)
                .build();
    }
}

