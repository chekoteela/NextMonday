package com.sharkit.nextmonday.auth.validation;

import android.content.Context;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthValidation {

    private final Context context;
    private final WidgetContainer.AuthorisationMenuWidget widget;

    public boolean isValidAuthData() {
        if (isValidAuthField(widget.getEmailLayout())) {
            return Boolean.FALSE;
        }
        if (isValidAuthField(widget.getPasswordLayout())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean isValidAuthField(TextInputLayout inputEditText) {
        return new TextValidation(inputEditText, context)
                .notEmpty()
                .build();
    }
}
