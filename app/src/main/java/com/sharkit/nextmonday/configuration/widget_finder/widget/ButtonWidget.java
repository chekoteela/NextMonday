package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharkit.nextmonday.R;

public class ButtonWidget {

    private final View view;

    public ButtonWidget(View view) {
        this.view = view;
    }


    public Button getCreateAccount() {
        return view.findViewById(R.id.create_account_xml);
    }

    public Button getSignIn() {
        return view.findViewById(R.id.sign_in_xml);

    }

    public ImageView getGoogle() {
        return view.findViewById(R.id.google_xml);

    }

    public TextView getForgotPassword() {
        return view.findViewById(R.id.forgot_password_xml);

    }
}
