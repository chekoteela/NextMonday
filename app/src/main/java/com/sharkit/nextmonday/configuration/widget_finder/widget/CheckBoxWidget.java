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

    public CheckBox isMonday() {
        return view.findViewById(R.id.monday_xml);
    }

    public CheckBox isTuesday() {
        return view.findViewById(R.id.tuesday_xml);
    }

    public CheckBox isWednesday() {
        return view.findViewById(R.id.wednesday_xml);
    }

    public CheckBox isThursday() {
        return view.findViewById(R.id.thursday_xml);
    }

    public CheckBox isFriday() {
        return view.findViewById(R.id.friday_xml);
    }

    public CheckBox isSaturday() {
        return view.findViewById(R.id.saturday_xml);
    }

    public CheckBox isSunday() {
        return view.findViewById(R.id.sunday_xml);
    }
}
