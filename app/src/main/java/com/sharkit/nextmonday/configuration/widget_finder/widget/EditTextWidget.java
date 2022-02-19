package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.EditText;

import com.sharkit.nextmonday.R;

public class EditTextWidget {

    private final View view;

    public EditTextWidget(View view){
        this.view = view;
    }

    public EditText getPassword() {
        return view.findViewById(R.id.password_xml);
    }

    public EditText getEmail() {
        return view.findViewById(R.id.email_xml);
    }

    public EditText getConfirmPassword(){
        return view.findViewById(R.id.confirm_password_xml);
    }

    public EditText getCalorie(){
        return view.findViewById(R.id.calorie_xml);
    }


}
