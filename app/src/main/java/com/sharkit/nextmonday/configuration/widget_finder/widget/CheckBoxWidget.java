package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.CheckBox;

import com.sharkit.nextmonday.R;

public class CheckBoxWidget {

    private final View view;

    public CheckBoxWidget(View view) {
        this.view = view;
    }

    public CheckBox isAgreePolicy() {
        return view.findViewById(R.id.policy_xml);
    }

}
