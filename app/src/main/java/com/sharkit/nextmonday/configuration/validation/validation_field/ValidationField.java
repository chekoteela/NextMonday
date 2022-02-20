package com.sharkit.nextmonday.configuration.validation.validation_field;

import static com.sharkit.nextmonday.configuration.constant.ToastMessage.IS_NULL_SPINNER_POSITION;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.constant.ValidationMessage;
import com.sharkit.nextmonday.configuration.validation.build_validation.BuildValidation;

public class ValidationField extends ValidationMessage {

    public static boolean isValidName(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .hasNoNumber(getResourceString(context, R.string.field_has_number, editText.getHint().toString()))
                .largerSizeFor(1, getResourceString(context, R.string.field_value_to_short, editText.getHint().toString()))
                .lessSizeFor(30, getResourceString(context, R.string.field_value_to_long, editText.getHint().toString()))
                .hasNoSymbols(getResourceString(context, R.string.field_has_symbols, editText.getHint().toString()))
                .hasNoSpace(getResourceString(context, R.string.field_has_space, editText.getHint().toString()))
                .build();
    }

    public static boolean isValidEmail(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .isValidEmail(context.getString(R.string.email_is_not_valid))
                .build();
    }

    public static boolean isValidPassword(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .hasNotCyrillic(getResourceString(context, R.string.field_has_cyrillic, editText.getHint().toString()))
                .largerSizeFor(5, getResourceString(context, R.string.field_value_to_short, editText.getHint().toString()))
                .lessSizeFor(25, getResourceString(context, R.string.field_value_to_long, editText.getHint().toString()))
                .hasNoSymbols(getResourceString(context, R.string.field_has_symbols, editText.getHint().toString()))
                .hasNoSpace(getResourceString(context, R.string.field_has_space, editText.getHint().toString()))
                .build();
    }

    public static boolean isValidField(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .build();
    }

    public static boolean isValidCreateNewTargetField(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .build();
    }

    public static boolean isValidAge(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .tooSmallValue(2, getResourceString(context, R.string.field_value_to_small, editText.getHint().toString()))
                .tooBigValue(150, getResourceString(context, R.string.field_value_to_big, editText.getHint().toString()))
                .build();
    }

    public static boolean isValidWeight(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .tooSmallValue(10, getResourceString(context, R.string.field_value_to_small, editText.getHint().toString()))
                .tooBigValue(400, getResourceString(context, R.string.field_value_to_big, editText.getHint().toString()))
                .build();
    }

    public static boolean isValidHeight(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .tooSmallValue(50, getResourceString(context, R.string.field_value_to_small, editText.getHint().toString()))
                .tooBigValue(350, getResourceString(context, R.string.field_value_to_big, editText.getHint().toString()))
                .build();
    }

    public static boolean isValidDesiredWeight(float desiredWeight, float currentWeight, int target, Context context) {
        if (target < 0 && desiredWeight < currentWeight) {
            return true;
        } else if (target == 0 && desiredWeight == currentWeight) {
            return true;
        } else if (target > 0 && desiredWeight > currentWeight) {
            return true;
        } else {
            IS_NULL_SPINNER_POSITION(context);
            return false;
        }
    }

    public static boolean isEmptySpinner(Spinner spinner, Context context) {
        if (spinner.getSelectedItemPosition() == -1) {
            IS_NULL_SPINNER_POSITION(context);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidNutritionValue(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .tooBigValue(20, getResourceString(context, R.string.field_value_to_big, editText.getHint().toString()))
                .build();
    }

    public static boolean isValidWaterValue(EditText editText, Context context) {
        return new BuildValidation(context)
                .setWidget(editText)
                .notEmpty(getResourceString(context, R.string.field_must_be_not_empty, editText.getHint().toString()))
                .tooBigValue(300, getResourceString(context, R.string.field_value_to_big, editText.getHint().toString()))
                .tooSmallValue(5, getResourceString(context, R.string.field_value_to_small, editText.getHint().toString()))
                .build();
    }

    private static String getResourceString(Context context, int idRes, String field) {
        return String.format(context.getString(idRes), field);
    }
}

