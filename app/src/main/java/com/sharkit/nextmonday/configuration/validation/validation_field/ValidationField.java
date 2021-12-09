package com.sharkit.nextmonday.configuration.validation.validation_field;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    public static boolean isValidAge(EditText editText, Context context){
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY)
                .tooSmallValue(2, TOO_SMALL_VALUE)
                .tooBigValue(150, TOO_BIG_VALUE)
                .build();
    }

    public static boolean isValidWeight(EditText editText, Context context){
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY)
                .tooSmallValue(10, TOO_SMALL_VALUE)
                .tooBigValue(400, TOO_BIG_VALUE)
                .build();
    }

    public static boolean isValidHeight(EditText editText, Context context){
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY)
                .tooSmallValue(50, TOO_SMALL_VALUE)
                .tooBigValue(350, TOO_BIG_VALUE)
                .build();
    }
    public static boolean isValidDesiredWeight(float desiredWeight, float currentWeight, int target, Context context){
        if (target < 0 && desiredWeight < currentWeight){
            return true;
        }else if (target == 0 && desiredWeight == currentWeight){
            return true;
        }else if (target > 0 && desiredWeight > currentWeight){
            return true;
        }else {
            Toast.makeText(context, IS_NOT_VALID_DESIRED_WEIGHT , Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean isEmptySpinner(Spinner spinner, Context context){
        if (spinner.getSelectedItemPosition() == -1) {
            Toast.makeText(context, IS_NULL_SPINNER_POSITION, Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }
    }

    public static boolean isValidNutritionValue(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(NOT_EMPTY)
                .tooBigValue(20, TOO_BIG_VALUE)
                .build();
    }
    public static boolean isValidWaterValue(EditText water, Context context) {
        return new BuildValidation(context)
                .setWidget(water)
                .notEmpty(NOT_EMPTY)
                .tooBigValue(300, TOO_BIG_VALUE)
                .tooSmallValue(5, TOO_SMALL_VALUE)
                .build();
    }
}

