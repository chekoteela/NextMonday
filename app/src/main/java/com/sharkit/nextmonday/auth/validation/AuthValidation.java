package com.sharkit.nextmonday.auth.validation;

import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;
import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthValidation {

    private final Context context;
    private final WidgetContainer.AuthorisationMenuWidget widget;

    public boolean isValidAuthData() {
        if (isValidAuthField(widget.getEmail())) {
            return Boolean.FALSE;
        }
        if (isValidAuthField(widget.getPassword())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean isValidAuthField(TextInputEditText inputEditText) {
        return new TextValidation(inputEditText, context)
                .notEmpty()
                .build();
    }
}
