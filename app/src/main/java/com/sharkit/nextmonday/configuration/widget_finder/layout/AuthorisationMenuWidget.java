package com.sharkit.nextmonday.configuration.widget_finder.layout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sharkit.nextmonday.R;

public class AuthorisationMenuWidget {

    private final View view;

    public AuthorisationMenuWidget(View view) {
        this.view = view;
    }

    public Button createAccount() {
        return view.findViewById(R.id.create_account_id);
    }

    public Button signIn() {
        return view.findViewById(R.id.sign_in_id);
    }

    public EditText email() {
        return view.findViewById(R.id.email_id);
    }

    public EditText password() {
        return view.findViewById(R.id.password_id);
    }

    public ImageView google() {
        return view.findViewById(R.id.google_id);
    }
}
