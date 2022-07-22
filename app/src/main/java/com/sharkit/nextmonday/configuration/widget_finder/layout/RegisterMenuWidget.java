package com.sharkit.nextmonday.configuration.widget_finder.layout;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sharkit.nextmonday.R;

public class RegisterMenuWidget {

    private final View view;

    public RegisterMenuWidget(View view) {
        this.view = view;
    }

    public EditText userName(){
        return view.findViewById(R.id.user_name_id);
    }

    public EditText userLastName(){
        return view.findViewById(R.id.user_last_name_id);
    }

    public EditText email(){
        return view.findViewById(R.id.email_id);
    }

    public EditText password(){
        return view.findViewById(R.id.password_id);
    }

    public CheckBox policy(){
        return view.findViewById(R.id.policy_id);
    }

    public TextView policyText(){
        return view.findViewById(R.id.policy_text_id);
    }

    public Button createAccount() {
        return view.findViewById(R.id.create_account_id);
    }

    public Button signIn() {
        return view.findViewById(R.id.sign_in_id);
    }
}
