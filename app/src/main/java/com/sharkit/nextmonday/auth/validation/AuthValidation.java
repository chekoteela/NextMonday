package com.sharkit.nextmonday.auth.validation;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;
import com.sharkit.nextmonday.auth.widget.AuthWidget;
import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthValidation {

    private final Context context;
    private final AuthWidget.AuthorisationMenuWidget widget;

    public boolean isValidAuthData() {
        if (this.isValidAuthField(this.widget.getEmailLayout())) {
            return Boolean.FALSE;
        }
        if (this.isValidAuthField(this.widget.getPasswordLayout())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean isValidAuthField(final TextInputLayout inputEditText) {
        return new TextValidation(inputEditText, this.context)
                .notEmpty()
                .build();
    }
}
